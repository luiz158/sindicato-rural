package com.sindicato.painelcontrole.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.impl.DAOImpl;
import com.sindicato.painelcontrole.dao.UsuarioDAO;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Perfil;
import com.sindicato.painelcontrole.entity.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.PasswordManager;

@Stateful
public class UsuarioDAOImpl extends DAOImpl<Usuario, Integer> implements UsuarioDAO {

	private Usuario usuarioAutenticado = null;
	
	@Override
	public void insert(Usuario usuario){
		usuario.setSenha(PasswordManager.generated(usuario.getSenha()));
		super.insert(usuario);
	}
	
	@Override
	public ResultOperation autenticar(String usuario, String senha) {
		String strQuery = "select u from Usuario u join fetch u.perfis" +
				" where u.email = :usuario ";
		
		TypedQuery<Usuario> query = em.createQuery(strQuery, Usuario.class);
		query.setParameter("usuario", usuario);
		
		ResultOperation result = new ResultOperation();
		
		try {
			usuarioAutenticado = query.getSingleResult();
			
			if(!usuarioAutenticado.isAtivo()){
				result.setMessage("Usuário desativado");
				result.setSuccess(false);
			}else if(usuarioAutenticado.getSenha().equals(PasswordManager.generated(senha))){
				result.setMessage("Usuário autenticado");
				result.setSuccess(true);
			}else{
				result.setMessage("Senha de usuário não confere");
				result.setSuccess(false);
				usuarioAutenticado = null;
			}
			
			return result;
			
		} catch (NoResultException e) {
			result.setMessage("Usuário não existe");
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	@Override
	public List<Menu> getMenusPermitidos() {
		if(usuarioAutenticado == null){
			return null;
		}
		String strQuery = "select p from Perfil p " +
				" where p in (:perfis)";
		TypedQuery<Perfil> query = em.createQuery(strQuery, Perfil.class);
		query.setParameter("perfis", usuarioAutenticado.getPerfis());
		
		try {
			List<Menu> menus = new ArrayList<Menu>();
			for (Perfil perfil : query.getResultList()) {
				menus.addAll(perfil.getMenus());
			}
			return menus;
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public List<Perfil> getPerfisDoUsuario(Usuario usuario) {
		
		try {
			String strQuery = "select u from Usuario u "
					+ " join fetch u.perfis p"
					+ " where u = :usuario";
			TypedQuery<Usuario> query = em.createQuery(strQuery, Usuario.class);
			query.setParameter("usuario", usuario);
			return query.getSingleResult().getPerfis();
		} catch (NoResultException e) {
			return null;
		}
	}
}

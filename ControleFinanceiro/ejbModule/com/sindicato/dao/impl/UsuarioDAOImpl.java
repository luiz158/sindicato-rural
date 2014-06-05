package com.sindicato.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.UsuarioDAO;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.PasswordManager;

@Stateless
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
			
			if(usuarioAutenticado.getSenha().equals(PasswordManager.generated(senha))){
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
	public void atualizarSenha(Usuario usuario, String novaSenha) {
		usuario.setSenha(PasswordManager.generated(novaSenha));
		super.update(usuario);
	}
}

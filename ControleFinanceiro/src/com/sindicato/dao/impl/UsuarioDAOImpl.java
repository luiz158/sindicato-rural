package com.sindicato.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.UsuarioDAO;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.PasswordManager;

public class UsuarioDAOImpl extends DAOImpl<Usuario, Integer> implements UsuarioDAO {

	public UsuarioDAOImpl(EntityManager em) {
		super(em);
	}

	private Usuario usuarioAutenticado = null;
	
	@Override
	public void insert(Usuario usuario){
		usuario.setSenha(PasswordManager.generated(usuario.getSenha()));
		this.insert(usuario);
	}
	
	@Override
	public ResultOperation autenticar(String usuario, String senha) {
		String strQuery = "select u from Usuario u " +
				" where u.email = :usuario ";
		
		TypedQuery<Usuario> query = em.createQuery(strQuery, Usuario.class);
		query.setParameter("usuario", usuario);
		
		ResultOperation result = new ResultOperation();
		
		try {
			usuarioAutenticado = query.getSingleResult();
			
			if(usuarioAutenticado.getSenha().equals(PasswordManager.generated(senha))){
				result.setMessage("Usu�rio autenticado");
				result.setSuccess(true);
			}else{
				result.setMessage("Senha de usu�rio n�o confere");
				result.setSuccess(false);
				usuarioAutenticado = null;
			}
			
			return result;
			
		} catch (NoResultException e) {
			result.setMessage("Usu�rio n�o existe");
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
}

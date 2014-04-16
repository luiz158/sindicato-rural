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
import com.sindicato.seguranca.PasswordManager;

public class UsuarioDAOImpl extends DAOImpl<Usuario, Integer> implements UsuarioDAO {

	public UsuarioDAOImpl(EntityManager em) {
		super(em);
	}

	private Usuario usuarioAutenticado = null;
	
	@Override
	public boolean autenticar(String usuario, String senha) {
		String strQuery = "select u from Usuario u " +
				" where u.email = :usuario and u.senha = :senha";
		
		TypedQuery<Usuario> query = em.createQuery(strQuery, Usuario.class);
		query.setParameter("usuario", usuario);
		query.setParameter("senha", PasswordManager.generated(senha));
		
		try {
			usuarioAutenticado = query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			usuarioAutenticado = null;
			return false;
		}
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

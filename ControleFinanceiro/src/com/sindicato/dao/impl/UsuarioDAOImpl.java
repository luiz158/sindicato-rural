package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.UsuarioDAO;
import com.sindicato.entity.autenticacao.Usuario;

public class UsuarioDAOImpl extends DAOImpl<Usuario, Integer> implements UsuarioDAO {

	public UsuarioDAOImpl(EntityManager em) {
		super(em);
	}
}

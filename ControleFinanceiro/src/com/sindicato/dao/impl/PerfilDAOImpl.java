package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.PerfilDAO;
import com.sindicato.entity.autenticacao.Perfil;

public class PerfilDAOImpl extends DAOImpl<Perfil, Integer> implements PerfilDAO {

	public PerfilDAOImpl(EntityManager em) {
		super(em);
	}
}

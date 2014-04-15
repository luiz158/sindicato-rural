package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.MenuDAO;
import com.sindicato.entity.autenticacao.Menu;

public class MenuDAOImpl extends DAOImpl<Menu, Integer> implements MenuDAO {

	public MenuDAOImpl(EntityManager em) {
		super(em);
	}
}

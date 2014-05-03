package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.entity.Debito;

public class DebitoDAOImpl implements DebitoDAO {

	EntityManager em;
	
	public DebitoDAOImpl(EntityManager entityManager) {
		em = entityManager;
	}

	@Override
	public Debito searchByID(Integer id) {
		return em.find(Debito.class, id);
	}
}

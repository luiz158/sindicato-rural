package com.sindicato.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.entity.Debito;

@Stateless
public class DebitoDAOImpl implements DebitoDAO {

	@PersistenceContext(name="ControleFinanceiro")
	EntityManager em;
	
	@Override
	public Debito searchByID(Integer id) {
		return em.find(Debito.class, id);
	}
}

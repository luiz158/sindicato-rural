package com.sindicato.painelcontrole.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.painelcontrole.dao.ListasPCDAO;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Perfil;

@Stateless
public class ListasPCDAOImpl implements ListasPCDAO {

	@PersistenceContext(name="ControleFinanceiro")
	private EntityManager em;
	
	@Override
	public List<Perfil> getTodosOsPerfis() {
		try {
			String strQuery = " select p from Perfil p ";
			TypedQuery<Perfil> query = em.createQuery(strQuery, Perfil.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Menu> getTodosOsMenus() {
		try {
			String strQuery = " select m from Menu m "
					+ "order by m.ordem asc ";
			TypedQuery<Menu> query = em.createQuery(strQuery, Menu.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}

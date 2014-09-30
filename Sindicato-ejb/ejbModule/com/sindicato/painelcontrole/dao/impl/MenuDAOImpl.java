package com.sindicato.painelcontrole.dao.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.impl.DAOImpl;
import com.sindicato.painelcontrole.dao.MenuDAO;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Perfil;

@Stateful
public class MenuDAOImpl extends DAOImpl<Menu, Integer> implements MenuDAO {

	@Override
	public List<Menu> getAll(){
		try {
			String strQuery = " select p from Menu p order by p.ordem";
			TypedQuery<Menu> query = em.createQuery(strQuery, Menu.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Menu> getMenusPorPerfil(Perfil perfil) {
		try {
			String strQuery = " select m from Perfil p JOIN p.menus m "
					+ " where p = :perfil";
			TypedQuery<Menu> query = em.createQuery(strQuery, Menu.class);
			query.setParameter("perfil", perfil);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}

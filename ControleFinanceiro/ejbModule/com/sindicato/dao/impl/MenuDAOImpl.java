package com.sindicato.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.MenuDAO;
import com.sindicato.entity.autenticacao.Menu;

@Stateless
public class MenuDAOImpl extends DAOImpl<Menu, Integer> implements MenuDAO {

	@Override
	public List<Menu> getAll(){
		try {
			String strQuery = " select p from Menu p order by p.id";
			TypedQuery<Menu> query = em.createQuery(strQuery, Menu.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}

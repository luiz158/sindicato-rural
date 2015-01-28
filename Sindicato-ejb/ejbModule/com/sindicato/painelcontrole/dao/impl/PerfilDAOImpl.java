package com.sindicato.painelcontrole.dao.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.impl.DAOImpl;
import com.sindicato.painelcontrole.dao.PerfilDAO;
import com.sindicato.painelcontrole.entity.Acao;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Perfil;

@Stateful
public class PerfilDAOImpl extends DAOImpl<Perfil, Integer> implements PerfilDAO {

	@Override
	public List<Menu> getMenus(Perfil perfil) {
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

	@Override
	public List<Acao> getAcoes(Perfil perfil) {
		try {
			String strQuery = " select a from Perfil p JOIN p.acoes a "
					+ " where p = :perfil";
			TypedQuery<Acao> query = em.createQuery(strQuery, Acao.class);
			query.setParameter("perfil", perfil);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	} 
}
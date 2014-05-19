package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.RecebimentoDAO;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.TipoRecebimento;

public class RecebimentoDAOImpl implements RecebimentoDAO {

	private EntityManager em;
	
	public RecebimentoDAOImpl(EntityManager entityManager) {
		this.em = entityManager;
	}

	@Override
	public DestinoRecebimento getDestinoRecebimentoById(Integer id) {
		return em.find(DestinoRecebimento.class, id);
	}

	@Override
	public TipoRecebimento getTipoRecebimentoById(Integer id) {
		return em.find(TipoRecebimento.class, id);
	}

}

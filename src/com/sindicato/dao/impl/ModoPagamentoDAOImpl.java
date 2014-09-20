package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.entity.ModoPagamento;

public class ModoPagamentoDAOImpl extends DAOImpl<ModoPagamento, Integer> implements ModoPagamentoDAO {

	public ModoPagamentoDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
}

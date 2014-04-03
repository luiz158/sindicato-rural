package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.ServicoDAO;
import com.sindicato.entity.Servico;

public class ServicoDAOImpl extends DAOImpl<Servico, Integer> implements ServicoDAO {

	public ServicoDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
}

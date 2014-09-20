package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.DestinoRecebimentoDAO;
import com.sindicato.entity.DestinoRecebimento;

public class DestinoRecebimentoDAOImpl extends DAOImpl<DestinoRecebimento, Integer> implements DestinoRecebimentoDAO {

	public DestinoRecebimentoDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
}

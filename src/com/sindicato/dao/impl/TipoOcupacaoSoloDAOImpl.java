package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.TipoOcupacaoSoloDAO;
import com.sindicato.entity.TipoOcupacaoSolo;

public class TipoOcupacaoSoloDAOImpl extends DAOImpl<TipoOcupacaoSolo, Integer> implements TipoOcupacaoSoloDAO {

	public TipoOcupacaoSoloDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
}

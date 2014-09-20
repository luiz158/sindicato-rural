package com.sindicato.dao.impl;

import javax.persistence.EntityManager;

import com.sindicato.dao.EmpresaDAO;
import com.sindicato.entity.Empresa;

public class EmpresaDAOImpl extends DAOImpl<Empresa, Integer> implements EmpresaDAO {

	public EmpresaDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
}

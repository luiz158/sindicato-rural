package com.sindicato.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sindicato.dao.InformacaoSocioDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;

public class InformacaoSocioDAOImpl extends DAOImpl<InformacaoSocio, Integer> implements InformacaoSocioDAO {

	public InformacaoSocioDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<InformacaoSocio> buscarTodosPorCliente(Cliente cliente) {
		String strQuery = "select i from InformacaoSocio i " +
				"Where i.cliente = :cliente";
		TypedQuery<InformacaoSocio> query = em.createQuery(strQuery, InformacaoSocio.class);
		query.setParameter("cliente", cliente);
		return query.getResultList();
	}
}
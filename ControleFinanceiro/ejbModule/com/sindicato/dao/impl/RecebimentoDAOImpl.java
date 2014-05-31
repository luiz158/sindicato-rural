package com.sindicato.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sindicato.dao.RecebimentoDAO;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.TipoRecebimento;

@Stateless
public class RecebimentoDAOImpl implements RecebimentoDAO {

	@PersistenceContext(name="ControleFinanceiro")
	private EntityManager em;

	@Override
	public DestinoRecebimento getDestinoRecebimentoById(Integer id) {
		return em.find(DestinoRecebimento.class, id);
	}

	@Override
	public TipoRecebimento getTipoRecebimentoById(Integer id) {
		return em.find(TipoRecebimento.class, id);
	}

}

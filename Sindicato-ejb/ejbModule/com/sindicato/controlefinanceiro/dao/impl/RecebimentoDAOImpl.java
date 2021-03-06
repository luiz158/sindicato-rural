package com.sindicato.controlefinanceiro.dao.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sindicato.controlefinanceiro.dao.RecebimentoDAO;
import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;

@Stateful
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

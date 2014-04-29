package com.sindicato.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sindicato.dao.ListasDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.TipoRecebimento;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;

public class ListasDAOImpl implements ListasDAO {

	public ListasDAOImpl(EntityManager em) {
		this.em = em;
	}
	
	private EntityManager em;
	
	@Override
	public List<Cliente> getClientesComDebitosNoStatus(StatusDebitoEnum status) {
		try {
			String strQuery = "select DISTINCT(d.cliente) from Debito d where d.status = :status";
			TypedQuery<Cliente> query = em.createQuery(strQuery, Cliente.class);
			query.setParameter("status", status);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Debito> getDebitosDoClienteNoStatus(Cliente cliente,
			StatusDebitoEnum status) {
		try {
			String strQuery = " select d from Debito d where d.status = :status and d.cliente = :cliente ";
			TypedQuery<Debito> query = em.createQuery(strQuery, Debito.class);
			query.setParameter("status", status);
			query.setParameter("cliente", cliente);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Debito> getTodasNotasDeCobranca() {
		try {
			String strQuery = "select d from Debito d where d.status = :status";
			TypedQuery<Debito> query = em.createQuery(strQuery, Debito.class);
			query.setParameter("status", StatusDebitoEnum.NOTAFISCALGERADA);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<TipoRecebimento> getTodasFormasRecebimento() {
		try {
			TypedQuery<TipoRecebimento> query = em.createQuery("select r from TipoRecebimento r", TipoRecebimento.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<DestinoRecebimento> getTodosDestinosRecebimento() {
		try {
			TypedQuery<DestinoRecebimento> query = em.createQuery("select r from DestinoRecebimento r", DestinoRecebimento.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<DebitoServico> getTodosOsServicosComRetencaoDoDebito(
			Debito debito) {
		try {
			String strQuery = "select d from DebitoServico d where d.debito = :debito and d.servico.retencao = :retencao";
			
			TypedQuery<DebitoServico> query = em.createQuery(strQuery, DebitoServico.class);
			query.setParameter("debito", debito);
			query.setParameter("retencao", true);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Perfil> getTodosOsPerfis() {
		try {
			String strQuery = " select p from Perfil p ";
			TypedQuery<Perfil> query = em.createQuery(strQuery, Perfil.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Menu> getTodosOsMenus() {
		try {
			String strQuery = " select p from Menu p ";
			TypedQuery<Menu> query = em.createQuery(strQuery, Menu.class);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}

package com.sindicato.contasapagar.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.result.ResultOperation;

@Stateless
public class BancoDAOImpl implements BancoDAO { 

	@PersistenceContext(name="ControleFinanceiro")
	protected EntityManager em;
	
	public BancoDAOImpl(){ }
	public BancoDAOImpl(EntityManager em){ 
		this.em = em; 
	}
	
	@Override
	public ResultOperation cadastrar(Banco banco) {
		ResultOperation result = new ResultOperation();
		try {
			em.persist(banco);
			result.setMessage("Banco cadastrado com sucesso");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro, por favor contate o administrador.");
			result.setSuccess(false);
		}
		return result;
	}
	@Override
	public ResultOperation alterar(Banco banco) {
		ResultOperation result = new ResultOperation();
		try {
			em.merge(banco);
			result.setMessage("Banco alterado com sucesso");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro, por favor contate o administrador.");
			result.setSuccess(false);
		}
		return result;
	}
	@Override
	public ResultOperation excluir(Banco banco) {
		ResultOperation result = new ResultOperation();
		try {
			banco.setExcluido(true);
			em.merge(banco);
			result.setMessage("Banco excluído com sucesso");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro, por favor contate o administrador.");
			result.setSuccess(false);
		}
		return result;
	}
	@Override
	public List<Banco> getAll() {
		String jpql = "select b from Banco b "
				+ " where b.excluido = :excluido ";
		
		TypedQuery<Banco> query = em.createQuery(jpql, Banco.class);
		query.setParameter("excluido", false);
		return query.getResultList();
	}
	@Override
	public Banco searchByID(Integer id) {
		String jpql = "select b from Banco b "
				+ " where b.excluido = :excluido and b.id = :id";
		
		TypedQuery<Banco> query = em.createQuery(jpql, Banco.class);
		query.setParameter("excluido", false);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
}
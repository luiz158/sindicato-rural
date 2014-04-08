package com.sindicato.dao.impl;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.result.ResultOperation;

public class FinanceiroDAOImpl implements FinanceiroDAO {

	private EntityManager em = null;
	
	public FinanceiroDAOImpl(EntityManager em) {
		this.em = em;
	}
	
	private boolean existirDebitoParaClienteNaMesmaDataBase(Cliente cliente, Calendar dataBase){
		String strQuery = "select COUNT(d) from Debito d " +
				"Where d.cliente = :cliente AND d.dataBase = :dataBase";
		
		TypedQuery<Long> query = em.createQuery(strQuery, Long.class);
		query.setParameter("cliente", cliente);
		query.setParameter("dataBase", dataBase);
		
		if(query.getSingleResult() > 0){
			return true;
		}
		return false;
	}
	private ResultOperation validarDebito(Debito debito){
		ResultOperation result = new ResultOperation();
		if(debito.getDataBase() == null){
			result.setSuccess(false);
			result.setMessage("Data base do débito é obrigatório.");
			return result;
		}
		if(debito.getServicos() == null || debito.getServicos().size() == 0){
			result.setSuccess(false);
			result.setMessage("Os serviços do debito devem ser declarados.");
			return result;
		}
		if(existirDebitoParaClienteNaMesmaDataBase(debito.getCliente(), debito.getDataBase())){
			result.setSuccess(false);
			result.setMessage("Cliente ja possui Debito nesta data base.");
			return result;
		}
		return result;
	}
	
	@Override
	public ResultOperation gravarDebito(Debito debito) {
		ResultOperation result = validarDebito(debito);
		if(!result.isSuccess()){
			return result;
		}
		
		try {
			em.getTransaction().begin();
			em.persist(debito);
			em.getTransaction().commit();
			result.setSuccess(true);
		} catch (Exception e) {
			em.getTransaction().rollback();
			result.setSuccess(false);
		}
		return result;
	}

}

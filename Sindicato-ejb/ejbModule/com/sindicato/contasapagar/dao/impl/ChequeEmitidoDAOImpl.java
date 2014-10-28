package com.sindicato.contasapagar.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.result.ResultOperation;

@Stateless
public class ChequeEmitidoDAOImpl implements ChequeEmitidoDAO {

	@PersistenceContext(name="ControleFinanceiro")
	protected EntityManager em;
	
	public ChequeEmitidoDAOImpl() { } 
	public ChequeEmitidoDAOImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public ResultOperation emitirCheque(ChequeEmitido cheque) {
		ResultOperation result = new ResultOperation();
		
		if(cheque.getContasPagas() == null || cheque.getContasPagas().size() == 0){
			result.setMessage("Selecione alguma conta para pagamento");
			result.setSuccess(false);
			return result;
		}
		
		try {
			cheque.setCancelado(false);
			em.persist(cheque);
			result.setMessage("Cheque emitido com sucesso.");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro ao gerar o cheque, por favor contate o administrador.");
			result.setSuccess(false);
		}
		
		return result;
	}
	@Override
	public ResultOperation cancelarCheque(ChequeEmitido cheque) {
		ResultOperation result = new ResultOperation();
		try {
			cheque.setCancelado(true);
			em.merge(cheque);
			result.setMessage("Cheque cancelado com sucesso.");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro ao cancelar o cheque, por favor contate o administrador.");
			result.setSuccess(false);
		}
		return result;
	}
	@Override
	public Long getNumeroUltimoChequeEmitido(Banco banco) {
		String jpql = "select MAX(ce.identificacao) from ChequeEmitido ce "
				+ " where ce.cancelado = :cancelado and ce.banco = :banco ";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("cancelado", false);
		query.setParameter("banco", banco);
		return query.getSingleResult();
	} 
}
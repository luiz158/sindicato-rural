package com.sindicato.contasapagar.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.result.ResultOperation;

@Stateless
public class ContaDAOImpl implements ContaDAO {

	@PersistenceContext(name="ControleFinanceiro")
	protected EntityManager em;

	public ContaDAOImpl(EntityManager em) {
		this.em = em;
	}
	public ContaDAOImpl() { } 
	
	@Override
	public ResultOperation cadastrar(Conta conta) {
		ResultOperation result = new ResultOperation();
		try {
			em.persist(conta);
			result.setMessage("Conta cadastrada com sucesso");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro, por favor contate o administrador.");
			result.setSuccess(false);
		}
		return result;
	}
	
	private ResultOperation validaAlteracaoConta(Conta conta){
		ResultOperation result = new ResultOperation();
		result.setSuccess(true);
		if(!conta.isDebitoConta() && conta.jaEstaPaga()){
			result.setSuccess(false);
			result.setMessage("Conta ja foi paga com cheque, não pode ser alterada.");
		}
		return result;
	}
	
	@Override
	public ResultOperation alterar(Conta conta) {
		ResultOperation validar = this.validaAlteracaoConta(conta);
		if(!validar.isSuccess()){
			return validar;
		}
		
		ResultOperation result = new ResultOperation();
		try {
			em.merge(conta);
			result.setMessage("Conta alterada com sucesso");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro, por favor contate o administrador do sistema.");
			result.setSuccess(false);
		}
		return result;
	}
	@Override
	public ResultOperation remover(Conta conta) {
		ResultOperation validar = this.validaExclusaoConta(conta);
		if(!validar.isSuccess()){
			return validar;
		}
		
		ResultOperation result = new ResultOperation();
		try {
			conta.setExcluida(true);
			em.merge(conta);
			result.setMessage("Conta excluída com sucesso");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro, por favor contate o administrador do sistema.");
			result.setSuccess(false);
		}
		return result;
	}
	private ResultOperation validaExclusaoConta(Conta conta) {
		ResultOperation result = new ResultOperation();
		result.setSuccess(true);
		if(!conta.isDebitoConta() && conta.jaEstaPaga()){
			result.setSuccess(false);
			result.setMessage("Conta ja foi paga com cheque, não pode ser excluida.");
		}
		return result;
	}
	
	@Override
	public List<Conta> listarContas(){
		String jpql = "select distinct c from Conta c "
				+ " left join fetch c.chequesPagamento "
				+ " where c.excluida = :excluida ";
		
		TypedQuery<Conta> query = em.createQuery(jpql, Conta.class);
		query.setParameter("excluida", false);
		return query.getResultList();
	}

	@Override
	public List<Conta> getContasPendentes() {
		String jpql = "select distinct c from Conta c "
				+ " where c.excluida = :excluida and c.debitoConta = :debito"
				+ " and c not in ( "
				+ " 	select contas from ChequeEmitido ce "
				+ "		join ce.contasPagas contas "
				+ "		where ce.cancelado = :chequeCancelado "
				+ " )"
				+ " order by c.vencimento desc";
	
		TypedQuery<Conta> query = em.createQuery(jpql, Conta.class);
		query.setParameter("excluida", false);
		query.setParameter("debito", false);
		query.setParameter("chequeCancelado", false);
		return query.getResultList();
	}
	
	@Override
	public Conta searchByID(int id) {
		Conta conta = em.find(Conta.class, id);
		return conta;
	}

}
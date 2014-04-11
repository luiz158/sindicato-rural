package com.sindicato.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.Enum.StatusDebitoEnum;
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
		if(debito.getDebitoServicos() == null || debito.getDebitoServicos().size() == 0){
			result.setSuccess(false);
			result.setMessage("Os serviços do debito devem ser declarados.");
			return result;
		}
		if(existirDebitoParaClienteNaMesmaDataBase(debito.getCliente(), debito.getDataBase())){
			result.setSuccess(false);
			result.setMessage("Cliente ja possui Debito nesta data base.");
			return result;
		}
		result.setSuccess(true);
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
			
			if(debito.getId() == 0){
				em.persist(debito);
			}else{
				em.merge(debito);
			}
			
			em.getTransaction().commit();
			result.setMessage("Debito foi gravado com sucesso.");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			result.setSuccess(false);
			result.setMessage("Debito não foi gravado com sucesso. Contate o administrados do sistema.");
		}
		return result;
	}

	private ResultOperation alterarStatusDoDebitoPara(Debito debito,
			StatusDebitoEnum status) {
		ResultOperation result = new ResultOperation();
		try {
			debito.setStatus(status);
			
			em.getTransaction().begin();
			em.merge(debito);
			em.getTransaction().commit();
			
			result.setSuccess(true);
			result.setMessage("Status do debito foi alterado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			result.setSuccess(false);
			result.setMessage("Status do debito não foi alterado com sucesso. Contate o administrador do sistema.");
		}
		return result;
	}

	@Override
	public ResultOperation gerarNotaDeCobranca(Debito debito) {
		debito.setDataEmissaoNotaCobranca(Calendar.getInstance());
		ResultOperation result = this.alterarStatusDoDebitoPara(debito, StatusDebitoEnum.NOTAFISCALGERADA);
		if(result.isSuccess()){
			result.setMessage("Nota de cobrança gerada com sucesso.");
		}else{
			result.setMessage("Nota de cobrança não foi gerada com sucesso.");
		}
		return result;
	}

	@Override
	public ResultOperation cancelarNotaDeCobranca(Debito debito) {
		ResultOperation result = this.alterarStatusDoDebitoPara(debito, StatusDebitoEnum.CANCELADO);
		if(result.isSuccess()){
			result.setMessage("Nota de cobrança foi cancelada com sucesso.");
		}else{
			result.setMessage("Nota de cobrança não foi cancelada com sucesso.");
		}
		return result;
	}

	@Override
	public ResultOperation registrarRecebimento(Debito debito) {
		debito.setStatus(StatusDebitoEnum.RECEBIDO);
		ResultOperation result = this.gravarDebito(debito);
		if(result.isSuccess()){
			result.setMessage("Recebimento registrado com sucesso.");
		}else{
			result.setMessage("Recebimento não foi registrado com sucesso.");
		}
		return result;
	}

	@Override
	public ResultOperation registrarRecolhimentos(Debito debito,
			List<DebitoServico> servicos) {
		ResultOperation result = new ResultOperation();
		try {
			debito.setStatus(StatusDebitoEnum.RECOLHIDO);
			em.getTransaction().begin();
			em.merge(debito);
			for (DebitoServico debitoServico : servicos) {
				em.merge(debitoServico);
			}
			em.getTransaction().commit();
			
			result.setSuccess(true);
			result.setMessage("Recolhimentos gravados com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("Recolhimentos não foram gravados com sucesso. Contate o administrador do sistema.");
		}
		return result;
	}

	

	

}

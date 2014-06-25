package com.sindicato.dao.impl;

import java.util.Calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.ResultOperation;

@Stateless
public class FinanceiroDAOImpl implements FinanceiroDAO {

	@PersistenceContext(name="ControleFinanceiro")
	private EntityManager em;
	
	private boolean existirDebitoParaClienteNaMesmaDataBase(Cliente cliente, Calendar dataBase){
		String strQuery = "select COUNT(d) from Debito d " +
				"Where d.cliente = :cliente AND d.dataBase = :dataBase AND d.status != :cancelado";
		
		TypedQuery<Long> query = em.createQuery(strQuery, Long.class);
		query.setParameter("cliente", cliente);
		query.setParameter("dataBase", dataBase);
		query.setParameter("cancelado", StatusDebitoEnum.CANCELADO);
		
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
		if(debito.getId() == 0 && existirDebitoParaClienteNaMesmaDataBase(debito.getCliente(), debito.getDataBase())){
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
			if(debito.getId() == 0){
				em.persist(debito);
			}else{
				em.merge(debito);
			}
			
			result.setMessage("Debito foi gravado com sucesso.");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
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
			em.merge(debito);
			result.setSuccess(true);
			result.setMessage("Status do debito foi alterado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("Status do debito não foi alterado com sucesso. Contate o administrador do sistema.");
		}
		return result;
	}

	@Override
	public ResultOperation gerarNotaDeCobranca(Debito debito) {
		if(debito.getDataEmissaoNotaCobranca() == null){
			debito.setDataEmissaoNotaCobranca(Calendar.getInstance());
		}
		if(debito.getNumeroNota() == 0){
			String jpql = "select MAX(d.numeroNota) from Debito d ";
			int ultimaNota = (Integer) em.createQuery(jpql).getSingleResult();
			debito.setNumeroNota(++ultimaNota);
		}
		ResultOperation result = this.alterarStatusDoDebitoPara(debito, StatusDebitoEnum.NOTACOBRANCAGERADA);
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
	public ResultOperation registrarRecolhimentos(Debito debito) {
		debito.setStatus(StatusDebitoEnum.RECOLHIDO);
		ResultOperation result = this.gravarDebito(debito);
		if(result.isSuccess()){
			result.setMessage("Recolhimentos gravados com sucesso.");
		}else{
			result.setMessage("Recolhimentos não foram gravados com sucesso. Contate o administrador do sistema.");
		}
		return result;
	}

	@Override
	public ResultOperation salvarAlteracaoNotaCobranca(Debito debito) {
		ResultOperation result = this.gravarDebito(debito);
		if(result.isSuccess()){
			result.setMessage("Nota de cobrança alterada com sucesso.");
		}else{
			result.setMessage("Nota de cobrança não foi alterada com sucesso. Contate o administrador do sistema.");
		}
		return result;
	}

}

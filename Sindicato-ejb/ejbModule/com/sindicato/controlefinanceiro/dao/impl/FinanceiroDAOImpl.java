package com.sindicato.controlefinanceiro.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.controlefinanceiro.dao.FinanceiroDAO;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.DebitoServico;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.ResultOperation;

@Stateless
public class FinanceiroDAOImpl implements FinanceiroDAO {

	@PersistenceContext(name="ControleFinanceiro")
	private EntityManager em;
	
	private boolean existirDebitoParaClienteNaMesmaDataBase(Debito debito){
		String strQuery = "select COUNT(d) from Debito d " +
				"Where d.cliente = :cliente "
				+ "AND d.dataBase = :dataBase "
				+ "AND d.status != :cancelado "
				+ "AND d.id <> :id";
		
		TypedQuery<Long> query = em.createQuery(strQuery, Long.class);
		query.setParameter("cliente", debito.getCliente());
		query.setParameter("dataBase", debito.getDataBase());
		query.setParameter("cancelado", StatusDebitoEnum.CANCELADO);
		query.setParameter("id", debito.getId());
		
		if(query.getSingleResult() > 0){
			return true;
		}
		return false;
	}
	
	private ResultOperation validarDebito(Debito debito){
		ResultOperation result = new ResultOperation();
		if(debito.getDataBase() == null){
			result.setSuccess(false);
			result.setMessage("Data base do d�bito � obrigat�rio.");
			return result;
		}
		if(debito.getDebitoServicos() == null || debito.getDebitoServicos().size() == 0){
			result.setSuccess(false);
			result.setMessage("Os servi�os do debito devem ser declarados.");
			return result;
		}
		if(existirDebitoParaClienteNaMesmaDataBase(debito)){
			result.setSuccess(false);
			result.setMessage("Cliente ja possui Debito nesta data base.");
			return result;
		}
		
		// n�o permite repetir mesmo servi�o para o d�bito
		for (DebitoServico debitoServico : debito.getDebitoServicos()) {
			if(debitoServico.getServico().isMensalidade()){
				continue;
			}
			
			for (DebitoServico debitoServico2 : debito.getDebitoServicos()) {
				if(debitoServico2.equals(debitoServico)){ 
					continue;
				}
				
				if(debitoServico.getServico().equals(debitoServico2.getServico())){
					result.setSuccess(false);
					result.setMessage("Nota n�o foi salva com sucesso, pois possui dois servi�os repetidos.");
					return result;
				}
				
				if(debitoServico.getValor() == null || 
						debitoServico.getValor().equals(BigDecimal.ZERO) ||
						debitoServico.getValor().toString().equals("0.00")
				){
					result.setSuccess(false);
					result.setMessage("Nota n�o foi salva com sucesso. Possui algum servi�o com valor zerado");
					return result;
				}
				
			}
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
			result.setMessage("Debito n�o foi gravado com sucesso. Contate o administrados do sistema.");
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
			result.setMessage("Status do debito n�o foi alterado com sucesso. Contate o administrador do sistema.");
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
			result.setMessage("Nota de cobran�a gerada com sucesso.");
		}else{
			result.setMessage("Nota de cobran�a n�o foi gerada com sucesso.");
		}
		return result;
	}

	@Override
	public ResultOperation cancelarNotaDeCobranca(Debito debito) {
		ResultOperation result = this.alterarStatusDoDebitoPara(debito, StatusDebitoEnum.CANCELADO);
		if(result.isSuccess()){
			result.setMessage("Nota de cobran�a foi cancelada com sucesso.");
		}else{
			result.setMessage("Nota de cobran�a n�o foi cancelada com sucesso.");
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
			result.setMessage("Recebimento n�o foi registrado com sucesso.");
		}
		return result;
	}

	@Override
	public ResultOperation registrarRecolhimentos(Debito debito) {
		
		boolean alteraStatus = true;
		for (DebitoServico debitoServico : debito.getDebitoServicos()) {
			if(debitoServico.getServico().isRetencao()){
				if(debitoServico.getRecolhimento().getValor().compareTo(BigDecimal.ZERO) == 0){
					alteraStatus = false;
					break;
				}
			}
		}
		
		if(alteraStatus){
			debito.setStatus(StatusDebitoEnum.RECOLHIDO);
		}
		
		ResultOperation result = this.gravarDebito(debito);
		if(result.isSuccess()){
			result.setMessage("Recolhimentos gravados com sucesso.");
		}else{
			result.setMessage("Recolhimentos n�o foram gravados com sucesso. Contate o administrador do sistema.");
		}
		return result;
	}

	@Override
	public ResultOperation salvarAlteracaoNotaCobranca(Debito debito) {
		ResultOperation result = this.gravarDebito(debito);
		if(result.isSuccess()){
			result.setMessage("Nota de cobran�a alterada com sucesso.");
		}
		return result;
	}

}

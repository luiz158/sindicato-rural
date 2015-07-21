package com.sindicato.contasapagar.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.contasapagar.report.model.FiltroRelatorioCheques;
import com.sindicato.contasapagar.report.model.RelatorioCheques;
import com.sindicato.report.util.FiltroBooleanEnum;
import com.sindicato.result.ResultOperation;
import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

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
				+ " where ce.banco = :banco ";
		TypedQuery<Long> query = em.createQuery(jpql, Long.class);
		query.setParameter("banco", banco);
		
		Long numeroUltimoChequeEmitido = query.getSingleResult();
		if(numeroUltimoChequeEmitido == null){
			numeroUltimoChequeEmitido = 0L;
		}
		
		return numeroUltimoChequeEmitido;
	}
	@Override
	public List<ChequeEmitido> listarCheques() {
		String jpql = "select distinct c from ChequeEmitido c "
				+ " join fetch c.contasPagas "
				+ " order by c.id desc";
		TypedQuery<ChequeEmitido> query = em.createQuery(jpql, ChequeEmitido.class);
		return query.getResultList();
	}
	
	@Override
	public RelatorioCheques getRelatorioCheques(FiltroRelatorioCheques filtro) {
		RelatorioCheques relatorio = new RelatorioCheques();
		relatorio.setFiltro(filtro);
		
		EasyCriteria<ChequeEmitido> criteria = EasyCriteriaFactory.createQueryCriteria(em, ChequeEmitido.class);
		criteria.leftJoinFetch("contasPagas");
		criteria.setDistinctTrue();
		criteria.orderByDesc("emissao");
		
		this.preencheFiltrosRelatorio(criteria, filtro);
		relatorio.setResultado(criteria.getResultList());
		return relatorio;
	}
	private void preencheFiltrosRelatorio(EasyCriteria<ChequeEmitido> criteria, FiltroRelatorioCheques filtro){
		
		// banco 
		if(filtro.getBancos() != null && filtro.getBancos().size() > 0){
			for (Banco banco : filtro.getBancos()) {
				criteria.orEquals(1, "banco", banco);
			}
		}
		// cancelado
		if(!filtro.getCancelado().equals(FiltroBooleanEnum.TODOS)){
			criteria.andEquals("cancelado", (filtro.getCancelado().equals(FiltroBooleanEnum.SIM)));
		}
		// emissaoDe
		if(filtro.getEmissaoDe() != null){
			criteria.andGreaterOrEqualTo("emissao", filtro.getEmissaoDe());
		}
		// emissaoAte
		if(filtro.getEmissaoAte() != null){
			criteria.andLessOrEqualTo("emissao", filtro.getEmissaoAte());
		}		
		// valorDe
		if(filtro.getValorDe() != null && filtro.getValorDe().compareTo(BigDecimal.ZERO) != 0){
			criteria.andGreaterOrEqualTo("valor", filtro.getValorDe());
		}
		// valorAte
		if(filtro.getValorAte() != null && filtro.getValorAte().compareTo(BigDecimal.ZERO) != 0){
			criteria.andLessOrEqualTo("valor", filtro.getValorAte());
		}
		// favorecido
		if(filtro.getFavorecido() != null && filtro.getFavorecido() != ""){
			criteria.andStringLike("favorecido", "%" + filtro.getFavorecido() + "%");
		}
		// versoCheque
		if(filtro.getVersoCheque() != null && filtro.getVersoCheque() != ""){
			criteria.andStringLike("versoCheque", "%" + filtro.getVersoCheque() + "%");
		}
		// identificacao
		if(filtro.getIdentificacao() != null && filtro.getIdentificacao() > 0){
			criteria.andEquals("identificacao", filtro.getIdentificacao());
		}
	}

	@Override
	public ChequeEmitido getChequePorId(int id) {
		String jpql = "select distinct c from ChequeEmitido c "
				+ " join fetch c.contasPagas "
				+ " where c.id = :id";
		TypedQuery<ChequeEmitido> query = em.createQuery(jpql, ChequeEmitido.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	@Override
	public ResultOperation salvarCheque(ChequeEmitido cheque) {
		ResultOperation result = new ResultOperation();
		try {
			em.merge(cheque);
			result.setMessage("Cheque salvo com sucesso.");
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("Erro ao salvar o cheque, por favor contate o administrador.");
			result.setSuccess(false);
		}
		return result;
	}
}
package com.sindicato.contas.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.dao.impl.BancoDAOImpl;
import com.sindicato.contasapagar.dao.impl.ChequeEmitidoDAOImpl;
import com.sindicato.contasapagar.dao.impl.ContaDAOImpl;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.result.ResultOperation;

public class TestContas {

	public EntityManager em;
	public ContaDAO contaDAO;
	public BancoDAO bancoDAO;
	public ChequeEmitidoDAO chequeDAO;
	
	
	@Before
	public void before(){
		EntityManagerFactory emFactory = EntityManagerFactorySingleton.getInstance();
		em = emFactory.createEntityManager();
		em.getTransaction().begin();
		
		contaDAO = new ContaDAOImpl(em);
		bancoDAO = new BancoDAOImpl(em);
		chequeDAO = new ChequeEmitidoDAOImpl(em);
	}
	
	@After
	public void after(){
		em.getTransaction().commit();
	}
	
	private Banco getBanco(){
		List<Banco> bancos = bancoDAO.getAll();
		Banco banco = new Banco();
		if(bancos.size() == 0){
			banco = new Banco();
			banco.setCodigo(237);
			banco.setDescricao("Bradesco");
			
			bancoDAO.cadastrar(banco);
		} else {
			banco = bancos.get(0);
		}
		return banco;
	}
	
	private Conta getConta(){
		Conta conta = new Conta();
		conta.setFavorecido("favorecido");
		conta.setHistorico("historico");
		conta.setValor(BigDecimal.TEN);
		return conta;
	}
	private Conta criaContaDebitoContaVencida(){
		Conta conta = this.getConta();
		conta.setDebitoConta(true);
		
		Calendar vencimento = Calendar.getInstance();
		vencimento.add(Calendar.MONTH, -1);
		conta.setVencimento(vencimento);
		contaDAO.cadastrar(conta);
		return conta;
	}
	private Conta criaContaDebitoContaNaoVencida() {
		Conta conta = this.getConta();
		conta.setDebitoConta(true);
		
		Calendar vencimento = Calendar.getInstance();
		vencimento.add(Calendar.MONTH, 1);
		conta.setVencimento(vencimento);
		
		contaDAO.cadastrar(conta);
		
		return conta;
	}
	private Conta criaContaChequeNaoPaga(){
		Conta conta = this.getConta();
		conta.setVencimento(Calendar.getInstance());
		contaDAO.cadastrar(conta);
		return conta;
	}
	private Conta criaContaChequePagamento(){
		Conta conta = this.getConta();
		conta.setVencimento(Calendar.getInstance());
		
		contaDAO.cadastrar(conta);

		ChequeEmitido cheque = new ChequeEmitido();
		cheque.setBanco(this.getBanco());
		cheque.setEmissao(Calendar.getInstance());
		cheque.setFavorecido("Favorecido");
		cheque.setIdentificacao(new Long(12345678));
		cheque.setValor(BigDecimal.TEN);
		cheque.setVersoCheque("versoCheque");
		
		conta.addChequePagamento(cheque);
		cheque.addConta(conta);
		
		ResultOperation result = chequeDAO.emitirCheque(cheque);
		Assert.assertTrue(result.isSuccess());
		return conta;
	}
	private Conta criaContaChequeCancelado() {
		Conta contaPaga = this.criaContaChequePagamento();
		chequeDAO.cancelarCheque(contaPaga.getChequesPagamento().get(0));
		return contaPaga;
	}
	
	@Test
	public void testEmitirCheque(){
		Conta conta = this.getConta();
		conta.setVencimento(Calendar.getInstance());
		contaDAO.cadastrar(conta);

		ChequeEmitido cheque = new ChequeEmitido();
		cheque.setBanco(this.getBanco());
		cheque.setEmissao(Calendar.getInstance());
		cheque.setFavorecido("Favorecido");
		cheque.setIdentificacao(new Long(12345678));
		cheque.setValor(BigDecimal.TEN);
		cheque.setVersoCheque("versoCheque");
		cheque.addConta(conta);
		
		ResultOperation result = chequeDAO.emitirCheque(cheque);
		Assert.assertTrue(result.isSuccess());
	}
	@Test
	public void testContaPaga() {
		// conta em debito automatido depois do vencimento
		Conta debitoContaVencida = this.criaContaDebitoContaVencida();
		Assert.assertTrue(debitoContaVencida.jaEstaPaga());

		// conta em debito automatico antes do vencimento
		Conta debitoContaNaoVencida = this.criaContaDebitoContaNaoVencida();
		Assert.assertFalse(debitoContaNaoVencida.jaEstaPaga());
		
		// conta com cheque 
		Conta chequeNaoPaga = this.criaContaChequeNaoPaga();
		Assert.assertFalse(chequeNaoPaga.jaEstaPaga());
		
		// conta com cheque pagamento
		Conta contaPaga = this.criaContaChequePagamento();
		Assert.assertTrue(contaPaga.jaEstaPaga());
		
		// conta com cheque cancelado
		Conta contaChequeCancelado = this.criaContaChequeCancelado();
		Assert.assertFalse(contaChequeCancelado.jaEstaPaga());
		
	}

	@Test
	public void testAlteracaoConta(){
		ResultOperation result;
		Conta contaDebitoContaVencida = this.criaContaDebitoContaVencida();
		contaDebitoContaVencida.setValor(BigDecimal.ONE);
		result = contaDAO.alterar(contaDebitoContaVencida);
		Assert.assertTrue(result.isSuccess());
		
		Conta contaDebitoContaNaoVencida = this.criaContaDebitoContaNaoVencida();
		contaDebitoContaNaoVencida.setValor(BigDecimal.ONE);
		result = contaDAO.alterar(contaDebitoContaNaoVencida);
		Assert.assertTrue(result.isSuccess());
		
		Conta contaChequePagamento = this.criaContaChequePagamento();
		contaChequePagamento.setValor(BigDecimal.ONE);
		result = contaDAO.alterar(contaChequePagamento);
		Assert.assertFalse(result.isSuccess());
		
		Conta contaChequeNaoPaga = this.criaContaChequeNaoPaga();
		contaChequeNaoPaga.setValor(BigDecimal.ONE);
		result = contaDAO.alterar(contaChequeNaoPaga);
		Assert.assertTrue(result.isSuccess());
		
		Conta contaChequeCancelado = this.criaContaChequeCancelado();
		contaChequeCancelado.setValor(BigDecimal.ONE);
		result = contaDAO.alterar(contaChequeCancelado);
		Assert.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testExclusaoConta(){
		ResultOperation result;
		Conta contaDebitoContaVencida = this.criaContaDebitoContaVencida();
		result = contaDAO.remover(contaDebitoContaVencida);
		Assert.assertTrue(result.isSuccess());
		
		Conta contaDebitoContaNaoVencida = this.criaContaDebitoContaNaoVencida();
		result = contaDAO.remover(contaDebitoContaNaoVencida);
		Assert.assertTrue(result.isSuccess());
		
		Conta contaChequePagamento = this.criaContaChequePagamento();
		result = contaDAO.remover(contaChequePagamento);
		Assert.assertFalse(result.isSuccess());
		
		Conta contaChequeNaoPaga = this.criaContaChequeNaoPaga();
		result = contaDAO.remover(contaChequeNaoPaga);
		Assert.assertTrue(result.isSuccess());
		
		Conta contaChequeCancelado = this.criaContaChequeCancelado();
		result = contaDAO.remover(contaChequeCancelado);
		Assert.assertTrue(result.isSuccess());
		
	}
	
	@Test
	public void testUltimoChequeEmitido(){
		Conta conta = this.getConta();
		conta.setVencimento(Calendar.getInstance());
		contaDAO.cadastrar(conta);

		ChequeEmitido cheque = new ChequeEmitido();
		cheque.setBanco(this.getBanco());
		cheque.setEmissao(Calendar.getInstance());
		cheque.setFavorecido("Favorecido");
		cheque.setIdentificacao(new Long(99999999));
		cheque.setValor(BigDecimal.TEN);
		cheque.setVersoCheque("versoCheque");
		cheque.addConta(conta);
		
		ResultOperation result = chequeDAO.emitirCheque(cheque);
		Assert.assertTrue(result.isSuccess());

		conta = this.getConta();
		conta.setVencimento(Calendar.getInstance());
		contaDAO.cadastrar(conta);

		cheque = new ChequeEmitido();
		cheque.setBanco(this.getBanco());
		cheque.setEmissao(Calendar.getInstance());
		cheque.setFavorecido("Favorecido");
		cheque.setIdentificacao(new Long(11111111));
		cheque.setValor(BigDecimal.TEN);
		cheque.setVersoCheque("versoCheque");
		cheque.addConta(conta);
		
		result = chequeDAO.emitirCheque(cheque);
		Assert.assertTrue(result.isSuccess());

		Long numeroUltimoChequeEmitido = chequeDAO.getNumeroUltimoChequeEmitido(this.getBanco());
		Assert.assertEquals(numeroUltimoChequeEmitido, new Long(99999999));
		
	}
	
	@Test
	public void testListaContasPendentes(){
		
		// remove todas as contas ja criadas em outros testes
		List<Conta> contas = contaDAO.listarContas();
		for (Conta conta : contas) {
			contaDAO.remover(conta);
		}
		
		List<Conta> contasPendentes = contaDAO.getContasPendentes();
		Assert.assertEquals(contasPendentes.size(), 6);
	}
	
}

package com.sindicato.test;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ServicoDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.ResultOperation;

public class TestCadastros {

	private EntityManager em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();

	private ClienteDAO clienteDAO = new ClienteDAOImpl(em);

	@BeforeClass
	public static void popula() {
		PreencheClasses.populaBanco(EntityManagerFactorySingleton.getInstance()
				.createEntityManager());
	}

	@Test
	public void pegarUltimoRegistroDeSocio() {
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(cliente.isSocio(),
				false);

		cliente.setSocio(true);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.searchByID(cliente.getId()).isSocio(),
				true);

		cliente.setSocio(false);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.searchByID(cliente.getId()).isSocio(),
				false);

		cliente.setSocio(true);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.searchByID(cliente.getId()).isSocio(),
				true);
		
		Assert.assertEquals(clienteDAO.getAll().get(0).isSocio(), true);
	}

	@Test
	public void cadastraERecuperacaoOcupacaoSolo() {
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(cliente.getEstabelecimentoRural()
				.getOcupacoesSolo().size(), 2);
	}
	
	@Test
	public void gravarDebitoDoCliente(){
		FinanceiroDAO financeiroDAO = new FinanceiroDAOImpl(em);
		ServicoDAO servicoDAO = new ServicoDAOImpl(em);
		ResultOperation result;
		
		// debito do cliente
		Debito debito = new Debito();
		debito.setCliente(clienteDAO.getAll().get(0));
		debito.setStatus(StatusDebitoEnum.DEBITOCRIADO);

		// valida debito sem data base
		result = financeiroDAO.gravarDebito(debito);
		Assert.assertEquals(result.isSuccess(), false);
		
		debito.setDataBase(Calendar.getInstance());

		// valida o debito sem servicos cadastrado
		result = financeiroDAO.gravarDebito(debito);
		Assert.assertEquals(result.isSuccess(), false);
		
		// preenche os servicos do debito
		DebitoServico servicoDebito = new DebitoServico();
		servicoDebito.setServico(servicoDAO.getAll().get(0));
		servicoDebito.setValor(BigDecimal.TEN);
		
		servicoDebito.setDebito(debito);
		debito.getDebitoServicos().add(servicoDebito);
		
		// agora ele salva o debito completo
		result = financeiroDAO.gravarDebito(debito);
		Assert.assertEquals(result.isSuccess(), true);
		
		// testa dois debitos na mesma data base
		debito = new Debito();
		debito.setDataBase(Calendar.getInstance());
		debito.setCliente(clienteDAO.getAll().get(0));
		debito.setStatus(StatusDebitoEnum.DEBITOCRIADO);

		servicoDebito = new DebitoServico();
		servicoDebito.setServico(servicoDAO.getAll().get(0));
		servicoDebito.setValor(BigDecimal.TEN);
		
		servicoDebito.setDebito(debito);
		debito.getDebitoServicos().add(servicoDebito);
		
		result = financeiroDAO.gravarDebito(debito);
		Assert.assertEquals(result.isSuccess(), false);
		
	}
	
	@Test
	public void somaDosServicosDebito(){
		// testa a soma dos serviços
		DebitoServico serv1 = new DebitoServico();
		serv1.setValor(BigDecimal.TEN);

		DebitoServico serv2 = new DebitoServico();
		serv2.setValor(BigDecimal.ONE);

		Debito debito = new Debito();
		debito.setDataBase(Calendar.getInstance());
		
		debito.getDebitoServicos().add(serv1);
		serv1.setDebito(debito);
		debito.getDebitoServicos().add(serv2);
		serv2.setDebito(debito);
		
		FinanceiroDAO financeiroDAO = new FinanceiroDAOImpl(em);
		financeiroDAO.gravarDebito(debito);
		
		Assert.assertEquals(em.find(Debito.class, debito.getId()).getTotalDebitos(), new BigDecimal(11));

	}
	
	
}

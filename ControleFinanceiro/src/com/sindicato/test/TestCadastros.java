package com.sindicato.test;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
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
	public void testaCadastroERecuperacaoOcupacaoSolo() {
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(cliente.getEstabelecimentoRural()
				.getOcupacoesSolo().size(), 2);
	}
	
	@Test
	public void testeCadastroDebito(){
		FinanceiroDAO financeiroDAO = new FinanceiroDAOImpl(em);
		
		Debito debito = new Debito();
		//debito.setDataBase(Calendar.getInstance());
		debito.setCliente(clienteDAO.getAll().get(0));
		debito.setStatus(StatusDebitoEnum.DEBITOCRIADO);
		
		ResultOperation result = financeiroDAO.gravarDebito(debito);
		
		Assert.assertEquals(result.isSuccess(), true);
		
	}
	
}

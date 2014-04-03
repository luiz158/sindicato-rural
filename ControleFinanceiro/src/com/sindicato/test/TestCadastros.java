package com.sindicato.test;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.entity.Cliente;

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
	public void pegarUltimoRegistroDeSocio(){
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(clienteDAO.isSocio(cliente), true);
		
		cliente.setSocio(false);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.isSocio(cliente), false);

		cliente.setSocio(true);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.isSocio(cliente), true);

		cliente.setSocio(false);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.isSocio(cliente), false);
	}
	
	@Test
	public void testaCadastroERecuperacaoOcupacaoSolo(){
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(cliente.getEstabelecimentoRural().getOcupacoesSolo().size(), 2);
	}
}

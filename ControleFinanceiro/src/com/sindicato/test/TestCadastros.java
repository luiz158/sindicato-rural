package com.sindicato.test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

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
		assertEquals(clienteDAO.isSocio(cliente), true);
		
		//cli.setSocio(false);
		//clienteDAO.update(cli);
		//assertEquals(clienteDAO.isSocio(cli), false);
	}
	
	
	
	
}

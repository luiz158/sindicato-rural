package com.sindicato.test;

import java.util.Calendar;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;

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
	public void controleDeMensalidadesDoCliente(){
		
		Cliente cliente = clienteDAO.searchByID(1);
		em.getTransaction().begin();
		for (InformacaoSocio i : cliente.getInformacoesSocio()) {
			em.remove(i);
		}
		em.getTransaction().commit();
		
		
		em.getTransaction().begin();
		
		// vira socio à um ano atras
		Calendar dataEvento = Calendar.getInstance();
		dataEvento.set(Calendar.YEAR, 2013);
		InformacaoSocio infSocio = new InformacaoSocio();
		infSocio.setCliente(cliente);
		infSocio.setDataEvento(dataEvento);
		infSocio.setSocio(true);
		
		em.persist(infSocio);

		// deixa de ser sócio hoje
		infSocio = new InformacaoSocio();
		infSocio.setCliente(cliente);
		infSocio.setDataEvento(Calendar.getInstance());
		infSocio.setSocio(false);
		
		em.persist(infSocio);
		
		em.getTransaction().commit();
		
		
		cliente = clienteDAO.searchByID(1);
		
		for (InformacaoSocio infSocioCliente : cliente.getInformacoesSocio()) {
			
		}
	}

}

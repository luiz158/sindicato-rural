package com.sindicato.contasapagar.test;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;

import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.impl.BancoDAOImpl;
import com.sindicato.contasapagar.entity.Banco;

public class TestContas {

	@Test
	public void test(){
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ControleFinanceiroTest");
		
		BancoDAO banco = new BancoDAOImpl(factory.createEntityManager());
		
		List<Banco> all = banco.getAll();
		
		Assert.assertTrue(all.size() > 0);
		
	}
	
	
}

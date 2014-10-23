package com.sindicato.contasapagar.dao.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.dao.impl.DAOImpl;

@Stateless
public class BancoDAOImpl extends DAOImpl<Banco, Integer> implements BancoDAO { 

	
	public BancoDAOImpl(){ }
	public BancoDAOImpl(EntityManager em){ 
		this.em = em; 
	}
	
}
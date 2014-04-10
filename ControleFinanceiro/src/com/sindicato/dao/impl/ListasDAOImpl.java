package com.sindicato.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.sindicato.dao.ListasDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;

public class ListasDAOImpl implements ListasDAO {

	public ListasDAOImpl(EntityManager em) {
		this.em = em;
	}
	
	private EntityManager em;
	
	
	@Override
	public List<Cliente> getClienteComDebitosNoStatus(StatusDebitoEnum status) {
		
		
		
		return null;
	}

	@Override
	public List<Debito> getDebitosDoClienteNoStatus(Cliente cliente,
			StatusDebitoEnum status) {
		// TODO Auto-generated method stub
		return null;
	}

}

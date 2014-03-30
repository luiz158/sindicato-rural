package com.sindicato.dao.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.InformacaoSocioDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;

public class ClienteDAOImpl extends DAOImpl<Cliente, Integer> implements ClienteDAO {

	public ClienteDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	private void inseriRegistroSocio(Cliente cliente){
		InformacaoSocio infSocio = new InformacaoSocio();
		infSocio.setCliente(cliente);
		infSocio.setDataEvento(Calendar.getInstance());
		infSocio.setSocio(cliente.isSocio());
		em.persist(infSocio);
	}
	
	@Override
	public void update(Cliente cliente){
		try {
			em.getTransaction().begin();
			if(this.isSocio(cliente) != cliente.isSocio()){
				inseriRegistroSocio(cliente);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}
	
	@Override
	public void insert(Cliente cliente){
		try {
			em.getTransaction().begin();
			em.persist(cliente);
			if(cliente.isSocio()){
				inseriRegistroSocio(cliente);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSocio(Cliente cliente) {
		InformacaoSocioDAO infSocioDAO = new InformacaoSocioDAOImpl(em);
		List<InformacaoSocio> informacoesSocio = infSocioDAO.buscarTodosPorCliente(cliente);
		if (informacoesSocio.size() == 0) {
			return false;
		}
		Collections.sort(informacoesSocio, new Comparator<InformacaoSocio>() {
			public int compare(InformacaoSocio o1, InformacaoSocio o2) {
				return (o1.getDataEvento().compareTo(o2.getDataEvento()));
			}
		});
		return informacoesSocio.get(informacoesSocio.size() - 1).isSocio();
	}
	
}

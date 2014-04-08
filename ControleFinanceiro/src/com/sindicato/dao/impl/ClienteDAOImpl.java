package com.sindicato.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.sindicato.dao.ClienteDAO;
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
	public Cliente searchByID(Integer id){
		Cliente cliente = em.find(Cliente.class, id);
		cliente.setSocio(this.isSocio(cliente));
		return cliente;
	} 
	
	@Override
	public List<Cliente> getAll(){
		
		String subQuerySocio = "select socio from InformacaoSocio s " +
			"Where s.id = (select MAX(s2.id) from InformacaoSocio s2 Where s2.cliente.id = c.id) ";
		
		String strQuery = "Select c, ("+ subQuerySocio +") from Cliente c ";
		TypedQuery<Object[]> query = null;
		query = em.createQuery(strQuery, Object[].class);
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			List<Object[]> objects = query.getResultList();
			for (Object[] o : objects) {
				Cliente cliente = (Cliente) o[0];
				if(o[1] != null){
					cliente.setSocio((Boolean) o[1]);
				}
				clientes.add(cliente);
			}
		} catch (NoResultException e) { }
		
		return clientes;
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
		String strQuery = "select socio from InformacaoSocio s " +
				"Where s.id = (select MAX(s2.id) from InformacaoSocio s2 Where s2.cliente = :cliente) ";
		Query query = em.createQuery(strQuery);
		query.setParameter("cliente", cliente);
		boolean socio = false;
		try {
			socio = (Boolean) query.getSingleResult();
		} catch (NoResultException e) { }
		return socio;
	}
	
}

package com.sindicato.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.joda.time.Months;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.result.InformacaoMensalidade;

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

	private List<InformacaoSocio> getInformacoesSocio(Cliente cliente){
		try {
			String strQuery = " select i from InformacaoSocio i where i.cliente = :cliente " +
					" order by i.id asc ";
			TypedQuery<InformacaoSocio> query = em.createQuery(strQuery, InformacaoSocio.class);
			query.setParameter("cliente", cliente);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public int calculaQuantosMesesOClienteESocio(Cliente cliente){
		List<InformacaoSocio> informacoesSocio = this.getInformacoesSocio(cliente);
		if(informacoesSocio.size() == 0){
			return 0;
		}
		int i = 1;
		boolean calcula = false;
		DateTime dataVirouSocio = null;
		DateTime dataDeixouDeSerSocio = null;
		int qtdMesComoSocio = 0;
		
		for (InformacaoSocio informacaoSocio : informacoesSocio) {
		
			// indice impar é data inicial e id par é data final, caso nao tenha id par, sera data atual
			// primeiro registro sempre sera socio = true, segundo sempre quando deixa de ser sócio
			if(i % 2 > 0){
				dataVirouSocio = new DateTime(informacaoSocio.getDataEvento());
			}else{
				dataDeixouDeSerSocio = new DateTime(informacaoSocio.getDataEvento());
				calcula = true;
			}
			
			if(calcula){
				qtdMesComoSocio += Months.monthsBetween(dataVirouSocio, dataDeixouDeSerSocio).getMonths();
				dataVirouSocio = null;
				dataDeixouDeSerSocio = null;
				calcula = false;
			}
			
			i++;
		}
		
		// se existir apenas registro de sócio = true, calcula com data atual
		if((i % 2 == 0) && dataDeixouDeSerSocio == null){
			qtdMesComoSocio += Months.monthsBetween(dataVirouSocio, new DateTime()).getMonths();
		}
		return qtdMesComoSocio;
	}
	
	@Override
	public int calculaQuantasMensalidadeForamPagas(Cliente cliente) {
		
		
		
		return 0;
	}
	
	@Override
	public InformacaoMensalidade estaEmDiaComAsMensalidades(Cliente cliente) {
		InformacaoMensalidade retorno = new InformacaoMensalidade(); 
		
		retorno.setMesesComoSocio(this.calculaQuantosMesesOClienteESocio(cliente));
		retorno.setMensalidadesPagas(0);
		
		
		retorno.setMensagem("Cliente nunca foi sócio do sindicato.");
		retorno.setAtrasado(false);

		
		
		return retorno;
	
		
	}

	

}
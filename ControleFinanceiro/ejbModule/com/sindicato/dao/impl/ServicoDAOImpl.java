package com.sindicato.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.dao.ServicoDAO;
import com.sindicato.entity.Servico;
import com.sindicato.result.ResultOperation;

@Stateless
public class ServicoDAOImpl implements ServicoDAO {

	@PersistenceContext(name="ControleFinanceiro")
	protected EntityManager em;
	
	public ResultOperation remove(Servico servico){
		
		String strQuery = "select COUNT(d) from DebitoServico d " +
				"where d.servico = :servico";
		
		TypedQuery<Long> query = em.createQuery(strQuery, Long.class);
		query.setParameter("servico", servico);
		
		Long count = query.getSingleResult();
		
		ResultOperation result = new ResultOperation();
		if(count > 0){
			result.setMessage("Existem d�bitos com este servi�o, voc� n�o pode remove-lo.");
			result.setSuccess(false);
		} else {
			try {
				em.getTransaction().begin();
				em.merge(servico);
				em.remove(servico);
				em.getTransaction().commit();

				result.setMessage("Servi�o removido com sucesso.");
				result.setSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				result.setMessage("Erro ao remover servi�o, por favor contate o administrador.");
				result.setSuccess(false);
			}
		}
		return result;
	}
	
	@Override
	public void insert(Servico entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		
	}
	
	@Override
	public Servico searchByID(Integer id) {
		return em.find(Servico.class, id);
	}

	@Override
	public void update(Servico entity) {
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
	}

	@Override
	public List<Servico> getAll(){
		String strQuery = "Select u from Servico u ";
		TypedQuery<Servico> query = null;
		query = em.createQuery(strQuery, Servico.class);
		return query.getResultList();
	}	
	
}
package com.sindicato.controlefinanceiro.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.controlefinanceiro.dao.ServicoDAO;
import com.sindicato.controlefinanceiro.entity.Servico;
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
			result.setMessage("Existem débitos com este serviço, você não pode remove-lo.");
			result.setSuccess(false);
		} else {
			try {
				em.merge(servico);
				em.remove(servico);

				result.setMessage("Serviço removido com sucesso.");
				result.setSuccess(true);
			} catch (Exception e) {
				e.printStackTrace();
				result.setMessage("Erro ao remover serviço, por favor contate o administrador.");
				result.setSuccess(false);
			}
		}
		return result;
	}
	
	@Override
	public void insert(Servico entity) {
		em.persist(entity);
		
	}
	
	@Override
	public Servico searchByID(Integer id) {
		return em.find(Servico.class, id);
	}

	@Override
	public void update(Servico entity) {
		em.merge(entity);
	}

	@Override
	public List<Servico> getAll(){
		String strQuery = "Select u from Servico u ";
		TypedQuery<Servico> query = null;
		query = em.createQuery(strQuery, Servico.class);
		return query.getResultList();
	}	
	
}

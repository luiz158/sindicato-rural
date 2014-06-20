package com.sindicato.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.util.FiltroDataTable;

@Stateless
public class DebitoDAOImpl implements DebitoDAO {

	@PersistenceContext(name="ControleFinanceiro")
	EntityManager em;
	
	@Override
	public Debito searchByID(Integer id) {
		return em.find(Debito.class, id);
	}

	@Override
	public List<Debito> getResultListFiltered(int first, int pageSize,
			String sortField, String sortOrder, Map<String, Object> filters,
			List<StatusDebitoEnum> statusPermitidos) {
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("select d from Debito d ");
		
		FiltroDataTable wheres = new FiltroDataTable();
		if(filters.isEmpty() == false || statusPermitidos.size() > 0){
			wheres = this.getFilterCondition(filters, statusPermitidos);
			jpql.append(wheres.getWhere());
		}
		
		if (sortField != null) {
			if (sortOrder == "ASCENDING") {
				jpql.append("order by " + sortField);
			} else if (sortOrder == "DESCENDING") {
				jpql.append("order by " + sortField + " desc ");
			}
		}

		TypedQuery<Debito> debitos = em.createQuery(jpql.toString(), Debito.class).setFirstResult(first)
				.setMaxResults(pageSize);
		
		if(filters.isEmpty() == false || statusPermitidos.size() > 0){
			if(wheres.getParameters().isEmpty() == false){
				for (Map.Entry<String, Object> filter : wheres.getParameters().entrySet()) {
					debitos.setParameter(filter.getKey(), filter.getValue());
				}
			}
		}
		
		return debitos.getResultList();
	
	}

	@Override
	public int count(Map<String, Object> filters,
			List<StatusDebitoEnum> statusPermitidos) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select COUNT(d) from Debito d ");
		
		FiltroDataTable wheres = new FiltroDataTable();
		if(filters.isEmpty() == false || statusPermitidos.size() > 0){
			wheres = this.getFilterCondition(filters, statusPermitidos);
			jpql.append(wheres.getWhere());
		}
		
		TypedQuery<Long> debitos = em.createQuery(jpql.toString(), Long.class);
		
		if(filters.isEmpty() == false || statusPermitidos.size() > 0){
			if(wheres.getParameters().isEmpty() == false){
				for (Map.Entry<String, Object> filter : wheres.getParameters().entrySet()) {
					debitos.setParameter(filter.getKey(), filter.getValue());
				}
			}
		}
		
		return debitos.getSingleResult().intValue();
	}
	
	private FiltroDataTable getFilterCondition(Map<String, Object> filters, List<StatusDebitoEnum> statusPermitidos) {
		
		StringBuilder where = new StringBuilder();
		where.append("where 1 = 1 ");
		FiltroDataTable filtro = new FiltroDataTable();
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			if (!filter.getValue().equals("")) {
				System.out.print(filter.getKey() + ":");
				System.out.println(filter.getValue());
			}
		}
		
		if(statusPermitidos.size() > 0){
			where.append("and d.status in (:status) ");
			filtro.getParameters().put("status", statusPermitidos);
		}
		
		filtro.setWhere(where.toString());
		return filtro;
	}

}

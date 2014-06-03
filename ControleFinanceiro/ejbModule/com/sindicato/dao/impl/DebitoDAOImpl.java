package com.sindicato.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;

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
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Debito> cq = cb.createQuery(Debito.class);
		
		Root<Debito> myObj = cq.from(Debito.class);
		cq.where(this.getFilterCondition(cb, myObj, filters, statusPermitidos));
		if (sortField != null) {
			if (sortOrder == "ASCENDING") {
				cq.orderBy(cb.asc(myObj.get(sortField)));
			} else if (sortOrder == "DESCENDING") {
				cq.orderBy(cb.desc(myObj.get(sortField)));
			}
		}

		List<Debito> debitos = em.createQuery(cq).setFirstResult(first)
				.setMaxResults(pageSize).getResultList();
		return debitos;
	
	}

	@Override
	public int count(Map<String, Object> filters,
			List<StatusDebitoEnum> statusPermitidos) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Debito> myObj = cq.from(Debito.class);
		cq.where(this.getFilterCondition(cb, myObj, filters, statusPermitidos));
		cq.select(cb.count(myObj));
		return em.createQuery(cq).getSingleResult().intValue();
	}
	
	private Predicate getFilterCondition(CriteriaBuilder cb,
			Root<Debito> myObj, Map<String, Object> filters, List<StatusDebitoEnum> statusPermitidos) {
		
		Predicate filterCondition = cb.conjunction();
		for (Map.Entry<String, Object> filter : filters.entrySet()) {

			if (!filter.getValue().equals("")) {
				javax.persistence.criteria.Path<String> path = myObj.get(filter
						.getKey());
				try {
					String columnType = myObj.get(filter.getKey())
							.getJavaType().toString();
					if (columnType.contains("String")) {
						String value = "%" + filter.getValue().toString().toUpperCase()
								+ "%";
						filterCondition = cb.and(filterCondition,
								cb.like(cb.upper(path), value));
					} else {
						filterCondition = cb.and(filterCondition,
								cb.equal(path, filter.getValue()));
					}
				} catch (Exception e) {
					filterCondition = cb.and(filterCondition,
							cb.equal(path, filter.getValue()));
				}
			}
		}
		
		if(statusPermitidos != null){
			javax.persistence.criteria.Path<String> status = myObj.get("status");
			filterCondition = cb.and(filterCondition,
					status.in(statusPermitidos));
		}
		
		return filterCondition;
	}

}

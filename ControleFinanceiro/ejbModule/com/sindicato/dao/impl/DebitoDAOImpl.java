package com.sindicato.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

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
		
		EasyCriteria<Debito> easyCriteria = EasyCriteriaFactory.createQueryCriteria(em, Debito.class);
		easyCriteria.innerJoin("cliente");
		this.getFilterCondition(easyCriteria, filters, statusPermitidos);
		
		if (sortField != null) {
			if (sortOrder == "ASCENDING") {
				easyCriteria.orderByAsc(sortField);
			} else if (sortOrder == "DESCENDING") {
				easyCriteria.orderByDesc(sortField);
			}
		}

		List<Debito> debitos = easyCriteria.setFirstResult(first)
				.setMaxResults(pageSize).getResultList();
		return debitos;
	}

	@Override
	public int count(Map<String, Object> filters,
			List<StatusDebitoEnum> statusPermitidos) {
		EasyCriteria<Debito> easyCriteria = EasyCriteriaFactory.createQueryCriteria(em, Debito.class);
		easyCriteria.innerJoin("cliente");
		this.getFilterCondition(easyCriteria, filters, statusPermitidos);
		return easyCriteria.count().intValue();
	}
	
	private void getFilterCondition(EasyCriteria<Debito> easyCriteria,
			Map<String, Object> filters, List<StatusDebitoEnum> statusPermitidos) {
		
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			if (!filter.getValue().equals("")) {
				if (filter.getKey().equals("numeroNota")) {
					easyCriteria.andEquals(filter.getKey(), filter.getValue());
				} else {
					boolean toLowerCase = true;
					easyCriteria.andStringLike(toLowerCase, filter.getKey(), "%" + filter.getValue().toString() + "%");
				}
			}
		}
		
		if(statusPermitidos != null){
			for (StatusDebitoEnum statusDebitoEnum : statusPermitidos) {
				easyCriteria.orEquals(1, "status", statusDebitoEnum.ordinal());
			}
		}
	}

}

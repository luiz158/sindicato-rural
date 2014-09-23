package com.sindicato.controlefinanceiro.dao.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sindicato.controlefinanceiro.dao.DebitoDAO;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

@Stateful
public class DebitoDAOImpl implements DebitoDAO {

	@PersistenceContext(name="ControleFinanceiro")
	private EntityManager em;
	
	@Override
	public Debito searchByID(Integer id) {
		return em.find(Debito.class, id);
	}

	@Override
	public List<Debito> getResultListFiltered(int first, int pageSize,
			String sortField, String sortOrder, Map<String, Object> filters,
			List<StatusDebitoEnum> statusPermitidos) {
		
		EasyCriteria<Debito> easyCriteria = EasyCriteriaFactory.createQueryCriteria(em, Debito.class);
		easyCriteria.setDistinctTrue();
		easyCriteria.innerJoin("cliente");
		easyCriteria.innerJoin("debitoServicos.servico");
		
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
		easyCriteria.setDistinctTrue();
		easyCriteria.innerJoin("cliente");
		easyCriteria.innerJoin("debitoServicos.servico");
		this.getFilterCondition(easyCriteria, filters, statusPermitidos);
		return easyCriteria.count().intValue();
	}
	
	private void getFilterCondition(EasyCriteria<Debito> easyCriteria,
			Map<String, Object> filters, List<StatusDebitoEnum> statusPermitidos) {
		
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			if (!filter.getValue().equals("")) {
				
				if (filter.getKey().equals("cliente.nome") || filter.getKey().equals("cliente.cpf")) {
					boolean toLowerCase = true;
					easyCriteria.andStringLike(toLowerCase, filter.getKey(), "%" + filter.getValue().toString() + "%");
				} else {
					easyCriteria.andEquals(filter.getKey(), filter.getValue());
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

package com.sindicato.controlefinanceiro.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;


@Remote
public interface DebitoDAO {

	Debito searchByID(Integer id);

	List<Debito> getResultListFiltered(int first, int pageSize,
			String sortField, String sortOrder, Map<String, Object> filters, List<StatusDebitoEnum> statusPermitidos);

	int count(Map<String, Object> filters, List<StatusDebitoEnum> statusPermitidos);

}

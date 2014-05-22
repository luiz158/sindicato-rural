package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.Servico;
import com.sindicato.result.ResultOperation;

public interface ServicoDAO{

	ResultOperation remove(Servico servico);
	
	void insert(Servico entity);
	
	Servico searchByID(Integer id);
	
	void update(Servico entity);
	
	List<Servico> getAll();

}

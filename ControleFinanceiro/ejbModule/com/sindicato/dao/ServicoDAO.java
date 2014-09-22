package com.sindicato.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.entity.Servico;
import com.sindicato.result.ResultOperation;

@Remote
public interface ServicoDAO{

	ResultOperation remove(Servico servico);
	
	void insert(Servico entity);
	
	Servico searchByID(Integer id);
	
	void update(Servico entity);
	
	List<Servico> getAll();

}

package com.sindicato.dao;

import com.sindicato.entity.Cliente;
import com.sindicato.result.InformacaoMensalidade;

public interface ClienteDAO extends DAO<Cliente, Integer> {

	boolean isSocio(Cliente cliente);
	
	int calculaQuantosMesesOClienteESocio(Cliente cliente);
	
	InformacaoMensalidade estaEmDiaComAsMensalidades(Cliente cliente);
	
}

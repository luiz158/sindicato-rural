package com.sindicato.dao;

import com.sindicato.entity.Cliente;

public interface ClienteDAO extends DAO<Cliente, Integer> {

	boolean isSocio(Cliente cliente);
	
}

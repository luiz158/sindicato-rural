package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;

public interface ListasDAO {

	List<Cliente> getClienteComDebitosNoStatus(StatusDebitoEnum status);
	List<Debito> getDebitosDoClienteNoStatus(Cliente cliente, StatusDebitoEnum status);
	
	
	
}

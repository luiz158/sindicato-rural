package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;

public interface InformacaoSocioDAO extends DAO<InformacaoSocio, Integer> {

	List<InformacaoSocio> buscarTodosPorCliente(Cliente cliente);
}

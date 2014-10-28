package com.sindicato.contasapagar.dao;

import java.util.List;

import javax.ejb.Local;

import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.result.ResultOperation;

@Local
public interface BancoDAO {
	
	ResultOperation cadastrar(Banco banco);
	ResultOperation alterar(Banco banco);
	ResultOperation excluir(Banco banco);
	List<Banco> getAll();
	Banco searchByID(Integer id);
}

package com.sindicato.contasapagar.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.result.ResultOperation;

@Remote
public interface ContaDAO { 
	
	Conta searchByID(int id);
	ResultOperation cadastrar(Conta conta);
	ResultOperation alterar(Conta conta);
	ResultOperation remover(Conta conta);
	
	List<Conta> listarContas();
	
	List<Conta> getContasPendentes();
	
}

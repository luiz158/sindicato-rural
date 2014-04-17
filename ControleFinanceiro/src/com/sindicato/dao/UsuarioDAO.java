package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;

public interface UsuarioDAO extends DAO<Usuario, Integer> {

	ResultOperation autenticar(String usuario, String senha);

	Usuario getUsuarioAutenticado();
	
	List<Menu> getMenusPermitidos();
}

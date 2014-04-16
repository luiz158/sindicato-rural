package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Usuario;

public interface UsuarioDAO extends DAO<Usuario, Integer> {

	boolean autenticar(String usuario, String senha);

	Usuario getUsuarioAutenticado();
	
	List<Menu> getMenusPermitidos();
}

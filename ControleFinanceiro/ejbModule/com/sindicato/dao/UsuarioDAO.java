package com.sindicato.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;

@Remote
public interface UsuarioDAO extends DAO<Usuario, Integer> {

	ResultOperation autenticar(String usuario, String senha);

	Usuario getUsuarioAutenticado();
	
	List<Menu> getMenusPermitidos();

	void atualizarSenha(Usuario usuario, String novaSenha);
}

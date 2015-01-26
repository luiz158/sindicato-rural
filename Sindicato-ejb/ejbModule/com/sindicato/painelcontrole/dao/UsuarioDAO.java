package com.sindicato.painelcontrole.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.dao.DAO;
import com.sindicato.painelcontrole.entity.Acao;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Modulo;
import com.sindicato.painelcontrole.entity.Perfil;
import com.sindicato.painelcontrole.entity.Usuario;
import com.sindicato.result.ResultOperation;

@Remote
public interface UsuarioDAO extends DAO<Usuario, Integer> {

	ResultOperation autenticar(String usuario, String senha);

	Usuario getUsuarioAutenticado();
	
	List<Acao> getAcoesPermitidas();
	List<Menu> getMenusPermitidos();

	List<Modulo> extraiModulosPermitidos(List<Perfil> perfis);

}

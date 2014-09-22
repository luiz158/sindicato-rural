package com.sindicato.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;

@Remote
public interface MenuDAO extends DAO<Menu, Integer>  {
	List<Menu> getMenusPorPerfil(Perfil perfil);
}

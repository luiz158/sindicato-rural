package com.sindicato.painelcontrole.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.dao.DAO;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Perfil;

@Remote
public interface MenuDAO extends DAO<Menu, Integer>  {
	List<Menu> getMenusPorPerfil(Perfil perfil);
}

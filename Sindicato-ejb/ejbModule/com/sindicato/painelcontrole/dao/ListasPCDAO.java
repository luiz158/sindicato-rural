package com.sindicato.painelcontrole.dao;

import java.util.List;

import javax.ejb.Local;

import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Modulo;
import com.sindicato.painelcontrole.entity.Perfil;

@Local
public interface ListasPCDAO {

	List<Perfil> getTodosOsPerfis();
	List<Menu> getTodosOsMenus();
	List<Modulo> getTodosModulos();
}

package com.sindicato.painelcontrole.dao;

import javax.ejb.Remote;

import com.sindicato.dao.DAO;
import com.sindicato.painelcontrole.entity.Menu;

@Remote
public interface MenuDAO extends DAO<Menu, Integer>  {
	
}

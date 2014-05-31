package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.autenticacao.Menu;

@Remote
public interface MenuDAO extends DAO<Menu, Integer>  {

}

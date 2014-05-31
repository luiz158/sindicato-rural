package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.Debito;


@Remote
public interface DebitoDAO {

	Debito searchByID(Integer id);
	
}

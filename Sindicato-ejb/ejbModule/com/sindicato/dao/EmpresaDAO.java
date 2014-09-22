package com.sindicato.dao;

import javax.ejb.Local;

import com.sindicato.entity.Empresa;

@Local
public interface EmpresaDAO extends DAO<Empresa, Integer> {

}

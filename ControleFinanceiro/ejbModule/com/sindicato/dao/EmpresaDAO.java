package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.Empresa;

@Remote
public interface EmpresaDAO extends DAO<Empresa, Integer> {

}

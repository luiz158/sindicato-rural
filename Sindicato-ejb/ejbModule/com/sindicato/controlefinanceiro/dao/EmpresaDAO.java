package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Local;

import com.sindicato.controlefinanceiro.entity.Empresa;
import com.sindicato.dao.DAO;

@Local
public interface EmpresaDAO extends DAO<Empresa, Integer> {

}

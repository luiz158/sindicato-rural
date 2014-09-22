package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Local;

import com.sindicato.controlefinanceiro.entity.Empresa;

@Local
public interface EmpresaDAO extends DAO<Empresa, Integer> {

}

package com.sindicato.contasapagar.dao;

import javax.ejb.Local;

import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.dao.DAO;

@Local
public interface ContaDAO extends DAO<Conta, Integer> { }

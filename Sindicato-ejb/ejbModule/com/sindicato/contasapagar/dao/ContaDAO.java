package com.sindicato.contasapagar.dao;

import javax.ejb.Remote;

import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.dao.DAO;

@Remote
public interface ContaDAO extends DAO<Conta, Integer> { }

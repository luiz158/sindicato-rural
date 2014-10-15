package com.sindicato.contasapagar.dao;

import javax.ejb.Remote;

import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.dao.DAO;

@Remote
public interface BancoDAO extends DAO<Banco, Integer> { }

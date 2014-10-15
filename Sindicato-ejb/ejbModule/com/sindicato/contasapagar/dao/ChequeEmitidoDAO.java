package com.sindicato.contasapagar.dao;

import javax.ejb.Local;

import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.dao.DAO;

@Local
public interface ChequeEmitidoDAO extends DAO<ChequeEmitido, Integer> { }

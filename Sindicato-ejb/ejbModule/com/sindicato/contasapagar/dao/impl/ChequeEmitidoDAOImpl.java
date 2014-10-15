package com.sindicato.contasapagar.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.dao.impl.DAOImpl;

@Stateless
public class ChequeEmitidoDAOImpl extends DAOImpl<ChequeEmitido, Integer> implements ChequeEmitidoDAO { }
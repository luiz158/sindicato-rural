package com.sindicato.contasapagar.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.dao.impl.DAOImpl;

@Stateless
public class ContaDAOImpl extends DAOImpl<Conta, Integer> implements ContaDAO { }
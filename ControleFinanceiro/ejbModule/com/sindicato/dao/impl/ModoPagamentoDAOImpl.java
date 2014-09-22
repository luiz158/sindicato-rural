package com.sindicato.dao.impl;

import javax.ejb.Stateful;

import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.entity.ModoPagamento;

@Stateful
public class ModoPagamentoDAOImpl extends DAOImpl<ModoPagamento, Integer> implements ModoPagamentoDAO { }

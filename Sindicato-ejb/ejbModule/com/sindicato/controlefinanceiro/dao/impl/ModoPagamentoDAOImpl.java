package com.sindicato.controlefinanceiro.dao.impl;

import javax.ejb.Stateful;

import com.sindicato.controlefinanceiro.dao.ModoPagamentoDAO;
import com.sindicato.controlefinanceiro.entity.ModoPagamento;

@Stateful
public class ModoPagamentoDAOImpl extends DAOImpl<ModoPagamento, Integer> implements ModoPagamentoDAO { }

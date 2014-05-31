package com.sindicato.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.entity.ModoPagamento;

@Stateless
public class ModoPagamentoDAOImpl extends DAOImpl<ModoPagamento, Integer> implements ModoPagamentoDAO { }

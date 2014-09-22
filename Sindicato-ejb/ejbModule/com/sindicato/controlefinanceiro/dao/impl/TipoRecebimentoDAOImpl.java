package com.sindicato.controlefinanceiro.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.controlefinanceiro.dao.TipoRecebimentoDAO;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;

@Stateless
public class TipoRecebimentoDAOImpl extends DAOImpl<TipoRecebimento, Integer> implements TipoRecebimentoDAO { }

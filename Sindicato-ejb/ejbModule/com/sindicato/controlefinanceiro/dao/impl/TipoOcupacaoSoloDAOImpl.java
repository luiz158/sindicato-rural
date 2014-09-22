package com.sindicato.controlefinanceiro.dao.impl;

import javax.ejb.Stateful;

import com.sindicato.controlefinanceiro.dao.TipoOcupacaoSoloDAO;
import com.sindicato.controlefinanceiro.entity.TipoOcupacaoSolo;
import com.sindicato.dao.impl.DAOImpl;

@Stateful
public class TipoOcupacaoSoloDAOImpl extends DAOImpl<TipoOcupacaoSolo, Integer> implements TipoOcupacaoSoloDAO { }

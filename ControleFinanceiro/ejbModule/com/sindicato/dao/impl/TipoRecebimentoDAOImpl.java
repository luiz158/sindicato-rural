package com.sindicato.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.dao.TipoRecebimentoDAO;
import com.sindicato.entity.TipoRecebimento;

@Stateless
public class TipoRecebimentoDAOImpl extends DAOImpl<TipoRecebimento, Integer> implements TipoRecebimentoDAO { }

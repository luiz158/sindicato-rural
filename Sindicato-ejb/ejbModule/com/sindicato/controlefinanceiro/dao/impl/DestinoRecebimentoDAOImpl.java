package com.sindicato.controlefinanceiro.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.controlefinanceiro.dao.DestinoRecebimentoDAO;
import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;
import com.sindicato.dao.impl.DAOImpl;

@Stateless
public class DestinoRecebimentoDAOImpl extends DAOImpl<DestinoRecebimento, Integer> implements DestinoRecebimentoDAO { }

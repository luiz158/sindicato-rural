package com.sindicato.painelcontrole.dao.impl;

import javax.ejb.Stateful;

import com.sindicato.controlefinanceiro.dao.impl.DAOImpl;
import com.sindicato.painelcontrole.dao.PerfilDAO;
import com.sindicato.painelcontrole.entity.Perfil;

@Stateful
public class PerfilDAOImpl extends DAOImpl<Perfil, Integer> implements PerfilDAO { }

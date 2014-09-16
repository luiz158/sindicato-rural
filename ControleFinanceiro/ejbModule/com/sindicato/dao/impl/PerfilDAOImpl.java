package com.sindicato.dao.impl;

import javax.ejb.Stateful;

import com.sindicato.dao.PerfilDAO;
import com.sindicato.entity.autenticacao.Perfil;

@Stateful
public class PerfilDAOImpl extends DAOImpl<Perfil, Integer> implements PerfilDAO { }

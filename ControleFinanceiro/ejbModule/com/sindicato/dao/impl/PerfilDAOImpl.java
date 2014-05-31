package com.sindicato.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.dao.PerfilDAO;
import com.sindicato.entity.autenticacao.Perfil;

@Stateless
public class PerfilDAOImpl extends DAOImpl<Perfil, Integer> implements PerfilDAO { }

package com.sindicato.controlefinanceiro.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.controlefinanceiro.dao.EmpresaDAO;
import com.sindicato.controlefinanceiro.entity.Empresa;
import com.sindicato.dao.impl.DAOImpl;

@Stateless
public class EmpresaDAOImpl extends DAOImpl<Empresa, Integer> implements EmpresaDAO { }

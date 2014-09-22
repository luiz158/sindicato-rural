package com.sindicato.dao.impl;

import javax.ejb.Stateless;

import com.sindicato.dao.EmpresaDAO;
import com.sindicato.entity.Empresa;

@Stateless
public class EmpresaDAOImpl extends DAOImpl<Empresa, Integer> implements EmpresaDAO { }

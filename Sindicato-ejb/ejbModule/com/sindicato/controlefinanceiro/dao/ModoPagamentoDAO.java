package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.entity.ModoPagamento;
import com.sindicato.dao.DAO;

@Remote
public interface ModoPagamentoDAO extends DAO<ModoPagamento, Integer> {

}

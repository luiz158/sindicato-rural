package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.entity.ModoPagamento;

@Remote
public interface ModoPagamentoDAO extends DAO<ModoPagamento, Integer> {

}

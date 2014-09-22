package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.ModoPagamento;

@Remote
public interface ModoPagamentoDAO extends DAO<ModoPagamento, Integer> {

}

package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Local;

import com.sindicato.controlefinanceiro.entity.TipoRecebimento;
import com.sindicato.dao.DAO;

@Local
public interface TipoRecebimentoDAO extends DAO<TipoRecebimento, Integer> {

}

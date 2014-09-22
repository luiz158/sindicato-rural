package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.entity.TipoOcupacaoSolo;
import com.sindicato.dao.DAO;

@Remote
public interface TipoOcupacaoSoloDAO extends DAO<TipoOcupacaoSolo, Integer> {

}

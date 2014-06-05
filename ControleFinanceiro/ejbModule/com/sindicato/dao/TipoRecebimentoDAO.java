package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.TipoRecebimento;

@Remote
public interface TipoRecebimentoDAO extends DAO<TipoRecebimento, Integer> {

}

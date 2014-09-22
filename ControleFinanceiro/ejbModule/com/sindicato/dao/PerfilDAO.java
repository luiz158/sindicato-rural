package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.autenticacao.Perfil;

@Remote
public interface PerfilDAO extends DAO<Perfil, Integer> {

}

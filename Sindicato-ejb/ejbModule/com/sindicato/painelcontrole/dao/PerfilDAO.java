package com.sindicato.painelcontrole.dao;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.dao.DAO;
import com.sindicato.painelcontrole.entity.Perfil;

@Remote
public interface PerfilDAO extends DAO<Perfil, Integer> {

}

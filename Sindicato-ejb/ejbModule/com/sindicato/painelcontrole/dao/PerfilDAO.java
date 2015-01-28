package com.sindicato.painelcontrole.dao;

import java.util.List;

import javax.ejb.Remote;

import com.sindicato.dao.DAO;
import com.sindicato.painelcontrole.entity.Acao;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Perfil;

@Remote
public interface PerfilDAO extends DAO<Perfil, Integer> {
	List<Menu> getMenus(Perfil perfil);
	List<Acao> getAcoes(Perfil perfil);
}

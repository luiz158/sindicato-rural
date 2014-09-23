package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;

@Remote
public interface RecebimentoDAO {

	DestinoRecebimento getDestinoRecebimentoById(Integer id);
	TipoRecebimento getTipoRecebimentoById(Integer id);
	
}

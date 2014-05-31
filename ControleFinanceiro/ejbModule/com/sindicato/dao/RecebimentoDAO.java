package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.TipoRecebimento;

@Remote
public interface RecebimentoDAO {

	DestinoRecebimento getDestinoRecebimentoById(Integer id);
	TipoRecebimento getTipoRecebimentoById(Integer id);
	
}

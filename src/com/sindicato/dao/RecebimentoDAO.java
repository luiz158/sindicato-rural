package com.sindicato.dao;

import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.TipoRecebimento;

public interface RecebimentoDAO {

	DestinoRecebimento getDestinoRecebimentoById(Integer id);
	TipoRecebimento getTipoRecebimentoById(Integer id);
	
}

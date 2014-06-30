package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.report.model.RelatorioAssociados;

@Remote
public interface RelatorioDAO {

	RelatorioAssociados getAssociados();
	
}

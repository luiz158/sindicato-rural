package com.sindicato.dao;

import java.util.Calendar;

import javax.ejb.Remote;

import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.report.model.RelatorioResumoServico;

@Remote
public interface RelatorioDAO {

	RelatorioAssociados getAssociados();

	RelatorioResumoServico getResumoServico(Calendar dataDe, Calendar dataAte);
	
}

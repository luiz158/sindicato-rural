package com.sindicato.dao;

import java.util.Calendar;

import javax.ejb.Remote;

import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.report.model.RelatorioRecolhimentosAberto;
import com.sindicato.report.model.RelatorioResumoRecebimentos;
import com.sindicato.report.model.RelatorioResumoRecolhimentos;
import com.sindicato.report.model.RelatorioResumoServico;
import com.sindicato.report.model.RelatorioRetencoesRecolher;

@Remote
public interface RelatorioDAO {
	RelatorioAssociados getAssociados();
	RelatorioResumoServico getResumoServico(Calendar dataDe, Calendar dataAte);
	RelatorioResumoRecebimentos getResumoRecebimentos(Calendar dataDe, Calendar dataAte);
	RelatorioResumoRecolhimentos getResumoRecolhimentos(Calendar dataDe, Calendar dataAte);
	RelatorioRecolhimentosAberto getRelatorioRecolhimentosAberto(Calendar dataAte);
	RelatorioRetencoesRecolher getRelatorioRetencoesRecolher(Calendar dataDe,
			Calendar dataAte);
}

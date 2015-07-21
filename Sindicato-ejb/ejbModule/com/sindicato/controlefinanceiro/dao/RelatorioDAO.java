package com.sindicato.controlefinanceiro.dao;

import java.util.Calendar;

import javax.ejb.Local;

import com.sindicato.controlefinanceiro.report.model.FiltroAssociados;
import com.sindicato.controlefinanceiro.report.model.RelatorioAssociados;
import com.sindicato.controlefinanceiro.report.model.RelatorioFolhaVotacao;
import com.sindicato.controlefinanceiro.report.model.RelatorioInscricaoEstadual;
import com.sindicato.controlefinanceiro.report.model.RelatorioNotasEmitidas;
import com.sindicato.controlefinanceiro.report.model.RelatorioRecolhimentosAberto;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoRecebimentos;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoRecolhimentos;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoServico;
import com.sindicato.controlefinanceiro.report.model.RelatorioRetencoesRecolher;

@Local
public interface RelatorioDAO {
	RelatorioAssociados getAssociados(FiltroAssociados filtro);
	RelatorioResumoServico getResumoServico(Calendar dataDe, Calendar dataAte);
	RelatorioResumoRecebimentos getResumoRecebimentos(Calendar dataDe, Calendar dataAte);
	RelatorioResumoRecolhimentos getResumoRecolhimentos(Calendar dataDe, Calendar dataAte);
	RelatorioRecolhimentosAberto getRelatorioRecolhimentosAberto(Calendar dataAte);
	RelatorioRetencoesRecolher getRelatorioRetencoesRecolher(Calendar dataDe,
			Calendar dataAte);
	RelatorioNotasEmitidas getRelatorioNotasEmitidas(Calendar dataDe,
			Calendar dataAte);
	RelatorioInscricaoEstadual getRelatorioInscricaoEstadual();
	RelatorioFolhaVotacao getRelatorioFolhaVotacao();
}

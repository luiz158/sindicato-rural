package com.sindicato.MB.relatorios;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.dao.RelatorioDAO;
import com.sindicato.report.model.RelatorioInscricaoEstadual;

@ManagedBean
@ViewScoped
public class InscricaoEstadualVencidaBean {
	
	@EJB
	private RelatorioDAO relatorioDAO;
	
	private RelatorioInscricaoEstadual relatorio;

	@PostConstruct
	public void carregaRelatorio() {
		relatorio = relatorioDAO.getRelatorioInscricaoEstadual();
	}
	public RelatorioInscricaoEstadual getRelatorio() {
		return relatorio;
	}
	public void setRelatorio(RelatorioInscricaoEstadual relatorio) {
		this.relatorio = relatorio;
	}
	
}

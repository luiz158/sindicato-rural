package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRetencoesRecolher implements Serializable {
	
	public RelatorioRetencoesRecolher(){
		listaServicos = new ArrayList<ServicoRetencoesRecolher>();
	}
	
	private static final long serialVersionUID = 1L;
	private List<ServicoRetencoesRecolher> listaServicos;
	private BigDecimal totalRetencoesRecolher;
	
	public List<ServicoRetencoesRecolher> getListaServicos() {
		return listaServicos;
	}
	public BigDecimal getTotalRetencoesRecolher() {
		totalRetencoesRecolher = BigDecimal.ZERO;
		if(listaServicos == null || listaServicos.size() == 0){
			return totalRetencoesRecolher;
		}
		
		for (ServicoRetencoesRecolher servico : listaServicos) {
			totalRetencoesRecolher = totalRetencoesRecolher.add(servico.getTotalRetencoesServico());
		}
		
		return totalRetencoesRecolher;
	}
	public void setListaServicos(List<ServicoRetencoesRecolher> listaServicos) {
		this.listaServicos = listaServicos;
	}
	public void setTotalRetencoesRecolher(BigDecimal totalRetencoesRecolher) {
		this.totalRetencoesRecolher = totalRetencoesRecolher;
	}
	
}

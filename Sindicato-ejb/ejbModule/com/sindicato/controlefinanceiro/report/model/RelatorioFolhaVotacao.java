package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioFolhaVotacao implements Serializable {

	public RelatorioFolhaVotacao(){
		this.detalhesAssociado = new ArrayList<DetalhesAssociado>();
	}
	
	private static final long serialVersionUID = 1L;	
	List<DetalhesAssociado> detalhesAssociado;


	public List<DetalhesAssociado> getDetalhesAssociado() {
		return detalhesAssociado;
	}
	public void setDetalhesAssociado(List<DetalhesAssociado> detalhesAssociado) {
		this.detalhesAssociado = detalhesAssociado;
	}
	
}

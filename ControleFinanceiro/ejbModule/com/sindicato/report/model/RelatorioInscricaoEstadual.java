package com.sindicato.report.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioInscricaoEstadual implements Serializable {

	public RelatorioInscricaoEstadual(){
		detalhesAssociados = new ArrayList<DetalheInscricaoEstadual>();
	}
	
	private static final long serialVersionUID = 1L;
	private List<DetalheInscricaoEstadual> detalhesAssociados;
	private int qtdInscricoesVencidas;
	private int qtdInscricoesProximaVencer;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<DetalheInscricaoEstadual> getDetalhesAssociados() {
		return detalhesAssociados;
	}
	public int getQtdInscricoesVencidas() {
		if(detalhesAssociados == null || detalhesAssociados.size() == 0){
			return 0;
		}
		
		for (DetalheInscricaoEstadual detalhe : detalhesAssociados) {
			if(detalhe.isInscricaoVencida()){
				qtdInscricoesVencidas++;
			}
		}
		
		return qtdInscricoesVencidas;
	}
	public int getQtdInscricoesProximaVencer() {
		if(detalhesAssociados == null || detalhesAssociados.size() == 0){
			return 0;
		}
		
		for (DetalheInscricaoEstadual detalhe : detalhesAssociados) {
			if(detalhe.isInscricaoVencida() == false){
				qtdInscricoesProximaVencer++;
			}
		}
		
		return qtdInscricoesProximaVencer;
	}
	public void setDetalhesAssociados(
			List<DetalheInscricaoEstadual> detalhesAssociados) {
		this.detalhesAssociados = detalhesAssociados;
	}
	public void setQtdInscricoesVencidas(int qtdInscricoesVencidas) {
		this.qtdInscricoesVencidas = qtdInscricoesVencidas;
	}
	public void setQtdInscricoesProximaVencer(int qtdInscricoesProximaVencer) {
		this.qtdInscricoesProximaVencer = qtdInscricoesProximaVencer;
	}
	
}

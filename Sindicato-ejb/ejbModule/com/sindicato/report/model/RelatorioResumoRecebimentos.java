package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioResumoRecebimentos implements Serializable {

	public RelatorioResumoRecebimentos(){
		detalhesDestino = new ArrayList<DetalhesDestinoRecebimento>();
	}
	
	private static final long serialVersionUID = 1L;
	private List<DetalhesDestinoRecebimento> detalhesDestino; 
	private BigDecimal totalRecebido;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<DetalhesDestinoRecebimento> getDetalhesDestino() {
		return detalhesDestino;
	}
	public BigDecimal getTotalRecebido() {
		totalRecebido = BigDecimal.ZERO;
		if(detalhesDestino == null || detalhesDestino.size() == 0){
			return totalRecebido;
		}
		
		for (DetalhesDestinoRecebimento detalhe : detalhesDestino) {
			totalRecebido = totalRecebido.add(detalhe.getTotalRecebido());
		}
		
		return totalRecebido;
	}
	public void setDetalhesDestino(List<DetalhesDestinoRecebimento> detalhesDestino) {
		this.detalhesDestino = detalhesDestino;
	}
	public void setTotalRecebido(BigDecimal totalRecebido) {
		this.totalRecebido = totalRecebido;
	}
	
}

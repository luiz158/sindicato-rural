package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetalhesDestinoRecebimento implements Serializable {

	public DetalhesDestinoRecebimento(){
		recebimentosDestino = new ArrayList<RecebimentoDia>();
	}
	
	private static final long serialVersionUID = 1L;

	private String destino;
	private List<RecebimentoDia> recebimentosDestino;
	private BigDecimal totalRecebido;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDestino() {
		return destino;
	}
	public List<RecebimentoDia> getRecebimentosDestino() {
		return recebimentosDestino;
	}
	public BigDecimal getTotalRecebido() {
		totalRecebido = BigDecimal.ZERO;
		if(recebimentosDestino == null || recebimentosDestino.size() == 0){
			return totalRecebido;
		}
		
		for (RecebimentoDia recebimento : recebimentosDestino) {
			totalRecebido = totalRecebido.add(recebimento.getValorRecebido());
		}
		
		return totalRecebido;
	}
	
	public void setTotalRecebido(BigDecimal totalRecebido) {
		this.totalRecebido = totalRecebido;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public void setRecebimentosDestino(List<RecebimentoDia> recebimentosDestino) {
		this.recebimentosDestino = recebimentosDestino;
	}
	
	
}

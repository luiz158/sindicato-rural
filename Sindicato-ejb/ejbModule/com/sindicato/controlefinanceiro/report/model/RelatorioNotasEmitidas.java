package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioNotasEmitidas implements Serializable {

	public RelatorioNotasEmitidas(){
		notasEmitidas = new ArrayList<DetalheNotasEmitidas>();
	}
	
	private static final long serialVersionUID = 1L;
	private List<DetalheNotasEmitidas> notasEmitidas;
	private BigDecimal valorTotalNotas;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<DetalheNotasEmitidas> getNotasEmitidas() {
		return notasEmitidas;
	}
	public BigDecimal getValorTotalNotas() {
		valorTotalNotas = BigDecimal.ZERO;
		if(notasEmitidas == null || notasEmitidas.size() == 0){
			return valorTotalNotas;
		}
		
		for (DetalheNotasEmitidas detalhe : notasEmitidas) {
			valorTotalNotas = valorTotalNotas.add(detalhe.getValorTotalDia());
		}
		
		return valorTotalNotas;
	}
	public void setNotasEmitidas(List<DetalheNotasEmitidas> notasEmitidas) {
		this.notasEmitidas = notasEmitidas;
	}
	public void setValorTotalNotas(BigDecimal valorTotalNotas) {
		this.valorTotalNotas = valorTotalNotas;
	}
	
	
	
}

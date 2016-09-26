package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioNotasEmitidas implements Serializable {

	public RelatorioNotasEmitidas(){
		notasEmitidasDia = new ArrayList<NotasEmitidasDia>();
	}
	
	private static final long serialVersionUID = 1L;
	private List<NotasEmitidasDia> notasEmitidasDia;
	private BigDecimal valorTotalNotas;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getValorTotalNotas() {
		valorTotalNotas = BigDecimal.ZERO;
		if(notasEmitidasDia == null || notasEmitidasDia.size() == 0){
			return valorTotalNotas;
		}
		
		for (NotasEmitidasDia detalhe : notasEmitidasDia) {
			if(detalhe.getValorTotalDia() == null){
				continue;
			}
			valorTotalNotas = valorTotalNotas.add(detalhe.getValorTotalDia());
		}
		
		return valorTotalNotas;
	}
	public void setValorTotalNotas(BigDecimal valorTotalNotas) {
		this.valorTotalNotas = valorTotalNotas;
	}
	public List<NotasEmitidasDia> getNotasEmitidasDia() {
		return notasEmitidasDia;
	}
	public void setNotasEmitidasDia(List<NotasEmitidasDia> notasEmitidasDia) {
		this.notasEmitidasDia = notasEmitidasDia;
	}
	
}

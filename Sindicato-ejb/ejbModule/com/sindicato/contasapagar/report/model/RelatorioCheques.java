package com.sindicato.contasapagar.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.sindicato.contasapagar.entity.ChequeEmitido;

public class RelatorioCheques implements Serializable {

	private static final long serialVersionUID = 1L;
	public FiltroRelatorioCheques filtro;
	public String titulo;
	public List<ChequeEmitido> resultado;
	public BigDecimal valorTotal;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public FiltroRelatorioCheques getFiltro() {
		return filtro;
	}
	public String getTitulo() {
		return titulo;
	}
	public List<ChequeEmitido> getResultado() {
		return resultado;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setFiltro(FiltroRelatorioCheques filtro) {
		this.filtro = filtro;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setResultado(List<ChequeEmitido> resultado) {
		this.resultado = resultado;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
		
	
	
}

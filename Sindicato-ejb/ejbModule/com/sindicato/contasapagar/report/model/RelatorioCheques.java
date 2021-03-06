package com.sindicato.contasapagar.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sindicato.contasapagar.entity.ChequeEmitido;

public class RelatorioCheques implements Serializable {

	public RelatorioCheques(){
		filtro = new FiltroRelatorioCheques();
		resultado = new ArrayList<ChequeEmitido>();
	}
	
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
		valorTotal = BigDecimal.ZERO;
		if(resultado == null || resultado.size() == 0){
			return valorTotal;
		}
		
		for (ChequeEmitido cheque : resultado) {
			valorTotal = valorTotal.add(cheque.getValor());
		}
		
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

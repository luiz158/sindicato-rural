package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetalhesNotaRecolhimentosAberto implements Serializable {

	public DetalhesNotaRecolhimentosAberto(){
		servicos = new ArrayList<ServicoRecolhimentosAberto>();
	}
	
	private static final long serialVersionUID = 1L;
	
	private int numeroNota;
	private Calendar dataBase;
	private List<ServicoRecolhimentosAberto> servicos;

	private BigDecimal valorARecolher;
	private BigDecimal valorJaRecolhido;
	
	private BigDecimal totalPendente;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getNumeroNota() {
		return numeroNota;
	}

	public Calendar getDataBase() {
		return dataBase;
	}

	public List<ServicoRecolhimentosAberto> getServicos() {
		return servicos;
	}

	public BigDecimal getValorARecolher() {
		valorARecolher = BigDecimal.ZERO;
		if(servicos == null || servicos.size() == 0){
			return valorARecolher;
		}
		
		for (ServicoRecolhimentosAberto servico : servicos) {
			valorARecolher = valorARecolher.add(servico.getValorARecolher());
		}
		
		return valorARecolher;
	}
	public BigDecimal getValorJaRecolhido() {
		valorJaRecolhido = BigDecimal.ZERO;
		if(servicos == null || servicos.size() == 0){
			return valorJaRecolhido;
		}
		
		for (ServicoRecolhimentosAberto servico : servicos) {
			if(servico.getValorJaRecolhido() == null) continue;
			
			valorJaRecolhido = valorJaRecolhido.add(servico.getValorJaRecolhido());
		}
		
		return valorJaRecolhido;
	}

	public BigDecimal getTotalPendente() {
		totalPendente = valorARecolher.subtract(valorJaRecolhido, MathContext.DECIMAL32);
		return totalPendente;
	}

	public void setValorJaRecolhido(BigDecimal valorJaRecolhido) {
		this.valorJaRecolhido = valorJaRecolhido;
	}
	public void setValorARecolher(BigDecimal valorNota) {
		this.valorARecolher = valorNota;
	}
	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}
	public void setDataBase(Calendar dataBase) {
		this.dataBase = dataBase;
	}

	public void setServicos(List<ServicoRecolhimentosAberto> servicos) {
		this.servicos = servicos;
	}
	
	
}

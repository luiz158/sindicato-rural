package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

	private BigDecimal valorNota;
	
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

	public BigDecimal getValorNota() {
		valorNota = BigDecimal.ZERO;
		if(servicos == null || servicos.size() == 0){
			return valorNota;
		}
		
		for (ServicoRecolhimentosAberto servico : servicos) {
			valorNota = valorNota.add(servico.getValor());
		}
		
		return valorNota;
	}

	public void setValorNota(BigDecimal valorNota) {
		this.valorNota = valorNota;
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

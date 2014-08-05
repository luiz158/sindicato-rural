package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClienteRecolhimentosAberto implements Serializable {

	public ClienteRecolhimentosAberto(){
		listaNotasPendentes = new ArrayList<DetalhesNotaRecolhimentosAberto>();
	}
	
	private static final long serialVersionUID = 1L;
	
	private int matricula;
	private String nome;
	private BigDecimal totalPendencias;
	
	private List<DetalhesNotaRecolhimentosAberto> listaNotasPendentes;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getMatricula() {
		return matricula;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getTotalPendencias() {
		totalPendencias = BigDecimal.ZERO;
		if(listaNotasPendentes == null || listaNotasPendentes.size() == 0){
			return totalPendencias;
		}
		
		for (DetalhesNotaRecolhimentosAberto nota : listaNotasPendentes) {
			totalPendencias = totalPendencias.add(nota.getValorNota());
		}
		
		return totalPendencias;
	}

	public List<DetalhesNotaRecolhimentosAberto> getListaNotasPendentes() {
		return listaNotasPendentes;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTotalPendencias(BigDecimal totalPendencias) {
		this.totalPendencias = totalPendencias;
	}

	public void setListaNotasPendentes(
			List<DetalhesNotaRecolhimentosAberto> listaNotasPendentes) {
		this.listaNotasPendentes = listaNotasPendentes;
	}
	
}

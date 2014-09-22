package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRecolhimentosAberto implements Serializable {

	public RelatorioRecolhimentosAberto(){
		listaPendenciasPorCliente = new ArrayList<ClienteRecolhimentosAberto>(); 
	}
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal totalPendencias;
	private List<ClienteRecolhimentosAberto> listaPendenciasPorCliente;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getTotalPendencias() {
		totalPendencias = BigDecimal.ZERO;
		if(listaPendenciasPorCliente == null || listaPendenciasPorCliente.size() == 0){
			return totalPendencias;
		}
		
		for (ClienteRecolhimentosAberto cliente : listaPendenciasPorCliente) {
			totalPendencias = totalPendencias.add(cliente.getTotalPendencias());
		}
		
		return totalPendencias;
	}
	public List<ClienteRecolhimentosAberto> getListaPendenciasPorCliente() {
		return listaPendenciasPorCliente;
	}
	public void setTotalPendencias(BigDecimal totalPendencias) {
		this.totalPendencias = totalPendencias;
	}
	public void setListaPendenciasPorCliente(
			List<ClienteRecolhimentosAberto> listaPendenciasPorCliente) {
		this.listaPendenciasPorCliente = listaPendenciasPorCliente;
	}
	
}

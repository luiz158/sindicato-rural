package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServicoRetencoesRecolher implements Serializable {

	public ServicoRetencoesRecolher(){
		listaClientes = new ArrayList<ClienteRetencoesRecolher>();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeServico;
	private List<ClienteRetencoesRecolher> listaClientes;
	private BigDecimal totalRetencoesServico;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<ClienteRetencoesRecolher> getListaClientes() {
		return listaClientes;
	}
	public BigDecimal getTotalRetencoesServico() {
		totalRetencoesServico = BigDecimal.ZERO;
		if(listaClientes == null || listaClientes.size() == 0){
			return totalRetencoesServico;
		}
		
		for (ClienteRetencoesRecolher cliente : listaClientes) {
			totalRetencoesServico = totalRetencoesServico.add(cliente.getValor());
		}
		
		return totalRetencoesServico;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	public void setListaClientes(List<ClienteRetencoesRecolher> listaClientes) {
		this.listaClientes = listaClientes;
	}
	public void setTotalRetencoesServico(BigDecimal totalRetencoesServico) {
		this.totalRetencoesServico = totalRetencoesServico;
	}
	
	
	
}

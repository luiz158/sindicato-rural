package com.sindicato.report.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioAssociados implements Serializable {

	public RelatorioAssociados(){
		this.detalhesAssociado = new ArrayList<DetalhesAssociado>();
	}
	
	private static final long serialVersionUID = 1L;	private int totalAssociados;
	private int totalAssociadosEmDia;
	private int totalAssociadosEmAtraso;
	
	List<DetalhesAssociado> detalhesAssociado;


	public int getTotalAssociados() {
		return totalAssociados;
	}
	public int getTotalAssociadosEmDia() {
		return totalAssociadosEmDia;
	}
	public int getTotalAssociadosEmAtraso() {
		return totalAssociadosEmAtraso;
	}
	public List<DetalhesAssociado> getDetalhesAssociado() {
		return detalhesAssociado;
	}
	public void setTotalAssociados(int totalAssociados) {
		this.totalAssociados = totalAssociados;
	}
	public void setTotalAssociadosEmDia(int totalAssociadosEmDia) {
		this.totalAssociadosEmDia = totalAssociadosEmDia;
	}
	public void setTotalAssociadosEmAtraso(int totalAssociadosEmAtraso) {
		this.totalAssociadosEmAtraso = totalAssociadosEmAtraso;
	}
	public void setDetalhesAssociado(List<DetalhesAssociado> detalhesAssociado) {
		this.detalhesAssociado = detalhesAssociado;
	}
	
}

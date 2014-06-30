package com.sindicato.report.model;

import java.util.ArrayList;
import java.util.List;

public class RelatorioAssociados {

	public RelatorioAssociados(){
		this.detalhesAssociado = new ArrayList<DetalhesAssociado>();
	}
	
	private int totalAssociados;
	private int totalAssociadosEmDia;
	private int totalAssociadosEmAtrazo;
	
	List<DetalhesAssociado> detalhesAssociado;


	public int getTotalAssociados() {
		return totalAssociados;
	}
	public int getTotalAssociadosEmDia() {
		return totalAssociadosEmDia;
	}
	public int getTotalAssociadosEmAtrazo() {
		return totalAssociadosEmAtrazo;
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
	public void setTotalAssociadosEmAtrazo(int totalAssociadosEmAtrazo) {
		this.totalAssociadosEmAtrazo = totalAssociadosEmAtrazo;
	}
	public void setDetalhesAssociado(List<DetalhesAssociado> detalhesAssociado) {
		this.detalhesAssociado = detalhesAssociado;
	}
	
}

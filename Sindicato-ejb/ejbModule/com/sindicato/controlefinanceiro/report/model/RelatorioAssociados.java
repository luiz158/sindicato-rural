package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioAssociados implements Serializable {

	public RelatorioAssociados(){
		this.detalhesAssociado = new ArrayList<DetalhesAssociado>();
	}

	private FiltroAssociados filtro = new FiltroAssociados();

	private static final long serialVersionUID = 1L;	
	
	private int totalAssociados;
	private int totalClientesDesativadosComDebito;
	private int totalAssociadosEmDia;
	private int totalAssociadosEmAtraso;
	
	List<DetalhesAssociado> detalhesAssociado;


	public void addTotalClientesDesativadosComDebito(){
		totalClientesDesativadosComDebito++;
	}
	public void addTotalAssociadosEmDia(){
		totalAssociadosEmDia++;
	}
	public void addTotalAssociadosEmAtraso(){
		totalAssociadosEmAtraso++;
	}
	public void addTotalAssociados(){
		totalAssociados++;
	}
	
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
	public FiltroAssociados getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroAssociados filtro) {
		this.filtro = filtro;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTotalClientesDesativadosComDebito() {
		return totalClientesDesativadosComDebito;
	}
	public void setTotalClientesDesativadosComDebito(int totalClientesDesativadosComDebito) {
		this.totalClientesDesativadosComDebito = totalClientesDesativadosComDebito;
	}
	
}

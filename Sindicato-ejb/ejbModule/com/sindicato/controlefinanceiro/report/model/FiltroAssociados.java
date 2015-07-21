package com.sindicato.controlefinanceiro.report.model;

import java.util.Calendar;

import com.sindicato.report.util.FiltroBooleanEnum;

public class FiltroAssociados {

	private int matricula;
	private Calendar socioDe;
	private Calendar socioAte;
	private FiltroBooleanEnum clientesAtivos = FiltroBooleanEnum.TODOS;
	private FiltroBooleanEnum pendentes = FiltroBooleanEnum.TODOS;
	private String nome;
	private int situacaoDe;
	private int situacaoAte;
	
	
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public Calendar getSocioDe() {
		return socioDe;
	}
	public void setSocioDe(Calendar socioDe) {
		this.socioDe = socioDe;
	}
	public Calendar getSocioAte() {
		return socioAte;
	}
	public void setSocioAte(Calendar socioAte) {
		this.socioAte = socioAte;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getSituacaoDe() {
		return situacaoDe;
	}
	public void setSituacaoDe(int situacaoDe) {
		this.situacaoDe = situacaoDe;
	}
	public int getSituacaoAte() {
		return situacaoAte;
	}
	public void setSituacaoAte(int situacaoAte) {
		this.situacaoAte = situacaoAte;
	}
	public FiltroBooleanEnum getClientesAtivos() {
		return clientesAtivos;
	}
	public void setClientesAtivos(FiltroBooleanEnum clientesAtivos) {
		this.clientesAtivos = clientesAtivos;
	}
	public FiltroBooleanEnum getPendentes() {
		return pendentes;
	}
	public void setPendentes(FiltroBooleanEnum pendentes) {
		this.pendentes = pendentes;
	}

	
}

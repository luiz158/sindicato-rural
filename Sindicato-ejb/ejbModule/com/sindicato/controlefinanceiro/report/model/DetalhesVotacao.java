package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;

public class DetalhesVotacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int matricula;
	private String nome;
	private String observacao;
	
	private String produtorRuralDesde;
	private String socioDesde;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getMatricula() {
		return matricula;
	}
	public String getNome() {
		return nome;
	}
	public String getObservacao() {
		return observacao;
	}
	public String getProdutorRuralDesde() {
		return produtorRuralDesde;
	}
	public String getSocioDesde() {
		return socioDesde;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public void setProdutorRuralDesde(String produtorRuralDesde) {
		this.produtorRuralDesde = produtorRuralDesde;
	}
	public void setSocioDesde(String socioDesde) {
		this.socioDesde = socioDesde;
	}
	
}

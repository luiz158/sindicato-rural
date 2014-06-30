package com.sindicato.report.model;

public class DetalhesAssociado {

	private int matricula;
	private String nome;
	private String telefone;
	private String observacao;
	
	private String produtorRuralDesde;
	private String socioDesde;
	
	private String situacao;
	private String statusAssociado;
	
	
	public int getMatricula() {
		return matricula;
	}
	public String getNome() {
		return nome;
	}
	public String getTelefone() {
		return telefone;
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
	public String getSituacao() {
		return situacao;
	}
	public String getStatusAssociado() {
		return statusAssociado;
	}
	public void setStatusAssociado(String statusAssociado) {
		this.statusAssociado = statusAssociado;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}

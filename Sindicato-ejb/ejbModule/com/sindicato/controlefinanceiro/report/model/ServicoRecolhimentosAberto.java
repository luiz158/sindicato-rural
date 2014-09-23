package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServicoRecolhimentosAberto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String descricao;
	private BigDecimal valorARecolher;
	private BigDecimal valorJaRecolhido;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public BigDecimal getValorARecolher() {
		return valorARecolher;
	}
	public BigDecimal getValorJaRecolhido() {
		return valorJaRecolhido;
	}
	public void setValorARecolher(BigDecimal valorARecolher) {
		this.valorARecolher = valorARecolher;
	}
	public void setValorJaRecolhido(BigDecimal valorJaRecolhido) {
		this.valorJaRecolhido = valorJaRecolhido;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}

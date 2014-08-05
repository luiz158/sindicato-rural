package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServicoRecolhimentosAberto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String descricao;
	private BigDecimal valor;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}

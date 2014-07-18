package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DetalhesServico implements Serializable {

	private static final long serialVersionUID = 1L;
	private String servico;
	private BigDecimal valor;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getServico() {
		return servico;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}

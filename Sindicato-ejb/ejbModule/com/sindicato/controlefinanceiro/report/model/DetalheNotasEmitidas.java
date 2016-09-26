package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DetalheNotasEmitidas implements Serializable {

	private static final long serialVersionUID = 1L;
	private int primeiraNota;
	private int ultimaNota;
	private BigDecimal valorTotalNota;
	private String servico;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getPrimeiraNota() {
		return primeiraNota;
	}
	public int getUltimaNota() {
		return ultimaNota;
	}
	public void setPrimeiraNota(int primeiraNota) {
		this.primeiraNota = primeiraNota;
	}
	public void setUltimaNota(int ultimaNota) {
		this.ultimaNota = ultimaNota;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public BigDecimal getValorTotalNota() {
		return valorTotalNota;
	}
	public void setValorTotalNota(BigDecimal valorTotalNota) {
		this.valorTotalNota = valorTotalNota;
	}

}

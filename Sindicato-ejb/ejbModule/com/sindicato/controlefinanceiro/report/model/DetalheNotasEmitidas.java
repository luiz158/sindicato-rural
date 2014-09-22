package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class DetalheNotasEmitidas implements Serializable {

	private static final long serialVersionUID = 1L;
	private Calendar dataEmissaoNota;
	private int primeiraNota;
	private int ultimaNota;
	private BigDecimal valorTotalDia;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Calendar getDataEmissaoNota() {
		return dataEmissaoNota;
	}
	public int getPrimeiraNota() {
		return primeiraNota;
	}
	public int getUltimaNota() {
		return ultimaNota;
	}
	public BigDecimal getValorTotalDia() {
		return valorTotalDia;
	}
	public void setDataEmissaoNota(Calendar dataEmissaoNota) {
		this.dataEmissaoNota = dataEmissaoNota;
	}
	public void setPrimeiraNota(int primeiraNota) {
		this.primeiraNota = primeiraNota;
	}
	public void setUltimaNota(int ultimaNota) {
		this.ultimaNota = ultimaNota;
	}
	public void setValorTotalDia(BigDecimal valorTotalDia) {
		this.valorTotalDia = valorTotalDia;
	}

}

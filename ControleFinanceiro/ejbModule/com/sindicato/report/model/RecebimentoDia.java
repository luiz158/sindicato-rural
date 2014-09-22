package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class RecebimentoDia implements Serializable {

	private static final long serialVersionUID = 1L;

	private Calendar dataRecebimento;
	private BigDecimal valorRecebido;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Calendar getDataRecebimento() {
		return dataRecebimento;
	}
	public BigDecimal getValorRecebido() {
		return valorRecebido;
	}
	public void setDataRecebimento(Calendar dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public void setValorRecebido(BigDecimal valorRecebido) {
		this.valorRecebido = valorRecebido;
	}
}

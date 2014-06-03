package com.sindicato.result;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class MensalidadePaga implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Calendar dataPagamento;
	private BigDecimal valor;
	private String descricaoMensalidade;

	public Calendar getDataPagamento() {
		return dataPagamento;
	}
	public String getDescricaoMensalidade() {
		return descricaoMensalidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public void setDescricaoMensalidade(String descricaoMensalidade) {
		this.descricaoMensalidade = descricaoMensalidade;
	}
	
}

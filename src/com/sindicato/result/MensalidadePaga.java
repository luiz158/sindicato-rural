package com.sindicato.result;

import java.util.Calendar;

public class MensalidadePaga {
	
	private Calendar dataPagamento;
	private String descricaoMensalidade;

	public Calendar getDataPagamento() {
		return dataPagamento;
	}
	public String getDescricaoMensalidade() {
		return descricaoMensalidade;
	}
	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public void setDescricaoMensalidade(String descricaoMensalidade) {
		this.descricaoMensalidade = descricaoMensalidade;
	}
	
}

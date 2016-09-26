package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotasEmitidasDia implements Serializable{

	private static final long serialVersionUID = 1L;
	private Calendar dataEmissaoNota;
	private BigDecimal valorTotalDia;

	private List<DetalheNotasEmitidas> notas;

	public Calendar getDataEmissaoNota() {
		return dataEmissaoNota;
	}

	public void setDataEmissaoNota(Calendar dataEmissaoNota) {
		this.dataEmissaoNota = dataEmissaoNota;
	}

	public BigDecimal getValorTotalDia() {
		return valorTotalDia;
	}

	public void setValorTotalDia(BigDecimal valorTotalDia) {
		this.valorTotalDia = valorTotalDia;
	}

	public List<DetalheNotasEmitidas> getNotas() {
		if(notas == null){
			notas = new ArrayList<>();
		}
		return notas;
	}

	public void setNotas(List<DetalheNotasEmitidas> notas) {
		this.notas = notas;
	}
}

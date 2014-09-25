package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;

public class DetalhesClienteRecolhimentos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int matricula;
	private String nome;
	private BigDecimal valorRecolhido;
	private BigDecimal valorDoServico;
	private Calendar dataRecolhimento;
	private Calendar dataBase;
	private int numeroNota;
	private Calendar dataEmissaoNota;
	private BigDecimal variacao;

	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getValorRecolhido() {
		return valorRecolhido;
	}
	public void setValorRecolhido(BigDecimal valorRecolhido) {
		this.valorRecolhido = valorRecolhido;
	}
	public Calendar getDataRecolhimento() {
		return dataRecolhimento;
	}
	public void setDataRecolhimento(Calendar dataRecolhimento) {
		this.dataRecolhimento = dataRecolhimento;
	}
	public Calendar getDataBase() {
		return dataBase;
	}
	public void setDataBase(Calendar dataBase) {
		this.dataBase = dataBase;
	}
	public int getNumeroNota() {
		return numeroNota;
	}
	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}
	public Calendar getDataEmissaoNota() {
		return dataEmissaoNota;
	}
	public void setDataEmissaoNota(Calendar dataEmissaoNota) {
		this.dataEmissaoNota = dataEmissaoNota;
	}
	public BigDecimal getVariacao() {
		variacao = BigDecimal.ZERO;
		if (valorDoServico == null || valorDoServico.compareTo(BigDecimal.ZERO) == 0) {
			return variacao;
		}
		variacao = valorDoServico.subtract(valorRecolhido, MathContext.DECIMAL32);	
		
		return variacao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigDecimal getValorDoServico() {
		return valorDoServico;
	}
	public void setValorDoServico(BigDecimal valorDoServico) {
		this.valorDoServico = valorDoServico;
	}
}

package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class ClienteRetencoesRecolher implements Serializable {

	private static final long serialVersionUID = 1L;
	private int matricula;
	private String nome;
	private BigDecimal valor;
	private Calendar dataBase;
	private int numeroNota;
	private Calendar dataEmissaoNota;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getMatricula() {
		return matricula;
	}
	public String getNome() {
		return nome;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public Calendar getDataBase() {
		return dataBase;
	}
	public int getNumeroNota() {
		return numeroNota;
	}
	public Calendar getDataEmissaoNota() {
		return dataEmissaoNota;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public void setDataBase(Calendar dataBase) {
		this.dataBase = dataBase;
	}
	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}
	public void setDataEmissaoNota(Calendar dataEmissaoNota) {
		this.dataEmissaoNota = dataEmissaoNota;
	}
	
}

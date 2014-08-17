package com.sindicato.report.model;

import java.io.Serializable;
import java.util.Calendar;

public class DetalheInscricaoEstadual implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int matricula;
	private String nome;
	private String documento;
	private Calendar vencimentoInscricao;
	private boolean inscricaoVencida;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getMatricula() {
		return matricula;
	}
	public String getNome() {
		return nome;
	}
	public String getDocumento() {
		return documento;
	}
	public Calendar getVencimentoInscricao() {
		return vencimentoInscricao;
	}
	public boolean isInscricaoVencida() {
		inscricaoVencida = false;
		if(vencimentoInscricao == null){
			return inscricaoVencida;
		}
		if(vencimentoInscricao.compareTo(Calendar.getInstance()) <= 0){
			inscricaoVencida = true;
		}
		
		return inscricaoVencida;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public void setVencimentoInscricao(Calendar vencimentoInscricao) {
		this.vencimentoInscricao = vencimentoInscricao;
	}
	public void setInscricaoVencida(boolean inscricaoVencida) {
		this.inscricaoVencida = inscricaoVencida;
	}
}

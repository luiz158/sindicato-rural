package com.sindicato.contasapagar.report.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.sindicato.contasapagar.entity.Banco;


public class FiltroRelatorioContas implements Serializable {

	public FiltroRelatorioContas() {
		debitoConta = FiltroBooleanEnum.TODOS;
		excluida = FiltroBooleanEnum.TODOS;
	}
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Calendar vencimentoDe;
	private Calendar vencimentoAte;
	private String favorecido;
	private BigDecimal valorDe;
	private BigDecimal valorAte;
	private List<Banco> bancosDebito;
	private String historico;
	private String classificacaoContabil;
	private FiltroBooleanEnum excluida;

	private FiltroBooleanEnum debitoConta;
	
	public int getId() {
		return id;
	}
	public Calendar getVencimentoDe() {
		return vencimentoDe;
	}
	public Calendar getVencimentoAte() {
		return vencimentoAte;
	}
	public String getFavorecido() {
		return favorecido;
	}
	public BigDecimal getValorDe() {
		return valorDe;
	}
	public BigDecimal getValorAte() {
		return valorAte;
	}
	public List<Banco> getBancosDebito() {
		return bancosDebito;
	}
	public String getHistorico() {
		return historico;
	}
	public String getClassificacaoContabil() {
		return classificacaoContabil;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setVencimentoDe(Calendar vencimentoDe) {
		this.vencimentoDe = vencimentoDe;
	}
	public void setVencimentoAte(Calendar vencimentoAte) {
		this.vencimentoAte = vencimentoAte;
	}
	public void setFavorecido(String favorecido) {
		this.favorecido = favorecido;
	}
	public void setValorDe(BigDecimal valorDe) {
		this.valorDe = valorDe;
	}
	public void setValorAte(BigDecimal valorAte) {
		this.valorAte = valorAte;
	}
	public void setBancosDebito(List<Banco> bancosDebito) {
		this.bancosDebito = bancosDebito;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public void setClassificacaoContabil(String classificacaoContabil) {
		this.classificacaoContabil = classificacaoContabil;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public FiltroBooleanEnum getDebitoConta() {
		return debitoConta;
	}
	public FiltroBooleanEnum getExcluida() {
		return excluida;
	}
	public void setDebitoConta(FiltroBooleanEnum debitoConta) {
		this.debitoConta = debitoConta;
	}
	public void setExcluida(FiltroBooleanEnum excluida) {
		this.excluida = excluida;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bancosDebito == null) ? 0 : bancosDebito.hashCode());
		result = prime
				* result
				+ ((classificacaoContabil == null) ? 0 : classificacaoContabil
						.hashCode());
		result = prime * result
				+ ((debitoConta == null) ? 0 : debitoConta.hashCode());
		result = prime * result
				+ ((excluida == null) ? 0 : excluida.hashCode());
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result
				+ ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((valorAte == null) ? 0 : valorAte.hashCode());
		result = prime * result + ((valorDe == null) ? 0 : valorDe.hashCode());
		result = prime * result
				+ ((vencimentoAte == null) ? 0 : vencimentoAte.hashCode());
		result = prime * result
				+ ((vencimentoDe == null) ? 0 : vencimentoDe.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiltroRelatorioContas other = (FiltroRelatorioContas) obj;
		if (bancosDebito == null) {
			if (other.bancosDebito != null)
				return false;
		} else if (!bancosDebito.equals(other.bancosDebito))
			return false;
		if (classificacaoContabil == null) {
			if (other.classificacaoContabil != null)
				return false;
		} else if (!classificacaoContabil.equals(other.classificacaoContabil))
			return false;
		if (debitoConta != other.debitoConta)
			return false;
		if (excluida != other.excluida)
			return false;
		if (favorecido == null) {
			if (other.favorecido != null)
				return false;
		} else if (!favorecido.equals(other.favorecido))
			return false;
		if (historico == null) {
			if (other.historico != null)
				return false;
		} else if (!historico.equals(other.historico))
			return false;
		if (id != other.id)
			return false;
		if (valorAte == null) {
			if (other.valorAte != null)
				return false;
		} else if (!valorAte.equals(other.valorAte))
			return false;
		if (valorDe == null) {
			if (other.valorDe != null)
				return false;
		} else if (!valorDe.equals(other.valorDe))
			return false;
		if (vencimentoAte == null) {
			if (other.vencimentoAte != null)
				return false;
		} else if (!vencimentoAte.equals(other.vencimentoAte))
			return false;
		if (vencimentoDe == null) {
			if (other.vencimentoDe != null)
				return false;
		} else if (!vencimentoDe.equals(other.vencimentoDe))
			return false;
		return true;
	}
	
}

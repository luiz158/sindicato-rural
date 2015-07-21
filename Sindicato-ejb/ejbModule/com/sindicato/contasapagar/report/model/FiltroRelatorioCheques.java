package com.sindicato.contasapagar.report.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.report.util.FiltroBooleanEnum;

public class FiltroRelatorioCheques {
	
	private int id;
	private List<Banco> bancos;
	private Long identificacao;
	private BigDecimal valorDe;
	private BigDecimal valorAte;
	private String favorecido;
	private String versoCheque;
	private Calendar emissaoDe;
	private Calendar emissaoAte;
	private FiltroBooleanEnum cancelado = FiltroBooleanEnum.TODOS;
	public int getId() {
		return id;
	}
	public List<Banco> getBancos() {
		return bancos;
	}
	public Long getIdentificacao() {
		return identificacao;
	}
	public BigDecimal getValorDe() {
		return valorDe;
	}
	public BigDecimal getValorAte() {
		return valorAte;
	}
	public String getFavorecido() {
		return favorecido;
	}
	public String getVersoCheque() {
		return versoCheque;
	}
	public Calendar getEmissaoDe() {
		return emissaoDe;
	}
	public Calendar getEmissaoAte() {
		return emissaoAte;
	}
	public FiltroBooleanEnum getCancelado() {
		return cancelado;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}
	public void setIdentificacao(Long identificacao) {
		this.identificacao = identificacao;
	}
	public void setValorDe(BigDecimal valorDe) {
		this.valorDe = valorDe;
	}
	public void setValorAte(BigDecimal valorAte) {
		this.valorAte = valorAte;
	}
	public void setFavorecido(String favorecido) {
		this.favorecido = favorecido;
	}
	public void setVersoCheque(String versoCheque) {
		this.versoCheque = versoCheque;
	}
	public void setEmissaoDe(Calendar emissaoDe) {
		this.emissaoDe = emissaoDe;
	}
	public void setEmissaoAte(Calendar emissaoAte) {
		this.emissaoAte = emissaoAte;
	}
	public void setCancelado(FiltroBooleanEnum cancelado) {
		this.cancelado = cancelado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bancos == null) ? 0 : bancos.hashCode());
		result = prime * result
				+ ((cancelado == null) ? 0 : cancelado.hashCode());
		result = prime * result
				+ ((emissaoAte == null) ? 0 : emissaoAte.hashCode());
		result = prime * result
				+ ((emissaoDe == null) ? 0 : emissaoDe.hashCode());
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
		result = prime * result
				+ ((valorAte == null) ? 0 : valorAte.hashCode());
		result = prime * result + ((valorDe == null) ? 0 : valorDe.hashCode());
		result = prime * result
				+ ((versoCheque == null) ? 0 : versoCheque.hashCode());
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
		FiltroRelatorioCheques other = (FiltroRelatorioCheques) obj;
		if (bancos == null) {
			if (other.bancos != null)
				return false;
		} else if (!bancos.equals(other.bancos))
			return false;
		if (cancelado != other.cancelado)
			return false;
		if (emissaoAte == null) {
			if (other.emissaoAte != null)
				return false;
		} else if (!emissaoAte.equals(other.emissaoAte))
			return false;
		if (emissaoDe == null) {
			if (other.emissaoDe != null)
				return false;
		} else if (!emissaoDe.equals(other.emissaoDe))
			return false;
		if (favorecido == null) {
			if (other.favorecido != null)
				return false;
		} else if (!favorecido.equals(other.favorecido))
			return false;
		if (id != other.id)
			return false;
		if (identificacao == null) {
			if (other.identificacao != null)
				return false;
		} else if (!identificacao.equals(other.identificacao))
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
		if (versoCheque == null) {
			if (other.versoCheque != null)
				return false;
		} else if (!versoCheque.equals(other.versoCheque))
			return false;
		return true;
	}
	
	
	

}

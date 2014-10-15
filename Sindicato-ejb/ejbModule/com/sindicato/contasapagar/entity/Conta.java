package com.sindicato.contasapagar.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sindicato.contasapagar.entity.Enum.StatusConta;

@Entity
@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName = "SEQ_CONTA", name = "seqConta")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqConta")
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Calendar vencimento;
	
	private String favorecido;
	
	@Column(nullable=false)
	private BigDecimal valor;
	private boolean debitoConta;
	
	@ManyToOne(optional=true)
	private Banco debitoBanco;
	
	private StatusConta status;
	private String historico;
	private String classificacaoContabil;
	
	
	public int getId() {
		return id;
	}
	public Calendar getVencimento() {
		return vencimento;
	}
	public String getFavorecido() {
		return favorecido;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public boolean isDebitoConta() {
		return debitoConta;
	}
	public StatusConta getStatus() {
		return status;
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
	public void setVencimento(Calendar vencimento) {
		this.vencimento = vencimento;
	}
	public void setFavorecido(String favorecido) {
		this.favorecido = favorecido;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public void setDebitoConta(boolean debitoConta) {
		this.debitoConta = debitoConta;
	}
	public void setStatus(StatusConta status) {
		this.status = status;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public void setClassificacaoContabil(String classificacaoContabil) {
		this.classificacaoContabil = classificacaoContabil;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((classificacaoContabil == null) ? 0 : classificacaoContabil
						.hashCode());
		result = prime * result + (debitoConta ? 1231 : 1237);
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result
				+ ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result
				+ ((vencimento == null) ? 0 : vencimento.hashCode());
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
		Conta other = (Conta) obj;
		if (classificacaoContabil == null) {
			if (other.classificacaoContabil != null)
				return false;
		} else if (!classificacaoContabil.equals(other.classificacaoContabil))
			return false;
		if (debitoConta != other.debitoConta)
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
		if (status != other.status)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (vencimento == null) {
			if (other.vencimento != null)
				return false;
		} else if (!vencimento.equals(other.vencimento))
			return false;
		return true;
	}
	
	
	
}

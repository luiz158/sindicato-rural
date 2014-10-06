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

import com.sindicato.contasapagar.entity.Enum.StatusCheque;

//@Entity
@SequenceGenerator(allocationSize=1, initialValue=1, name="seqChequeEmitido", sequenceName="SEQ_CHEQUEEMITIDO")
public class ChequeEmitido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqChequeEmitido")
	private int id;
	
	@ManyToOne
	private Banco banco;
	
	@Column(nullable=false)
	private String identificacao;
	
	@Column(nullable=false)
	private BigDecimal valor;
	
	@Column(nullable=false)
	private String favorecido;
	private String versoCheque;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar emissao;
	
	private StatusCheque status;

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public Banco getBanco() {
		return banco;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public String getFavorecido() {
		return favorecido;
	}
	public String getVersoCheque() {
		return versoCheque;
	}
	public Calendar getEmissao() {
		return emissao;
	}
	public StatusCheque getStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public void setFavorecido(String favorecido) {
		this.favorecido = favorecido;
	}
	public void setVersoCheque(String versoCheque) {
		this.versoCheque = versoCheque;
	}
	public void setEmissao(Calendar emissao) {
		this.emissao = emissao;
	}
	public void setStatus(StatusCheque status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result + ((emissao == null) ? 0 : emissao.hashCode());
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ChequeEmitido other = (ChequeEmitido) obj;
		if (banco == null) {
			if (other.banco != null)
				return false;
		} else if (!banco.equals(other.banco))
			return false;
		if (emissao == null) {
			if (other.emissao != null)
				return false;
		} else if (!emissao.equals(other.emissao))
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
		if (status != other.status)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (versoCheque == null) {
			if (other.versoCheque != null)
				return false;
		} else if (!versoCheque.equals(other.versoCheque))
			return false;
		return true;
	}
	
	
	
}
package com.sindicato.contasapagar.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(allocationSize=1, initialValue=1, name="seqChequeEmitido", sequenceName="SEQ_CHEQUEEMITIDO")
public class ChequeEmitido implements Serializable {

	public ChequeEmitido(){
		contasPagas = new ArrayList<Conta>();
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqChequeEmitido")
	private int id;
	
	@ManyToOne
	private Banco banco;
	
	@Column(nullable=false)
	private Long identificacao;
	
	@Column(nullable=false)
	private BigDecimal valor;
	
	@Column(nullable=false, length=500)
	private String favorecido;
	
	@Column(length=500)
	private String versoCheque;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar emissao = Calendar.getInstance();
	
	private boolean cancelado;

	@ManyToMany(fetch=FetchType.LAZY)
	private List<Conta> contasPagas;
	
	@Transient
	private String emissaoFormatado;
	@Transient
	private String valorFormatado;
	
	public String getValorFormatado(){
		if(valor == null){
			valorFormatado = "R$ 0,00";
		} else {
			valorFormatado = NumberFormat.getCurrencyInstance().format(valor);
		}
		return valorFormatado;
	}
	public String getEmissaoFormatado(){
		if(emissao == null){
			emissaoFormatado = "";
		} else {
			emissaoFormatado = new SimpleDateFormat("dd/MM/yyyy").format(emissao.getTime());
		}
		return emissaoFormatado;
	}
	public void addConta(Conta conta){
		contasPagas.add(conta);
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public Banco getBanco() {
		return banco;
	}
	public BigDecimal getValor() {
		if(contasPagas == null || contasPagas.size() == 0){
			return BigDecimal.ZERO;
		}
		valor = BigDecimal.ZERO;
		for (Conta conta : contasPagas) {
			valor = valor.add(conta.getValor());
		}
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
	public List<Conta> getContasPagas() {
		return contasPagas;
	}
	public boolean isCancelado() {
		return cancelado;
	}
	public Long getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(Long identificacao) {
		this.identificacao = identificacao;
	}
	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
	public void setContasPagas(List<Conta> contasPagas) {
		this.contasPagas = contasPagas;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result + (cancelado ? 1231 : 1237);
		result = prime * result
				+ ((contasPagas == null) ? 0 : contasPagas.hashCode());
		result = prime * result + ((emissao == null) ? 0 : emissao.hashCode());
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
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
		if (cancelado != other.cancelado)
			return false;
		if (contasPagas == null) {
			if (other.contasPagas != null)
				return false;
		} else if (!contasPagas.equals(other.contasPagas))
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

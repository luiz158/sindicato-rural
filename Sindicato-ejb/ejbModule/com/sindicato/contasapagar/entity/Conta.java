package com.sindicato.contasapagar.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName = "SEQ_CONTA", name = "seqConta")
public class Conta implements Serializable {

	public Conta(){
		chequesPagamento = new ArrayList<ChequeEmitido>();
	}
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqConta")
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Calendar vencimento;
	@Transient
	private String vencimentoFormatado;
	
	private String favorecido;
	
	@Column(nullable=false)
	private BigDecimal valor;
	@Transient
	private String valorFormatado;
	
	private boolean debitoConta;
	
	@ManyToOne(optional=true)
	private Banco debitoBanco;
	
	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(mappedBy = "contasPagas", targetEntity = ChequeEmitido.class, fetch=FetchType.EAGER)
	private List<ChequeEmitido> chequesPagamento;
	
	private String historico;
	private String classificacaoContabil;
	
	private boolean excluida;

	
	public String getVencimentoFormatado() {
		if(vencimento == null){
			return "";
		}
		vencimentoFormatado = new SimpleDateFormat("dd/MM/yyyy").format(vencimento.getTime());
		return vencimentoFormatado;
	}
	public String getValorFormatado() {
		if(valor == null){
			return "";
		}
		valorFormatado = NumberFormat.getCurrencyInstance().format(valor);
		return valorFormatado;
	}
	public String getDescricao(){
		StringBuilder descricao = new StringBuilder();
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		
		descricao.append("Vencimento: ");
		descricao.append(new SimpleDateFormat("dd/MM/yyyy").format(this.getVencimento().getTime()));
		descricao.append(" Valor: R$ ");
		descricao.append(numberFormat.format(this.getValor()));
		
		descricao.append(" Favorecido: ");
		descricao.append(favorecido);
		
		return descricao.toString();
	}
	
	public void addChequePagamento(ChequeEmitido cheque){
		chequesPagamento.add(cheque);
	}
	public boolean estaVencida(){
		if(vencimento == null){
			return false;
		}
		boolean passouVencimento = (Calendar.getInstance().after(this.vencimento));
		return passouVencimento;
	}
	public boolean jaEstaPaga(){
		/*
		 * NÃO ESTA PAGA AINDA
		 * - debito automatico e antes vencimento
		 * - não debito automatico e chequePagamento == null
		 * 
		 * JA ESTA PAGA
		 * - debito automatico e após o vencimento
		 * - não debito automatico e chequePagamento != null
		 * */
		boolean paga = false;
		if(this.isDebitoConta()){
			if(this.estaVencida()){
				paga = true; 
			} 
		} else {
			if(chequesPagamento != null && chequesPagamento.size() > 0){
				for (ChequeEmitido cheque : chequesPagamento) {
					if(cheque.isCancelado() == false){
						paga = true;
					}
				}
			}
		}
		return paga;
	}
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
	public String getHistorico() {
		return historico;
	}
	public String getClassificacaoContabil() {
		return classificacaoContabil;
	}
	public Banco getDebitoBanco() {
		return debitoBanco;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<ChequeEmitido> getChequesPagamento() {
		return chequesPagamento;
	}
	public boolean isExcluida() {
		return excluida;
	}
	public void setExcluida(boolean excluida) {
		this.excluida = excluida;
	}
	public void setChequesPagamento(List<ChequeEmitido> chequesPagamento) {
		this.chequesPagamento = chequesPagamento;
	}
	public void setDebitoBanco(Banco debitoBanco) {
		this.debitoBanco = debitoBanco;
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
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public void setClassificacaoContabil(String classificacaoContabil) {
		this.classificacaoContabil = classificacaoContabil;
	}
	
	
/*	
	@Override
	public String toString() {
		StringBuilder conta = new StringBuilder();
		conta.append(new SimpleDateFormat("dd/MM/yyyy").format(vencimento.getTime()));
		conta.append(" - ");
		conta.append(valor);
		conta.append(" - ");
		conta.append(favorecido);
		return conta.toString();
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((classificacaoContabil == null) ? 0 : classificacaoContabil
						.hashCode());
		result = prime * result
				+ ((debitoBanco == null) ? 0 : debitoBanco.hashCode());
		result = prime * result + (debitoConta ? 1231 : 1237);
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result
				+ ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + id;
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
		if (debitoBanco == null) {
			if (other.debitoBanco != null)
				return false;
		} else if (!debitoBanco.equals(other.debitoBanco))
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

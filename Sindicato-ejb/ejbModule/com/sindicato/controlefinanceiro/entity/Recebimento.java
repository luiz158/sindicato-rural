package com.sindicato.controlefinanceiro.entity;

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

@Entity
@SequenceGenerator(allocationSize=1, name="seqRecebimento", sequenceName="SEQ_RECEBIMENTO")
public class Recebimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqRecebimento")
	private int id;
	
	@ManyToOne(optional=false)
	private Debito debito;
	
	@ManyToOne(optional=false)
	private DestinoRecebimento destino;
	
	@ManyToOne(optional=false)
	private TipoRecebimento tipoRecebimento;
	
	private String numeroCheque;
	private String bancoCheque;
	private boolean chequeProprio;
	private boolean chequePre;
	@Temporal(TemporalType.DATE)
	private Calendar vencimentoCheque;
	private boolean chequeDevolvido;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataLiquidacao;
	
	@Column(precision=18, scale=2, nullable=false)
	private BigDecimal valor;

	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataRecebimento = Calendar.getInstance();

	
	public int getId() {
		return id;
	}
	public Debito getDebito() {
		return debito;
	}
	public DestinoRecebimento getDestino() {
		return destino;
	}
	public TipoRecebimento getTipoRecebimento() {
		return tipoRecebimento;
	}
	public String getBancoCheque() {
		return bancoCheque;
	}
	public boolean isChequeProprio() {
		return chequeProprio;
	}
	public boolean isChequePre() {
		return chequePre;
	}
	public boolean isChequeDevolvido() {
		return chequeDevolvido;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public Calendar getDataLiquidacao() {
		return dataLiquidacao;
	}
	public Calendar getVencimentoCheque() {
		return vencimentoCheque;
	}
	
	public String getNumeroCheque() {
		return numeroCheque;
	}
	public Calendar getDataRecebimento() {
		return dataRecebimento;
	}
	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
	public void setDataRecebimento(Calendar dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public void setVencimentoCheque(Calendar vencimentoCheque) {
		this.vencimentoCheque = vencimentoCheque;
	}
	public void setDataLiquidacao(Calendar dataLiquidacao) {
		this.dataLiquidacao = dataLiquidacao;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDebito(Debito debito) {
		this.debito = debito;
	}
	public void setDestino(DestinoRecebimento destino) {
		this.destino = destino;
	}
	public void setTipoRecebimento(TipoRecebimento tipoRecebimento) {
		this.tipoRecebimento = tipoRecebimento;
	}
	public void setBancoCheque(String bancoCheque) {
		this.bancoCheque = bancoCheque;
	}
	public void setChequeProprio(boolean chequeProprio) {
		this.chequeProprio = chequeProprio;
	}
	public void setChequePre(boolean chequePre) {
		this.chequePre = chequePre;
	}
	public void setChequeDevolvido(boolean chequeDevolvido) {
		this.chequeDevolvido = chequeDevolvido;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recebimento other = (Recebimento) obj;
		if (bancoCheque == null) {
			if (other.bancoCheque != null)
				return false;
		} else if (!bancoCheque.equals(other.bancoCheque))
			return false;
		if (chequeDevolvido != other.chequeDevolvido)
			return false;
		if (chequePre != other.chequePre)
			return false;
		if (chequeProprio != other.chequeProprio)
			return false;
		if (dataLiquidacao == null) {
			if (other.dataLiquidacao != null)
				return false;
		} else if (!dataLiquidacao.equals(other.dataLiquidacao))
			return false;
		if (dataRecebimento == null) {
			if (other.dataRecebimento != null)
				return false;
		} else if (!dataRecebimento.equals(other.dataRecebimento))
			return false;
		if (debito == null) {
			if (other.debito != null)
				return false;
		} else if (!debito.equals(other.debito))
			return false;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (id != other.id)
			return false;
		if (numeroCheque == null) {
			if (other.numeroCheque != null)
				return false;
		} else if (!numeroCheque.equals(other.numeroCheque))
			return false;
		if (tipoRecebimento == null) {
			if (other.tipoRecebimento != null)
				return false;
		} else if (!tipoRecebimento.equals(other.tipoRecebimento))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (vencimentoCheque == null) {
			if (other.vencimentoCheque != null)
				return false;
		} else if (!vencimentoCheque.equals(other.vencimentoCheque))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bancoCheque == null) ? 0 : bancoCheque.hashCode());
		result = prime * result + (chequeDevolvido ? 1231 : 1237);
		result = prime * result + (chequePre ? 1231 : 1237);
		result = prime * result + (chequeProprio ? 1231 : 1237);
		result = prime * result + ((dataLiquidacao == null) ? 0 : dataLiquidacao.hashCode());
		result = prime * result + ((dataRecebimento == null) ? 0 : dataRecebimento.hashCode());
		result = prime * result + ((debito == null) ? 0 : debito.hashCode());
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + id;
		result = prime * result + ((numeroCheque == null) ? 0 : numeroCheque.hashCode());
		result = prime * result + ((tipoRecebimento == null) ? 0 : tipoRecebimento.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((vencimentoCheque == null) ? 0 : vencimentoCheque.hashCode());
		return result;
	}
	
}

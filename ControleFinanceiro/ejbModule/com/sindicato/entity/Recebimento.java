package com.sindicato.entity;

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
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataLiquidacao;
	
	@Column(precision=18, scale=2, nullable=false)
	private BigDecimal valor;

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
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Recebimento)){
			return false;
		}
		
		Recebimento o = (Recebimento) obj;
		if(o.hashCode() == this.hashCode()
				&& o.valor.compareTo(this.valor) == 0
				&& o.dataLiquidacao.compareTo(this.dataLiquidacao) == 0
				&& o.debito.getId() == debito.getId()
				&& o.tipoRecebimento.equals(tipoRecebimento)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
	
}

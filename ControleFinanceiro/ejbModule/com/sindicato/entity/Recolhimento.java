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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(allocationSize=1, name="seqRecolhimento", sequenceName="SEQ_RECOLHIMENTO")
public class Recolhimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqRecolhimento")
	private int id;
	
	@OneToOne(targetEntity=DebitoServico.class, mappedBy="recolhimento")
	private DebitoServico servico;
	
	@ManyToOne(optional=false)
	private ModoPagamento modoPagamento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Calendar data = Calendar.getInstance();
	
	@Column(precision=18, scale=2, nullable=false)
	private BigDecimal valor;

	
	public int getId() {
		return id;
	}
	public DebitoServico getServico() {
		return servico;
	}
	public ModoPagamento getModoPagamento() {
		return modoPagamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setServico(DebitoServico servico) {
		this.servico = servico;
	}
	public void setModoPagamento(ModoPagamento modoPagamento) {
		this.modoPagamento = modoPagamento;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Recolhimento)){
			return false;
		}
		
		Recolhimento o = (Recolhimento) obj;
		if(o.hashCode() == this.hashCode()
				&& o.data.compareTo(data) == 0
				&& o.modoPagamento.equals(modoPagamento)
				&& o.valor.compareTo(valor) == 0
				&& o.servico.getId() == servico.getId()){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
}

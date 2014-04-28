package com.sindicato.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqDebitoServico", sequenceName="SEQ_DEBITOSERVICO")
public class DebitoServico {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDebitoServico")
	private int id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="servico_id")
	private Servico servico;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="debito_id")
	private Debito debito;
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="recolhimento_id")
	private Recolhimento recolhimento;
	
	@Column(precision=18, scale=2, nullable=false)
	private BigDecimal valor;
	
	
	public int getId() {
		return id;
	}
	public Servico getServico() {
		return servico;
	}
	public Debito getDebito() {
		return debito;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public void setDebito(Debito debito) {
		this.debito = debito;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Recolhimento getRecolhimento() {
		return recolhimento;
	}
	public void setRecolhimento(Recolhimento recolhimento) {
		this.recolhimento = recolhimento;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof DebitoServico)){
			return false;
		}
		
		DebitoServico o = (DebitoServico) obj;
		if(o.hashCode() == this.hashCode()){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}	
}

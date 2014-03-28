package com.sindicato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, sequenceName="SEQ_OCUPACAOSOLO", name="seqOcupacaoSolo")
public class OcupacaoSolo {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqOcupacaoSolo")
	private int id;
	
	@ManyToOne(optional=false)
	private EstabelecimentoRural estabelecimentoRural;
	
	@Column(length=500, nullable=false)
	private String descricao;
	
	
	public int getId() {
		return id;
	}
	public EstabelecimentoRural getEstabelecimentoRural() {
		return estabelecimentoRural;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEstabelecimentoRural(EstabelecimentoRural estabelecimentoRural) {
		this.estabelecimentoRural = estabelecimentoRural;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj instanceof OcupacaoSolo){
			return false;
		}
		
		OcupacaoSolo o = (OcupacaoSolo) obj;
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

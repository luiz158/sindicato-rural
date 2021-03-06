package com.sindicato.controlefinanceiro.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqModoPagamento", sequenceName="SEQ_MODOPAGAMENTO")
public class ModoPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqModoPagamento")
	private int id;
	
	@Column(length=500, nullable=false)
	private String descricao;
	
	
	public int getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof ModoPagamento)){
			return false;
		}
		
		ModoPagamento o = (ModoPagamento) obj;
		if(o.hashCode() == this.hashCode()
				&& o.descricao.equals(this.descricao)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
}

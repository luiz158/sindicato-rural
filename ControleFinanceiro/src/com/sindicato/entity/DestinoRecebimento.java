package com.sindicato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqDestinoRecebimento", sequenceName="SEQ_DESTINORECEBIMENTO")
public class DestinoRecebimento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDestinoRecebimento")
	private int id;
	
	@Column(length=300)
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
		if(obj instanceof DestinoRecebimento){
			return false;
		}
		
		DestinoRecebimento o = (DestinoRecebimento) obj;
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

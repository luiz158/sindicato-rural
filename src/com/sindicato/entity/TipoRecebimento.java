package com.sindicato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqTipoRecebimento", sequenceName="SEQ_TIPORECEBIMENTO")
public class TipoRecebimento {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqTipoRecebimento")
	private int id;
	
	@Column(nullable=false, length=500)
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
		if(!(obj instanceof TipoRecebimento)){
			return false;
		}
		
		TipoRecebimento o = (TipoRecebimento) obj;
		if(o.hashCode() == this.hashCode()
				&& o.descricao.equals(descricao)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
}

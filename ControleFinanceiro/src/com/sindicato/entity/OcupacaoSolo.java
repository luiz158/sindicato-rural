package com.sindicato.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, sequenceName="SEQ_OCUPACAOSOLO", name="seqOcupacaoSolo")
public class OcupacaoSolo {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqOcupacaoSolo")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="tipoocupacaosolo_id")
	private TipoOcupacaoSolo tipoOcupacaoSolo;
	
	private int areaOcupada;
	
	
	public int getId() {
		return id;
	}
	public int getAreaOcupada() {
		return areaOcupada;
	}
	public TipoOcupacaoSolo getTipoOcupacaoSolo() {
		return tipoOcupacaoSolo;
	}
	public void setTipoOcupacaoSolo(TipoOcupacaoSolo tipoOcupacaoSolo) {
		this.tipoOcupacaoSolo = tipoOcupacaoSolo;
	}
	public void setAreaOcupada(int areaOcupada) {
		this.areaOcupada = areaOcupada;
	}
	public void setId(int id) {
		this.id = id;
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

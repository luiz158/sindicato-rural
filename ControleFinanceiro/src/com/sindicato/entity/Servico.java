package com.sindicato.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqServico", sequenceName="SEQ_SERVICO")
public class Servico {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqServico")
	private int id;
	
	@Column(nullable=false, length=500)
	private String descricao;
	private boolean retencao;
	
	private boolean mensalidade;
	private int quantosMesesVale;
	
	public int getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public boolean isRetencao() {
		return retencao;
	}
	public boolean isMensalidade() {
		return mensalidade;
	}
	public void setMensalidade(boolean mensalidade) {
		this.mensalidade = mensalidade;
	}
	public int getQuantosMesesVale() {
		return quantosMesesVale;
	}
	public void setQuantosMesesVale(int quantosMesesVale) {
		this.quantosMesesVale = quantosMesesVale;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setRetencao(boolean retencao) {
		this.retencao = retencao;
	}

	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Servico)){
			return false;
		}
		
		Servico o = (Servico) obj;
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

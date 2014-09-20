package com.sindicato.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(allocationSize=1, name="seqInformacaoClienteSocio", sequenceName="SEQ_INFORMACAOCLIENTESOCIO")
public class InformacaoSocio {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqInformacaoClienteSocio")
	private int id;
	
	@ManyToOne(optional=false)
	private Cliente cliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataEvento;
	
	private boolean socio;
	
	
	public int getId() {
		return id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public boolean isSocio() {
		return socio;
	}
	public Calendar getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Calendar dataEvento) {
		this.dataEvento = dataEvento;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void setSocio(boolean socio) {
		this.socio = socio;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof InformacaoSocio)){
			return false;
		}
		
		InformacaoSocio o = (InformacaoSocio) obj;
		if(o.hashCode() == this.hashCode()
				&& o.socio == this.socio
				&& o.cliente.equals(this.cliente)
				&& o.dataEvento.compareTo(this.dataEvento) == 0){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
}

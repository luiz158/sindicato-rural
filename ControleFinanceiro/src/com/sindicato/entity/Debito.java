package com.sindicato.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sindicato.entity.Enum.StatusDebitoEnum;

@Entity
@SequenceGenerator(allocationSize=1, name="seqDebito", sequenceName="SEQ_DEBITO")
public class Debito {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDebito")
	private int id;
	
	@OneToMany(targetEntity=DebitoServico.class, mappedBy="debito", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private List<DebitoServico> servicos;
	
	@OneToMany(targetEntity=Recebimento.class, mappedBy="debito", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	//@JoinColumn(name="recebimento_id", insertable=true, updatable=true)
	private List<Recebimento> recebimentos;

	@ManyToOne
	private Cliente cliente;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Calendar dataBase;
	
	@Column(nullable=false)
	private StatusDebitoEnum status;
	
	
	
	public int getId() {
		return id;
	}
	public StatusDebitoEnum getStatus() {
		return status;
	}
	public List<DebitoServico> getServicos() {
		return servicos;
	}
	public List<Recebimento> getRecebimentos() {
		return recebimentos;
	}
	public Calendar getDataBase() {
		return dataBase;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void setDataBase(Calendar dataBase) {
		this.dataBase = dataBase;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setStatus(StatusDebitoEnum status) {
		this.status = status;
	}
	public void setServicos(List<DebitoServico> servicos) {
		this.servicos = servicos;
	}
	public void setRecebimentos(List<Recebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj instanceof Debito){
			return false;
		}
		
		Debito o = (Debito) obj;
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

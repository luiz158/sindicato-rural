package com.sindicato.painelcontrole.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, sequenceName="SEQ_ACAO", name="seqAcao")
public class Acao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="seqAcao", strategy=GenerationType.SEQUENCE)
	private int id;

	@ManyToOne
	private Menu menu;

	@ManyToMany(mappedBy="acoes", targetEntity=Perfil.class, fetch=FetchType.LAZY)
	private List<Perfil> perfil;
	
	@Column(unique=true, nullable=false, length=255)
	private String identificacao;
	
	private String descricao;
	
	public List<Perfil> getPerfil() {
		return perfil;
	}
	public void setPerfil(List<Perfil> perfil) {
		this.perfil = perfil;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	public Menu getMenu() {
		return menu;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((identificacao == null) ? 0 : identificacao.hashCode());
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acao other = (Acao) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id != other.id)
			return false;
		if (identificacao == null) {
			if (other.identificacao != null)
				return false;
		} else if (!identificacao.equals(other.identificacao))
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "Ação: " + this.descricao;
	}
	
}

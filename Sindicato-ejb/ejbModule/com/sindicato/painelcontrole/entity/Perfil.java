package com.sindicato.painelcontrole.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqPerfil", sequenceName="SEQ_PERFIL")
public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqPerfil")
	private int id;
	
	@ManyToMany(mappedBy="perfis", targetEntity=Usuario.class, fetch=FetchType.LAZY)
	private List<Usuario> usuario;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Menu> menus;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Acao> acoes;
	
	private String descricao;


	public List<Usuario> getUsuario() {
		return usuario;
	}
	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Acao> getAcoes() {
		return acoes;
	}
	public void setAcoes(List<Acao> acoes) {
		this.acoes = acoes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Menu> getMenus() {
		if(menus == null){
			menus = new ArrayList<Menu>();
		}
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		if (acoes == null) {
			if (other.acoes != null)
				return false;
		} else if (!acoes.equals(other.acoes))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id != other.id)
			return false;
		if (menus == null) {
			if (other.menus != null)
				return false;
		} else if (!menus.equals(other.menus))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acoes == null) ? 0 : acoes.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + id;
		result = prime * result + ((menus == null) ? 0 : menus.hashCode());
		return result;
	}
	
}

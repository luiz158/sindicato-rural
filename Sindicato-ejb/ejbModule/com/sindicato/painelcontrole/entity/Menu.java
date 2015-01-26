package com.sindicato.painelcontrole.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqMenu", sequenceName="SEQ_MENU")
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqMenu")
	private int id;

	@ManyToOne
	@JoinColumn(name="menupai_id")
	private Menu menuPai;
	
	@ManyToMany(mappedBy="menus", targetEntity=Perfil.class, fetch=FetchType.LAZY)
	private List<Perfil> perfil;
	
	@ManyToOne
	private Modulo modulo = new Modulo();

	private String descricao;
	private String url;
	private int ordem;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Perfil> getPerfil() {
		return perfil;
	}
	public void setPerfil(List<Perfil> perfil) {
		this.perfil = perfil;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Menu getMenuPai() {
		return menuPai;
	}
	public void setMenuPai(Menu menuPai) {
		this.menuPai = menuPai;
	}
	public String getDescricao() {
		return descricao;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Modulo getModulo() {
		return modulo;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	
	
	@Override
	public String toString(){
		return descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id != other.id)
			return false;
		if (menuPai == null) {
			if (other.menuPai != null)
				return false;
		} else if (!menuPai.equals(other.menuPai))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (ordem != other.ordem)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + id;
		result = prime * result + ((menuPai == null) ? 0 : menuPai.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ordem;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	
}

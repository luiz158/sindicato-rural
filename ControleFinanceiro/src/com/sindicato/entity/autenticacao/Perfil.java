package com.sindicato.entity.autenticacao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqPerfil", sequenceName="SEQ_PERFIL")
public class Perfil {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqPerfil")
	private int id;
	
	@ManyToMany
	private List<Menu> menus;
	
	private String descricao;


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
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Perfil)){
			return false;
		}
		
		Perfil o = (Perfil) obj;
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

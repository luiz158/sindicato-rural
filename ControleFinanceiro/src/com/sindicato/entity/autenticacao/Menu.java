package com.sindicato.entity.autenticacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqMenu", sequenceName="SEQ_MENU")
public class Menu {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqMenu")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="menupai_id")
	private Menu menuPai;
	
	private String descricao;
	private String url;

	
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
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Menu)){
			return false;
		}
		
		Menu o = (Menu) obj;
		if(o.hashCode() == this.hashCode()
				&& o.descricao.equals(descricao)
				&& o.menuPai == menuPai
				&& o.url.equals(url)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
	
}

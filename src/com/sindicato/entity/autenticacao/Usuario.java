package com.sindicato.entity.autenticacao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.sindicato.entity.Empresa;

@Entity
@SequenceGenerator(allocationSize=1, name="seqUsuario", sequenceName="SEQ_USUARIO")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqUsuario")
	private int id;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Perfil> perfis;
	
	@ManyToOne
	private Empresa empresa;
	
	private String nome;
	private String email;
	private String senha;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Perfil> getPerfis() {
		if(perfis == null){
			perfis = new ArrayList<Perfil>();
		}
		return perfis;
	}
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Usuario)){
			return false;
		}
		
		Usuario o = (Usuario) obj;
		if(o.hashCode() == this.hashCode()
				&& o.email.equals(email)
				&& o.nome.equals(nome)
				&& o.senha.equals(senha)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
	
}

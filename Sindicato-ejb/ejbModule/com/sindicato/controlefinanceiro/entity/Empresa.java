package com.sindicato.controlefinanceiro.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize=1, name="seqEmpresa", sequenceName="SEQ_EMPRESA")
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqEmpresa")
	private int id;
	
	@OneToMany(targetEntity=Cliente.class, mappedBy="empresa")
	private List<Cliente> clientes;
	
	@Column(nullable=false, length=500)
	private String razaoSocial;
	
	@Column(length=300)
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	
	@Column(length=9)
	private String cep;
	private String cnpj;
	private String cartaSindical;
	private String telefone;
	private String telefone2;
	private String fax;
	private String email;
	
	
	
	public int getId() {
		return id;
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public String getEndereco() {
		return endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public String getEstado() {
		return estado;
	}
	public String getCep() {
		return cep;
	}
	public String getCnpj() {
		return cnpj;
	}
	public String getCartaSindical() {
		return cartaSindical;
	}
	public String getTelefone() {
		return telefone;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public String getFax() {
		return fax;
	}
	public String getEmail() {
		return email;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public void setCartaSindical(String cartaSindical) {
		this.cartaSindical = cartaSindical;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Empresa)){
			return false;
		}
		
		Empresa o = (Empresa) obj;
		if(o.hashCode() == this.hashCode()
				&& o.cnpj.equals(this.cnpj)){
			return true;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
}

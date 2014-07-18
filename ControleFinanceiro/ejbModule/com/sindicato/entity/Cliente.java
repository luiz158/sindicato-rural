package com.sindicato.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(allocationSize = 1, name = "seqCliente", sequenceName = "SEQ_CLIENTE")
public class Cliente implements Serializable {

	public Cliente() {
		estabelecimentoRural = new EstabelecimentoRural();
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCliente")
	private int id;

	@ManyToOne(optional = false)
	private Empresa empresa;

	@OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="estabelecimentorural_id")
	private EstabelecimentoRural estabelecimentoRural;

	@Column(nullable = false)
	private boolean socio = false;
	
	@OneToMany(targetEntity = Debito.class, mappedBy = "cliente")
	private List<Debito> debitos;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataCadastro = Calendar.getInstance();

	//@Past
	//@NotNull
	@Temporal(TemporalType.DATE)
	private Calendar produtorRuralDesde;

	//@Past
	//@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento;

	@Column(length = 2000)
	private String observacao;

	private String nome;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;
	private String telefone;
	private String email;
	private String tituloEleitor;
	
	//@NotNull
	private String cpf;
	private String rg;
	private String orgaoExp;
	
	//@Max(value=2)
	private String ufExp;

	public int getId() {
		return id;
	}

	public EstabelecimentoRural getEstabelecimentoRural() {
		return estabelecimentoRural;
	}

	public List<Debito> getDebitos() {
		return debitos;
	}

	public String getObservacao() {
		return observacao;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public String getEstado() {
		return estado;
	}

	public String getCep() {
		return cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getTituloEleitor() {
		return tituloEleitor;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRg() {
		return rg;
	}

	public String getOrgaoExp() {
		return orgaoExp;
	}

	public String getUfExp() {
		return ufExp;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public Calendar getProdutorRuralDesde() {
		return produtorRuralDesde;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public String getNome() {
		return nome;
	}
	public boolean isSocio() {
		return socio;
	}
	
	public String getBairro() {
		return bairro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setSocio(boolean socio) {
		this.socio = socio;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setProdutorRuralDesde(Calendar produtorRuralDesde) {
		this.produtorRuralDesde = produtorRuralDesde;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEstabelecimentoRural(
			EstabelecimentoRural estabelecimentoRural) {
		this.estabelecimentoRural = estabelecimentoRural;
	}

	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setTituloEleitor(String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public void setOrgaoExp(String orgaoExp) {
		this.orgaoExp = orgaoExp;
	}

	public void setUfExp(String ufExp) {
		this.ufExp = ufExp;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Cliente)) {
			return false;
		}

		Cliente o = (Cliente) obj;
		if (o.hashCode() == this.hashCode() 
				&& o.getNome().equals(this.nome)
				&& o.getCpf().equals(this.cpf)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

}

package com.sindicato.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(allocationSize = 1, name = "seqEstabelecimentoRural", sequenceName = "SEQ_ESTABELECIMENTORURAL")
public class EstabelecimentoRural {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqEstabelecimentoRural")
	private int id;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
	@JoinColumn(name="estabelecimentorural_id")
	private List<OcupacaoSolo> ocupacoesSolo;

	private String nirf;
	private String incra;
	private String iptu;

	@Column(precision = 18, scale = 2)
	private BigDecimal areaImovel;

	private String incricaoEstadual;
	private String cei;

	@Temporal(TemporalType.DATE)
	private Calendar validadeInscricaoEstadual;

	// @NotNull
	private String cnpj;

	private String titulo;
	private String logradouro;
	private String cidade;
	private String municipio;
	private String cep;
	private String estado;

	private String formaDominio;

	// @Past
	@Temporal(TemporalType.DATE)
	private Calendar dataInicioAtividade;

	private String vinculoProdutor;
	private String proprietarioImovel;

	public int getId() {
		return id;
	}

	public List<OcupacaoSolo> getOcupacoesSolo() {
		if(ocupacoesSolo == null){
			ocupacoesSolo = new ArrayList<OcupacaoSolo>();
		}
		return ocupacoesSolo;
	}

	public String getNirf() {
		return nirf;
	}

	public String getIncra() {
		return incra;
	}

	public String getIptu() {
		return iptu;
	}

	public BigDecimal getAreaImovel() {
		return areaImovel;
	}

	public String getIncricaoEstadual() {
		return incricaoEstadual;
	}

	public String getCei() {
		return cei;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getMunicipio() {
		return municipio;
	}

	public String getCep() {
		return cep;
	}

	public String getEstado() {
		return estado;
	}

	public String getFormaDominio() {
		return formaDominio;
	}

	public String getVinculoProdutor() {
		return vinculoProdutor;
	}

	public String getProprietarioImovel() {
		return proprietarioImovel;
	}

	public Calendar getValidadeInscricaoEstadual() {
		return validadeInscricaoEstadual;
	}

	public Calendar getDataInicioAtividade() {
		return dataInicioAtividade;
	}

	public void setDataInicioAtividade(Calendar dataInicioAtividade) {
		this.dataInicioAtividade = dataInicioAtividade;
	}

	public void setValidadeInscricaoEstadual(Calendar validadeInscricaoEstadual) {
		this.validadeInscricaoEstadual = validadeInscricaoEstadual;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOcupacoesSolo(List<OcupacaoSolo> ocupacoesSolo) {
		this.ocupacoesSolo = ocupacoesSolo;
	}

	public void setNirf(String nirf) {
		this.nirf = nirf;
	}

	public void setIncra(String incra) {
		this.incra = incra;
	}

	public void setIptu(String iptu) {
		this.iptu = iptu;
	}

	public void setAreaImovel(BigDecimal areaImovel) {
		this.areaImovel = areaImovel;
	}

	public void setIncricaoEstadual(String incricaoEstadual) {
		this.incricaoEstadual = incricaoEstadual;
	}

	public void setCei(String cei) {
		this.cei = cei;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setFormaDominio(String formaDominio) {
		this.formaDominio = formaDominio;
	}

	public void setVinculoProdutor(String vinculoProdutor) {
		this.vinculoProdutor = vinculoProdutor;
	}

	public void setProprietarioImovel(String proprietarioImovel) {
		this.proprietarioImovel = proprietarioImovel;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof EstabelecimentoRural) {
			return false;
		}

		EstabelecimentoRural o = (EstabelecimentoRural) obj;
		if (o.hashCode() == this.hashCode()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}
}

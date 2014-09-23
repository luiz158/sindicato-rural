package com.sindicato.controlefinanceiro.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;

/**
 * @author Alysson
 *
 */
@Entity
@SequenceGenerator(allocationSize = 1, name = "seqDebito", sequenceName = "SEQ_DEBITO")
public class Debito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDebito")
	private int id;

	@Fetch(FetchMode.SELECT)
	@OneToMany(targetEntity = DebitoServico.class, mappedBy = "debito", cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<DebitoServico> debitoServicos;

	@Transient
	private BigDecimal totalDebitos;
	@Transient
	private BigDecimal totalDebitosComRetencao;

	@Fetch(FetchMode.SELECT)
	@OneToMany(targetEntity = Recebimento.class, mappedBy = "debito", cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Recebimento> recebimentos;

	@ManyToOne
	private Cliente cliente;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Calendar dataBase = Calendar.getInstance();

	@Temporal(TemporalType.DATE)
	private Calendar dataEmissaoNotaCobranca;

	@Column(nullable = false)
	private StatusDebitoEnum status = StatusDebitoEnum.DEBITOCRIADO;

	private int numeroNota = 0;
	
	public BigDecimal getTotalDebitos() {
		if (debitoServicos == null || debitoServicos.size() == 0) {
			return BigDecimal.ZERO;
		}
		totalDebitos = BigDecimal.ZERO;
		for (DebitoServico debitoServico : debitoServicos) {
			totalDebitos = totalDebitos.add(debitoServico.getValor());
		}
		return totalDebitos;
	}

	public BigDecimal getTotalDebitosComRetencao() {
		if (debitoServicos == null || debitoServicos.size() == 0) {
			return BigDecimal.ZERO;
		}
		totalDebitosComRetencao = BigDecimal.ZERO;
		for (DebitoServico debitoServico : debitoServicos) {
			if (debitoServico.getServico().isRetencao()) {
				totalDebitosComRetencao = totalDebitosComRetencao
						.add(debitoServico.getValor());
			}
		}
		return totalDebitosComRetencao;
	}

	public int getId() {
		return id;
	}

	public StatusDebitoEnum getStatus() {
		return status;
	}

	public List<DebitoServico> getDebitoServicos() {
		if (debitoServicos == null) {
			debitoServicos = new ArrayList<DebitoServico>();
		}
		return debitoServicos;
	}

	public List<Recebimento> getRecebimentos() {
		if (recebimentos == null) {
			recebimentos = new ArrayList<Recebimento>();
		}
		return recebimentos;
	}

	public Calendar getDataBase() {
		return dataBase;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Calendar getDataEmissaoNotaCobranca() {
		return dataEmissaoNotaCobranca;
	}

	public int getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}

	public void setDataEmissaoNotaCobranca(Calendar dataEmissaoNotaCobranca) {
		this.dataEmissaoNotaCobranca = dataEmissaoNotaCobranca;
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

	public void setDebitoServicos(List<DebitoServico> debitoServicos) {
		this.debitoServicos = debitoServicos;
	}

	public void setRecebimentos(List<Recebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Debito)) {
			return false;
		}

		Debito o = (Debito) obj;
		if (o.hashCode() == this.hashCode()
				&& o.dataBase.compareTo(this.dataBase) == 0
				&& o.status.equals(this.status)
				&& o.cliente.equals(this.cliente)
				&& o.getTotalDebitos().compareTo(this.getTotalDebitos()) == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}
}

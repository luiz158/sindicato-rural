package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetalhesServicosRecolhimentos implements Serializable {
	public DetalhesServicosRecolhimentos() {
		detalhesCliente = new ArrayList<DetalhesClienteRecolhimentos>();
	}

	private static final long serialVersionUID = 1L;
	private String descricao;
	private List<DetalhesClienteRecolhimentos> detalhesCliente;
	private BigDecimal totalRecolhimentos;
	private BigDecimal valorContabil;
	private BigDecimal totalVariacao;

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<DetalhesClienteRecolhimentos> getDetalhesCliente() {
		return detalhesCliente;
	}
	public void setDetalhesCliente(List<DetalhesClienteRecolhimentos> detalhesCliente) {
		this.detalhesCliente = detalhesCliente;
	}
	public BigDecimal getTotalRecolhimentos() {
		totalRecolhimentos = BigDecimal.ZERO;
		if (detalhesCliente == null || detalhesCliente.size() == 0) {
			return totalRecolhimentos;
		}
		detalhesCliente.forEach((d) -> totalRecolhimentos = totalRecolhimentos.add(d.getValorRecolhido()));
		return totalRecolhimentos;
	}
	public void setTotalRecolhimentos(BigDecimal totalRecolhimentos) {
		this.totalRecolhimentos = totalRecolhimentos;
	}
	public BigDecimal getValorContabil() {
		valorContabil = BigDecimal.ZERO;
		if (detalhesCliente == null || detalhesCliente.size() == 0) {
			return valorContabil;
		}
		detalhesCliente.forEach((d) -> valorContabil = valorContabil.add(d.getValorDoServico()));
		return valorContabil;
	}
	public void setValorContabil(BigDecimal valorContabil) {
		this.valorContabil = valorContabil;
	}
	public BigDecimal getTotalVariacao() {
		totalVariacao = BigDecimal.ZERO;
		if (detalhesCliente == null || detalhesCliente.size() == 0) {
			return totalVariacao;
		}
		detalhesCliente.forEach((d) -> totalVariacao = totalVariacao.add(d.getVariacao()));
		return totalVariacao;
	}
	public void setTotalVariacao(BigDecimal totalVariacao) {
		this.totalVariacao = totalVariacao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioResumoRecolhimentos implements Serializable {
	
	public RelatorioResumoRecolhimentos(){
		detalhes = new ArrayList<DetalhesServicosRecolhimentos>();
	}
	
	private static final long serialVersionUID = 1L;
	private List<DetalhesServicosRecolhimentos> detalhes;
	private BigDecimal totalRecolhimentos;
	private BigDecimal valorContabil;
	private BigDecimal totalVariacao;

	public List<DetalhesServicosRecolhimentos> getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(List<DetalhesServicosRecolhimentos> detalhes) {
		this.detalhes = detalhes;
	}
	public BigDecimal getTotalRecolhimentos() {
		totalRecolhimentos = BigDecimal.ZERO;
		if (detalhes == null || detalhes.size() == 0) {
			return totalRecolhimentos;
		}
		detalhes.forEach((d) -> totalRecolhimentos = totalRecolhimentos.add(d.getTotalRecolhimentos()));
		return totalRecolhimentos;
	}
	public void setTotalRecolhimentos(BigDecimal totalRecolhimentos) {
		this.totalRecolhimentos = totalRecolhimentos;
	}
	public BigDecimal getValorContabil() {
		valorContabil = BigDecimal.ZERO;
		if (detalhes == null || detalhes.size() == 0) {
			return valorContabil;
		}
		detalhes.forEach((d) -> valorContabil = valorContabil.add(d.getValorContabil()));
		return valorContabil;
	}
	public void setValorContabil(BigDecimal valorContabil) {
		this.valorContabil = valorContabil;
	}
	public BigDecimal getTotalVariacao() {
		totalVariacao = BigDecimal.ZERO;
		if (detalhes == null || detalhes.size() == 0) {
			return totalVariacao;
		}
		detalhes.forEach((d) -> totalVariacao = totalVariacao.add(d.getTotalVariacao()));
		return totalVariacao;
	}
	public void setTotalVariacao(BigDecimal totalVariacao) {
		this.totalVariacao = totalVariacao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

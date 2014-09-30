package com.sindicato.controlefinanceiro.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioResumoServico implements Serializable {

	public RelatorioResumoServico() {
		retencoesSocios = new ArrayList<DetalhesServico>();
		retencoesNaoSocios = new ArrayList<DetalhesServico>();
		receitasSocios = new ArrayList<DetalhesServico>();
		receitasNaoSocios = new ArrayList<DetalhesServico>();
	}

	private static final long serialVersionUID = 1L;

	private List<DetalhesServico> retencoesSocios;
	private List<DetalhesServico> retencoesNaoSocios;

	private List<DetalhesServico> receitasSocios;
	private List<DetalhesServico> receitasNaoSocios;

	private BigDecimal totalRetencoesSocios;
	private BigDecimal totalRetencoesNaoSocios;

	private BigDecimal totalReceitasSocios;
	private BigDecimal totalReceitasNaoSocios;

	private BigDecimal totalReceitas;
	private BigDecimal totalRetencoes;
	private BigDecimal totalGeral;

	
	public BigDecimal getTotalRetencoesSocios() {
		totalRetencoesSocios = BigDecimal.ZERO;
		if (retencoesSocios == null || retencoesSocios.size() == 0) {
			return totalRetencoesSocios;
		}

		for (DetalhesServico item : retencoesSocios) {
			totalRetencoesSocios = totalRetencoesSocios.add(item.getValor());
		}

		return totalRetencoesSocios;
	}

	public BigDecimal getTotalRetencoesNaoSocios() {
		totalRetencoesNaoSocios = BigDecimal.ZERO;
		if (retencoesNaoSocios == null || retencoesNaoSocios.size() == 0) {
			return totalRetencoesNaoSocios;
		}

		for (DetalhesServico item : retencoesNaoSocios) {
			totalRetencoesNaoSocios = totalRetencoesNaoSocios.add(item
					.getValor());
		}

		return totalRetencoesNaoSocios;
	}

	public BigDecimal getTotalReceitasSocios() {
		totalReceitasSocios = BigDecimal.ZERO;
		if (receitasSocios == null || receitasSocios.size() == 0) {
			return totalReceitasSocios;
		}

		for (DetalhesServico item : receitasSocios) {
			totalReceitasSocios = totalReceitasSocios.add(item.getValor());
		}

		return totalReceitasSocios;
	}

	public BigDecimal getTotalReceitasNaoSocios() {
		totalReceitasNaoSocios = BigDecimal.ZERO;
		if (receitasNaoSocios == null || receitasNaoSocios.size() == 0) {
			return totalReceitasNaoSocios;
		}

		for (DetalhesServico item : receitasNaoSocios) {
			totalReceitasNaoSocios = totalReceitasNaoSocios
					.add(item.getValor());
		}

		return totalReceitasNaoSocios;
	}

	public BigDecimal getTotalGeral() {
		totalGeral = getTotalRetencoesSocios().add(getTotalRetencoesNaoSocios())
				.add(getTotalReceitasNaoSocios()).add(getTotalReceitasSocios());
		return totalGeral;
	}

	public List<DetalhesServico> getRetencoesSocios() {
		return retencoesSocios;
	}

	public List<DetalhesServico> getRetencoesNaoSocios() {
		return retencoesNaoSocios;
	}

	public List<DetalhesServico> getReceitasSocios() {
		return receitasSocios;
	}

	public List<DetalhesServico> getReceitasNaoSocios() {
		return receitasNaoSocios;
	}

	public BigDecimal getTotalReceitas() {
		totalReceitas = this.getTotalReceitasSocios().add(this.getTotalReceitasNaoSocios());
		return totalReceitas;
	}

	public BigDecimal getTotalRetencoes() {
		totalRetencoes = this.getTotalRetencoesNaoSocios().add(this.getTotalRetencoesSocios());
		return totalRetencoes;
	}

	public void setTotalReceitas(BigDecimal totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public void setTotalRetencoes(BigDecimal totalRetencoes) {
		this.totalRetencoes = totalRetencoes;
	}

	public void setRetencoesSocios(List<DetalhesServico> retencoesSocios) {
		this.retencoesSocios = retencoesSocios;
	}

	public void setRetencoesNaoSocios(List<DetalhesServico> retencoesNaoSocios) {
		this.retencoesNaoSocios = retencoesNaoSocios;
	}

	public void setReceitasSocios(List<DetalhesServico> receitasSocios) {
		this.receitasSocios = receitasSocios;
	}

	public void setReceitasNaoSocios(List<DetalhesServico> receitasNaoSocios) {
		this.receitasNaoSocios = receitasNaoSocios;
	}

	public void setTotalRetencoesSocios(BigDecimal totalRetencoesSocios) {
		this.totalRetencoesSocios = totalRetencoesSocios;
	}

	public void setTotalRetencoesNaoSocios(BigDecimal totalRetencoesNaoSocios) {
		this.totalRetencoesNaoSocios = totalRetencoesNaoSocios;
	}

	public void setTotalReceitasSocios(BigDecimal totalReceitasSocios) {
		this.totalReceitasSocios = totalReceitasSocios;
	}

	public void setTotalReceitasNaoSocios(BigDecimal totalReceitasNaoSocios) {
		this.totalReceitasNaoSocios = totalReceitasNaoSocios;
	}

	public void setTotalGeral(BigDecimal totalGeral) {
		this.totalGeral = totalGeral;
	}

}

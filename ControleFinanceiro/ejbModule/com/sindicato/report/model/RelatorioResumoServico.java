package com.sindicato.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RelatorioResumoServico implements Serializable {

	public RelatorioResumoServico() {
		retencoesSocios = new HashMap<String, BigDecimal>();
		retencoesNaoSocios = new HashMap<String, BigDecimal>();
		receitasSocios = new HashMap<String, BigDecimal>();
		receitasNaoSocios = new HashMap<String, BigDecimal>();
	}

	private static final long serialVersionUID = 1L;

	private Map<String, BigDecimal> retencoesSocios;
	private Map<String, BigDecimal> retencoesNaoSocios;

	private Map<String, BigDecimal> receitasSocios;
	private Map<String, BigDecimal> receitasNaoSocios;

	private BigDecimal totalRetencoesSocios;
	private BigDecimal totalRetencoesNaoSocios;

	private BigDecimal totalReceitasSocios;
	private BigDecimal totalReceitasNaoSocios;

	private BigDecimal totalGeral;

	public Map<String, BigDecimal> getRetencoesSocios() {
		return retencoesSocios;
	}

	public Map<String, BigDecimal> getRetencoesNaoSocios() {
		return retencoesNaoSocios;
	}

	public Map<String, BigDecimal> getReceitasSocios() {
		return receitasSocios;
	}

	public Map<String, BigDecimal> getReceitasNaoSocios() {
		return receitasNaoSocios;
	}

	public BigDecimal getTotalRetencoesSocios() {
		totalRetencoesSocios = BigDecimal.ZERO;
		if (retencoesSocios == null || retencoesSocios.size() == 0) {
			return totalRetencoesSocios;
		}

		for (Entry<String, BigDecimal> item : retencoesSocios.entrySet()) {
			totalRetencoesSocios = totalRetencoesSocios.add(item.getValue());
		}

		return totalRetencoesSocios;
	}

	public BigDecimal getTotalRetencoesNaoSocios() {
		totalRetencoesNaoSocios = BigDecimal.ZERO;
		if (retencoesNaoSocios == null || retencoesNaoSocios.size() == 0) {
			return totalRetencoesNaoSocios;
		}

		for (Entry<String, BigDecimal> item : retencoesNaoSocios.entrySet()) {
			totalRetencoesNaoSocios = totalRetencoesNaoSocios.add(item
					.getValue());
		}

		return totalRetencoesNaoSocios;
	}

	public BigDecimal getTotalReceitasSocios() {
		totalReceitasSocios = BigDecimal.ZERO;
		if (receitasSocios == null || receitasSocios.size() == 0) {
			return totalReceitasSocios;
		}

		for (Entry<String, BigDecimal> item : receitasSocios.entrySet()) {
			totalReceitasSocios = totalReceitasSocios.add(item.getValue());
		}

		return totalReceitasSocios;
	}

	public BigDecimal getTotalReceitasNaoSocios() {
		totalReceitasNaoSocios = BigDecimal.ZERO;
		if (receitasNaoSocios == null || receitasNaoSocios.size() == 0) {
			return totalReceitasNaoSocios;
		}

		for (Entry<String, BigDecimal> item : receitasNaoSocios.entrySet()) {
			totalReceitasNaoSocios = totalReceitasNaoSocios
					.add(item.getValue());
		}

		return totalReceitasNaoSocios;
	}

	public BigDecimal getTotalGeral() {
		totalGeral = getTotalRetencoesSocios().add(getTotalRetencoesNaoSocios())
				.add(getTotalReceitasNaoSocios()).add(getTotalReceitasSocios());
		return totalGeral;
	}

	public void setRetencoesSocios(Map<String, BigDecimal> retencoesSocios) {
		this.retencoesSocios = retencoesSocios;
	}

	public void setRetencoesNaoSocios(Map<String, BigDecimal> retencoesNaoSocios) {
		this.retencoesNaoSocios = retencoesNaoSocios;
	}

	public void setReceitasSocios(Map<String, BigDecimal> receitasSocios) {
		this.receitasSocios = receitasSocios;
	}

	public void setReceitasNaoSocios(Map<String, BigDecimal> receitasNaoSocios) {
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

package com.sindicato.contasapagar.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sindicato.contasapagar.entity.Conta;

public class RelatorioContas implements Serializable {

	public RelatorioContas(){
		filtro = new FiltroRelatorioContas();
		resultado = new ArrayList<Conta>();
	}
	
	private static final long serialVersionUID = 1L;
	public FiltroRelatorioContas filtro;
	public String titulo;
	public List<Conta> resultado;
	public BigDecimal valorTotal;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public FiltroRelatorioContas getFiltro() {
		return filtro;
	}
	public String getTitulo() {
		return titulo;
	}
	public List<Conta> getResultado() {
		return resultado;
	}
	public BigDecimal getValorTotal() {
		valorTotal = BigDecimal.ZERO;
		if(resultado == null || resultado.size() == 0){
			return valorTotal;
		}
		
		for (Conta conta : resultado) {
			valorTotal = valorTotal.add(conta.getValor());
		}
		
		return valorTotal;
	}
	public void setFiltro(FiltroRelatorioContas filtro) {
		this.filtro = filtro;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setResultado(List<Conta> resultado) {
		this.resultado = resultado;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filtro == null) ? 0 : filtro.hashCode());
		result = prime * result
				+ ((resultado == null) ? 0 : resultado.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result
				+ ((valorTotal == null) ? 0 : valorTotal.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelatorioContas other = (RelatorioContas) obj;
		if (filtro == null) {
			if (other.filtro != null)
				return false;
		} else if (!filtro.equals(other.filtro))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}
	
	
	
	
}

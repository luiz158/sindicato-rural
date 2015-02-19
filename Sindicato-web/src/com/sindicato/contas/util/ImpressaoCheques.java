package com.sindicato.contas.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sindicato.util.Extenso;


public class ImpressaoCheques {

	public ImpressaoCheques(BigDecimal valor, String favorecido, String verso){
		this.valor = valor;
		this.favorecido = favorecido;
		this.verso = verso;
	}
	public ImpressaoCheques(){}
	
	private BigDecimal valor;
	private String favorecido;
	private String verso;
	private Calendar emissao;
	private Long numeroCheque;
	private final int quebraLinha = 50;
	
	private String pad(String str, int size, String padChar)
	{
	  StringBuffer padded = new StringBuffer(str);
	  while (padded.length() < size)
	  {
	    padded.append(padChar);
	  }
	  return padded.toString();
	}
	
	public String getMes(){
		if(emissao == null){
			return null;
		}
		DateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
		return simpleDateFormat.format(emissao.getTime()).toUpperCase();
	}
	public int getAno(){
		if(emissao == null){
			return 0;
		}
		return emissao.get(Calendar.YEAR);
	}
	public int getDia(){
		if(emissao == null){
			return 0;
		}
		return emissao.get(Calendar.DAY_OF_MONTH);
	}
	public String getValorFormatado(){
		return "( " + NumberFormat.getCurrencyInstance().format(this.getValor()).replace("R$ ", "") + " )"; 
	}
	public String getValorPorExtenso(){
		Extenso extenso = new Extenso();
		extenso.setNumber(getValor());
		String valor = "( " + extenso.toString().toUpperCase() + " )";
		
		StringBuilder valorRetorno = new StringBuilder();
		String[] palavras = valor.split(" ");
		int qtdLetras = 0;
		boolean continua = false;
		for (String palavra : palavras) {
			qtdLetras += palavra.length() + 1;
			
			if(qtdLetras <= quebraLinha){
				valorRetorno.append(palavra);
				valorRetorno.append(" ");
			} else {
				continua = true;
				break;
			}
		}
		
		if(valorRetorno.toString().length() < quebraLinha && !continua){
			return this.pad(valorRetorno.toString(), quebraLinha, "X");
		} else {
			return valorRetorno.toString();
		}
		
	}
	public String getValorPorExtensoContinuacao(){
		Extenso extenso = new Extenso();
		extenso.setNumber(getValor());
		String valor = "( " + extenso.toString().toUpperCase() + " )";
		
		StringBuilder valorRetorno = new StringBuilder();
		String[] palavras = valor.split(" ");
		int qtdLetras = 0;
		for (String palavra : palavras) {
			qtdLetras += palavra.length() + 1;
			if(qtdLetras <= quebraLinha){
				continue;
			} else {
				valorRetorno.append(palavra);
				valorRetorno.append(" ");
			}
		}

		return this.pad(valorRetorno.toString(), quebraLinha, "X");
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public String getFavorecido() {
		return favorecido.toUpperCase();
	}
	public String getVerso() {
		return verso;
	}
	public Calendar getEmissao() {
		return emissao;
	}
	public Long getNumeroCheque() {
		return numeroCheque;
	}
	public void setNumeroCheque(Long numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
	public void setEmissao(Calendar emissao) {
		this.emissao = emissao;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public void setFavorecido(String favorecido) {
		this.favorecido = favorecido;
	}
	public void setVerso(String verso) {
		this.verso = verso;
	}
	
}

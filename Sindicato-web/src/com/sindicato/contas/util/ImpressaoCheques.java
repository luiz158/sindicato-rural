package com.sindicato.contas.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
 
	public String getMes(){
		DateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
		return simpleDateFormat.format(Calendar.getInstance().getTime());
	}
	public int getAno(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	public int getDia(){
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	public String getValorFormatado(){
		return NumberFormat.getInstance(new Locale("pt", "BR")).format(this.getValor()); 
	}
	public String getValorPorExtenso(){
		Extenso extenso = new Extenso();
		extenso.setNumber(getValor());
		return extenso.toString();
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public String getFavorecido() {
		return favorecido;
	}
	public String getVerso() {
		return verso;
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

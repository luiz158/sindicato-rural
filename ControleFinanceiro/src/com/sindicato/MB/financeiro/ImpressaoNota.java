package com.sindicato.MB.financeiro;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.entity.Debito;

@RequestScoped
@ManagedBean
public class ImpressaoNota implements Serializable {

	private static final long serialVersionUID = 1L;
	private Debito debito;
	
	@PostConstruct
	public void init(){
		debito = (Debito)UtilBean.getERemoveValorSessao("debitoImpressao");
	}

	public Debito getDebito() {
		return debito;
	}
	public void setDebito(Debito debito) {
		this.debito = debito;
	}
}

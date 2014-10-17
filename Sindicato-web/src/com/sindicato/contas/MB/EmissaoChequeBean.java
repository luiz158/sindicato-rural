package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.contasapagar.entity.Conta;

@ManagedBean
@ViewScoped
public class EmissaoChequeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private BancoDAO bancoDAO;
	@EJB private ChequeEmitidoDAO chequeDAO;

	private ChequeEmitido cheque;
	private List<Banco> bancos;
	private List<Conta> contasPendentes;

	public void reset() {
		cheque = new ChequeEmitido();
	}

	public void emitirCheque() {
		try {
			
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Cheque emitido com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ChequeEmitido getCheque() {
		return cheque;
	}

	public List<Banco> getBancos() {
		return bancos;
	}

	public List<Conta> getContasPendentes() {
		return contasPendentes;
	}

	public void setCheque(ChequeEmitido cheque) {
		this.cheque = cheque;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	public void setContasPendentes(List<Conta> contasPendentes) {
		this.contasPendentes = contasPendentes;
	}


}

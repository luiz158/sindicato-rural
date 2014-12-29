package com.sindicato.contas.MB;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.sindicato.contas.util.ImpressaoCheques;
import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.ChequeEmitido;

@ManagedBean
@RequestScoped
public class ImpressaoChequeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ChequeEmitidoDAO chequeDAO;
	private int idCheque;
	private ImpressaoCheques impressaoCheque;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getIdCheque() {
		return idCheque;
	}
	public ImpressaoCheques getImpressaoCheque() {
		return impressaoCheque;
	}
	public void setIdCheque(int idCheque) {
		this.idCheque = idCheque;
		
		ChequeEmitido chequeEmitido = chequeDAO.getChequePorId(idCheque);
		impressaoCheque = new ImpressaoCheques();
		impressaoCheque.setFavorecido(chequeEmitido.getFavorecido());
		impressaoCheque.setValor(chequeEmitido.getValor());
		impressaoCheque.setVerso(chequeEmitido.getVersoCheque());
	}
	public void setImpressaoCheque(ImpressaoCheques impressaoCheque) {
		this.impressaoCheque = impressaoCheque;
	}
	
}

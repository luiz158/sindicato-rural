package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class EmissaoChequeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private BancoDAO bancoDAO;
	@EJB private ChequeEmitidoDAO chequeDAO;
	@EJB private ContaDAO contaDAO;
	
	private ChequeEmitido cheque;
	private List<Banco> bancos;
	private List<Conta> contasPendentes;

	private List<Conta> contasSelecionadas;
	
	public void reset() {
		cheque = new ChequeEmitido();
		contasPendentes = null;
		contasSelecionadas = null;
	}

	public void carregaProximoNumeroCheque(){
		Long numeroUltimoChequeEmitido = chequeDAO.getNumeroUltimoChequeEmitido(cheque.getBanco());
		cheque.setIdentificacao(numeroUltimoChequeEmitido + 1);
	}
	
	public void incluirContas(){
		for (Conta contaSelecionada : contasSelecionadas) {
			cheque.getContasPagas().add(contaSelecionada);
			contasPendentes.remove(contaSelecionada);
		}
	}
	public void removerConta(Conta conta){
		cheque.getContasPagas().remove(conta);
		contasPendentes.add(conta);
	}
	
	public void imprimirCheque(int idCheque) {
	    final String pagina = "/Contas/cheque.xhtml"; 
	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
	    String actionUrl = viewHandler.getActionURL(facesContext, pagina);
	    
	    String javaScriptText = "window.open('"+actionUrl+"?id="+idCheque+"');";
	    RequestContext.getCurrentInstance().execute(javaScriptText);         
	}
	
	public void emitirCheque() {
		try {
			ResultOperation result = new ResultOperation();
			//	ResultOperation result = chequeDAO.emitirCheque(cheque);
		//	if(result.isSuccess()){
			if(true){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
				
				this.imprimirCheque(cheque.getId());
				this.reset();
			} else {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
						"Atenção", result.getMessage());
			}
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
		if(cheque == null){
			cheque = new ChequeEmitido();
		}
		return cheque;
	}

	public List<Banco> getBancos() {
		if(bancos == null){
			bancos = bancoDAO.getAll();
		}
		return bancos;
	}

	public List<Conta> getContasPendentes() {
		if(contasPendentes == null){
			contasPendentes = contaDAO.getContasPendentes();
		}
		return contasPendentes;
	}

	public List<Conta> getContasSelecionadas() {
		if(contasSelecionadas == null){
			contasSelecionadas = new ArrayList<Conta>();
		}
		return contasSelecionadas;
	}

	public void setContasSelecionadas(List<Conta> contasSelecionadas) {
		this.contasSelecionadas = contasSelecionadas;
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

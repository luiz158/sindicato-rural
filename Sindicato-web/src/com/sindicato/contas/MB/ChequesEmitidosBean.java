package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class ChequesEmitidosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB private ChequeEmitidoDAO chequeDAO;
	
	private ChequeEmitido chequeSelecionado;
	private List<ChequeEmitido> cheques;
	private int indexTab;
	
	public void reset() {
		chequeSelecionado = new ChequeEmitido();
		cheques = null;
	}
	
	public void alterarAba(int newTab){
		this.indexTab = newTab;
	}
	
	public void cancelarCheque() {
		try {
			ResultOperation result = chequeDAO.cancelarCheque(chequeSelecionado);
			if(result.isSuccess()){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
				this.reset();
				this.alterarAba(0);
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

	public void imprimirCheque() {
		if(chequeSelecionado.getId() == 0){
			return;
		}
		
		ResultOperation result = chequeDAO.salvarCheque(chequeSelecionado);
		if(result.isSuccess()){
		    final String pagina = "/Contas/cheque.xhtml"; 
		    FacesContext facesContext = FacesContext.getCurrentInstance();

		    ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		    String actionUrl = viewHandler.getActionURL(facesContext, pagina);
		    
		    String javaScriptText = "window.open('"+actionUrl+"?id="+chequeSelecionado.getId()+"');";
		    RequestContext.getCurrentInstance().execute(javaScriptText);         
		} else {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", result.getMessage());
		}
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ChequeEmitido getChequeSelecionado() {
		if(chequeSelecionado == null){
			chequeSelecionado = new ChequeEmitido();
		}
		return chequeSelecionado;
	}

	public List<ChequeEmitido> getCheques() {
		if(cheques == null){
			cheques = chequeDAO.listarCheques();
		}
		return cheques;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setChequeSelecionado(ChequeEmitido chequeSelecionado) {
		this.chequeSelecionado = chequeSelecionado;
	}

	public void setCheques(List<ChequeEmitido> cheques) {
		this.cheques = cheques;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}



}

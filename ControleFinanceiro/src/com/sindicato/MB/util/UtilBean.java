package com.sindicato.MB.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.sindicato.MB.seguranca.LoginBean;
import com.sindicato.entity.autenticacao.Usuario;

public class UtilBean {

	public static FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public static void addMessage(Severity severity, String titulo, String mensagem){
		UtilBean.getFacesContext().addMessage("", new FacesMessage(severity, titulo, mensagem));
	}
	
	public static void removeMessages(){
		FacesContext facesContext = UtilBean.getFacesContext();     
        if (facesContext != null) {
            //facesContext.getMessageList().clear();
         }
	}
	
	public static void addMessageAndRemoveOthers(Severity severity, String titulo, String mensagem){
		UtilBean.removeMessages();
		UtilBean.addMessage(severity, titulo, mensagem);
	}
	
	public static Usuario getUsuarioLogado() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);
		LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
		return loginBean.getUsuarioLogado();
	}

}

package com.sindicato.MB.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

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
	
}

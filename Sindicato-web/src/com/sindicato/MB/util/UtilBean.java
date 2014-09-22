package com.sindicato.MB.util;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import com.sindicato.MB.seguranca.LoginBean;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Recebimento;
import com.sindicato.entity.autenticacao.Usuario;

public class UtilBean {

	public static boolean confereValorRecebimentos(Debito debito){
		BigDecimal recebimentos = BigDecimal.ZERO;
		
		for (Recebimento recebimento : debito.getRecebimentos()) {
			recebimentos = recebimentos.add(recebimento.getValor());
		}
		
		if(recebimentos.compareTo(debito.getTotalDebitos()) == 0){
			return true;
		}
		return false;
	}

	
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

	public static void setUsuarioLogado(Usuario usuario) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);
		session.setAttribute("usuarioLogado", usuario);
	}
	
	public static void addValorSessaoFlash(String name, Object value){
		ExternalContext externalContext = UtilBean.getFacesContext().getExternalContext();
		externalContext.getFlash().put(name, value);
		externalContext.getFlash().setKeepMessages(true);
	}

	public static Object getValorSessaoFlash(String name){
		ExternalContext externalContext = UtilBean.getFacesContext().getExternalContext();
		return externalContext.getFlash().get(name);
	}
	
	public static void addValorSessao(String name, Object value){
		UtilBean.getFacesContext().getExternalContext().getSessionMap().put(name, value); 
	}

	public static Object getValorSessao(String name){
		ExternalContext ec = UtilBean.getFacesContext().getExternalContext();
		if (ec.getSessionMap().containsKey(name)){  
            Object object = ec.getSessionMap().get(name);  
            return object;
        }
		return null;
	}
	
	public static Object getERemoveValorSessao(String name){
		ExternalContext ec = UtilBean.getFacesContext().getExternalContext();
		if (ec.getSessionMap().containsKey(name)){  
            Object object = ec.getSessionMap().get(name);  
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("meuObjetoId");
            return object;
        }
		return null;
	}
	
	public static Object getClassLookup(String lookup){
		InitialContext ic;
		try {
			ic = new InitialContext();
			return ic.lookup("java:global/Sindicato-ear/" + lookup);
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

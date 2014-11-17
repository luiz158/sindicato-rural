package com.sindicato.contas.MB;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.report.model.RelatorioContas;

@ManagedBean
@ViewScoped
public class RelatorioContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ContaDAO contaDAO;
	private RelatorioContas relatorio;
	
	public void buscar(){
		relatorio = contaDAO.getRelatorioContas(relatorio.getFiltro());
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public RelatorioContas getRelatorio() {
		if(relatorio == null){
			relatorio = new RelatorioContas();
		}
		return relatorio;
	}
	public void setRelatorio(RelatorioContas relatorio) {
		this.relatorio = relatorio;
	}

}

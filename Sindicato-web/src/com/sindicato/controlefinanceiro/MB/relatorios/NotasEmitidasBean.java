package com.sindicato.controlefinanceiro.MB.relatorios;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fieldset.Fieldset;

import com.sindicato.controlefinanceiro.dao.RelatorioDAO;
import com.sindicato.controlefinanceiro.report.model.RelatorioNotasEmitidas;

@ManagedBean
@ViewScoped
public class NotasEmitidasBean {

	@EJB private RelatorioDAO relatorioDAO;
	
	private RelatorioNotasEmitidas relatorio;

	private Calendar dataDe;
	private Calendar dataAte;

	private Fieldset fieldSetResumo; 
	private Fieldset fieldSetFiltro; 
	
	private CommandButton btnImprimir;
	
	public void carregaRelatorio(){
		dataAte.add(Calendar.HOUR_OF_DAY, 23);
		dataAte.add(Calendar.MINUTE, 59);
		dataAte.add(Calendar.SECOND, 59);
		
		relatorio = relatorioDAO.getRelatorioNotasEmitidas(dataDe, dataAte);
		btnImprimir.setRendered(true);
		fieldSetResumo.setRendered(true);
		fieldSetFiltro.setCollapsed(true);
	}
	
	public Calendar getDataDe() {
		return dataDe;
	}
	public Calendar getDataAte() {
		return dataAte;
	}
	public Fieldset getFieldSetResumo() {
		return fieldSetResumo;
	}
	public Fieldset getFieldSetFiltro() {
		return fieldSetFiltro;
	}
	public CommandButton getBtnImprimir() {
		return btnImprimir;
	}
	public RelatorioNotasEmitidas getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioNotasEmitidas relatorio) {
		this.relatorio = relatorio;
	}
	public void setBtnImprimir(CommandButton btnImprimir) {
		this.btnImprimir = btnImprimir;
	}
	public void setFieldSetFiltro(Fieldset fieldSetFiltro) {
		this.fieldSetFiltro = fieldSetFiltro;
	}
	public void setFieldSetResumo(Fieldset fieldSetResumo) {
		this.fieldSetResumo = fieldSetResumo;
	}
	public void setDataDe(Calendar dataDe) {
		this.dataDe = dataDe;
	}
	public void setDataAte(Calendar dataAte) {
		this.dataAte = dataAte;
	}

}

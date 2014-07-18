package com.sindicato.MB.relatorios;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fieldset.Fieldset;

import com.sindicato.dao.RelatorioDAO;
import com.sindicato.report.model.RelatorioResumoServico;

@ManagedBean
@ViewScoped
public class ResumoServicosBean {

	@EJB private RelatorioDAO relatorioDAO;
	
	private RelatorioResumoServico relatorio;

	private Calendar dataDe;
	private Calendar dataAte;

	private SimpleDateFormat dataString = new SimpleDateFormat("dd/MM/yyyy");
	
	private Fieldset fieldSetResumo; 
	private Fieldset fieldSetFiltro; 
	
	private CommandButton btnImprimir;
	
	public void carregaRelatorio(){
		
		dataAte.add(Calendar.DAY_OF_YEAR, 1);
		dataAte.add(Calendar.MILLISECOND, -1);
		
		relatorio = relatorioDAO.getResumoServico(dataDe, dataAte);
		btnImprimir.setRendered(true);
		fieldSetResumo.setRendered(true);
		fieldSetFiltro.setCollapsed(true);
	}
	
	public RelatorioResumoServico getRelatorio() {
		return relatorio;
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
	public SimpleDateFormat getDataString() {
		return dataString;
	}

	public void setDataString(SimpleDateFormat dataString) {
		this.dataString = dataString;
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

	public void setRelatorio(RelatorioResumoServico relatorio) {
		this.relatorio = relatorio;
	}
	public void setDataDe(Calendar dataDe) {
		this.dataDe = dataDe;
	}
	public void setDataAte(Calendar dataAte) {
		this.dataAte = dataAte;
	}

}

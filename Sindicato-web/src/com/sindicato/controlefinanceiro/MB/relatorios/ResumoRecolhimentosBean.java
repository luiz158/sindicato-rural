package com.sindicato.controlefinanceiro.MB.relatorios;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fieldset.Fieldset;

import com.sindicato.controlefinanceiro.dao.RelatorioDAO;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoRecolhimentos;

@ManagedBean
@ViewScoped
public class ResumoRecolhimentosBean {
	@EJB
	private RelatorioDAO relatorioDAO;
	private RelatorioResumoRecolhimentos relatorio;
	private Calendar dataDe;
	private Calendar dataAte;
	private SimpleDateFormat dataString = new SimpleDateFormat("dd/MM/yyyy");
	private Fieldset fieldSetResumo;
	private Fieldset fieldSetFiltro;
	private CommandButton btnImprimir;

	public void carregaRelatorio() {
		dataAte.set(Calendar.HOUR_OF_DAY, 23);
		dataAte.set(Calendar.MINUTE, 59);
		dataAte.set(Calendar.SECOND, 59);

		relatorio = relatorioDAO.getResumoRecolhimentos(dataDe, dataAte);
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
	public SimpleDateFormat getDataString() {
		return dataString;
	}
	public RelatorioResumoRecolhimentos getRelatorio() {
		return relatorio;
	}
	public void setRelatorio(RelatorioResumoRecolhimentos relatorio) {
		this.relatorio = relatorio;
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
	public void setDataDe(Calendar dataDe) {
		this.dataDe = dataDe;
	}
	public void setDataAte(Calendar dataAte) {
		this.dataAte = dataAte;
	}
}

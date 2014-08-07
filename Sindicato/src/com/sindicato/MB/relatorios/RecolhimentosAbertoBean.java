package com.sindicato.MB.relatorios;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fieldset.Fieldset;

import com.sindicato.dao.RelatorioDAO;
import com.sindicato.report.model.RelatorioRecolhimentosAberto;

@ManagedBean
@ViewScoped
public class RecolhimentosAbertoBean {
	
	@EJB
	private RelatorioDAO relatorioDAO;
	
	private RelatorioRecolhimentosAberto relatorio;
	private Calendar dataAte = Calendar.getInstance();
	private Fieldset fieldSetResumo;
	private Fieldset fieldSetFiltro;
	private CommandButton btnImprimir;

	public void carregaRelatorio() {
		relatorio = relatorioDAO.getRelatorioRecolhimentosAberto(dataAte);
		btnImprimir.setRendered(true);
		fieldSetResumo.setRendered(true);
		fieldSetFiltro.setCollapsed(true);
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
	public RelatorioRecolhimentosAberto getRelatorio() {
		return relatorio;
	}
	public void setRelatorio(RelatorioRecolhimentosAberto relatorio) {
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
	public void setDataAte(Calendar dataAte) {
		this.dataAte = dataAte;
	}
}

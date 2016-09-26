package com.sindicato.controlefinanceiro.MB.relatorios;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fieldset.Fieldset;

import com.sindicato.controlefinanceiro.dao.RelatorioDAO;
import com.sindicato.controlefinanceiro.report.model.DetalheNotasEmitidas;
import com.sindicato.controlefinanceiro.report.model.NotasEmitidasDia;
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
		dataAte.set(Calendar.HOUR_OF_DAY, 23);
		dataAte.set(Calendar.MINUTE, 59);
		dataAte.set(Calendar.SECOND, 59);
		
		relatorio = relatorioDAO.getRelatorioNotasEmitidas(dataDe, dataAte);
		btnImprimir.setRendered(true);
		fieldSetResumo.setRendered(true);
		fieldSetFiltro.setCollapsed(true);
	}

	public void exportXLS() {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder nomePlanilha = new StringBuilder();
        nomePlanilha.append("notas-emitidas-");
        nomePlanilha.append(dateFormat.format(dataDe.getTime()));
        nomePlanilha.append("-ate-");
        nomePlanilha.append(dateFormat.format(dataAte.getTime()));
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("notas-emitidas");
        HSSFRow header = sheet.createRow(0);
         
        header.createCell(0).setCellValue("Data emissão nota");
        header.createCell(1).setCellValue("Notas");
        header.createCell(2).setCellValue("Serviço");
        header.createCell(3).setCellValue("Valor total do serviço no dia");
        header.createCell(4).setCellValue("Valor no dia");
        header.createCell(5).setCellValue("Valor total das notas emitidas");
        
        int ind = 1;
        for (NotasEmitidasDia notasDia : relatorio.getNotasEmitidasDia()) {
			for (DetalheNotasEmitidas detalheNotas : notasDia.getNotas()) {
		        HSSFRow row = sheet.createRow(ind++);
		        row.createCell(0).setCellValue(dateFormat.format(notasDia.getDataEmissaoNota().getTime()));
		        row.createCell(1).setCellValue(detalheNotas.getPrimeiraNota() + " até " + detalheNotas.getUltimaNota());
		        row.createCell(2).setCellValue(detalheNotas.getServico());
		        row.createCell(3).setCellValue(NumberFormat.getCurrencyInstance().format(detalheNotas.getValorTotalNota()));
		        row.createCell(4).setCellValue(NumberFormat.getCurrencyInstance().format(notasDia.getValorTotalDia()));
		        row.createCell(5).setCellValue(NumberFormat.getCurrencyInstance().format(relatorio.getValorTotalNotas()));
			}
		}
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nomePlanilha.toString() + ".xls\"");

        try {
			workbook.write(externalContext.getResponseOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
        facesContext.responseComplete();
        
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

package com.sindicato.controlefinanceiro.MB.relatorios;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.RelatorioDAO;
import com.sindicato.controlefinanceiro.report.model.RelatorioFolhaVotacao;
import com.sindicato.controlefinanceiro.reports.GeradorReports;

@ManagedBean
@ViewScoped
public class FolhaVotacaoBean {

	@EJB private RelatorioDAO relatorioDAO;
	
	private RelatorioFolhaVotacao relatorio;
	private String titulo;
	private Calendar dataEleicao;
	
	public void imprimirRelatorio() throws JRException, IOException{
		
		relatorio = relatorioDAO.getRelatorioFolhaVotacao();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy", new Locale("pt", "BR"));
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("titulo", titulo);
		parameters.put("dataEleicao", dateFormat.format(dataEleicao.getTime()));
		
		GeradorReports gerador = new GeradorReports("folhaVotacao.jasper", parameters, new JRBeanCollectionDataSource(
				relatorio.getDetalhesAssociado()));
		
		FacesContext context = UtilBean.getFacesContext();

		HttpServletResponse httpServletResponse = (HttpServletResponse) context
				.getExternalContext().getResponse();

		httpServletResponse.setContentType("application/pdf");

		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(gerador.getJasperPrint(),
				servletOutputStream);
		
		servletOutputStream.flush();  
        servletOutputStream.close();
		
		context.responseComplete();
	}

	public RelatorioDAO getRelatorioDAO() {
		return relatorioDAO;
	}

	public String getTitulo() {
		return titulo;
	}

	public Calendar getDataEleicao() {
		return dataEleicao;
	}

	public RelatorioFolhaVotacao getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioFolhaVotacao relatorio) {
		this.relatorio = relatorio;
	}

	public void setRelatorioDAO(RelatorioDAO relatorioDAO) {
		this.relatorioDAO = relatorioDAO;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDataEleicao(Calendar dataEleicao) {
		this.dataEleicao = dataEleicao;
	}

}

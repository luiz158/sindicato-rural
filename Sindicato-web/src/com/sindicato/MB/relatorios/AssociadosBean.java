package com.sindicato.MB.relatorios;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import com.sindicato.controlefinanceiro.report.model.RelatorioAssociados;
import com.sindicato.reports.GeradorReports;

@ManagedBean
@ViewScoped
public class AssociadosBean {

	@EJB private RelatorioDAO relatorioDAO;
	
	private RelatorioAssociados relatorio;
	
	@PostConstruct
	public void init(){
		relatorio = relatorioDAO.getAssociados();
	}
	
	public void imprimirRelatorio() throws JRException, IOException{
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("totalAssociados", relatorio.getTotalAssociados());
		parameters.put("associadosEmDia", relatorio.getTotalAssociadosEmDia());
		parameters.put("associadosEmAtraso", relatorio.getTotalAssociadosEmAtraso());
		
		GeradorReports gerador = new GeradorReports("associados.jasper", parameters, new JRBeanCollectionDataSource(
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

	
	public RelatorioAssociados getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(RelatorioAssociados relatorio) {
		this.relatorio = relatorio;
	}

}

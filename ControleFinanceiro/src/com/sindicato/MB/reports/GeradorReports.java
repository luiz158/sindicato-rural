package com.sindicato.MB.reports;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.sindicato.MB.util.UtilBean;

public class GeradorReports {

	private final String CAMINHO_REPORTS = "/reports/";
	private JasperPrint jasperPrint;

	private Map<String, Object> parameters;
	private JRBeanCollectionDataSource beanCollectionDataSource;
	private String report;
	
	public GeradorReports(String report, Map<String, Object> parameters, JRBeanCollectionDataSource beanCollectionDataSource) throws JRException{
		this.parameters = parameters;
		this.beanCollectionDataSource = beanCollectionDataSource;
		this.report = CAMINHO_REPORTS + report;
		this.initReport();
	}

	private void initReport() throws JRException {
		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(report);
		
		jasperPrint = JasperFillManager.fillReport(reportPath, parameters,
				beanCollectionDataSource);
	}

	public void imprimirNota() throws JRException, IOException {
		FacesContext context = UtilBean.getFacesContext();

		HttpServletResponse httpServletResponse = (HttpServletResponse) context
				.getExternalContext().getResponse();

		httpServletResponse.setContentType("application/pdf");

		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		
		servletOutputStream.flush();  
        servletOutputStream.close();
		
		context.responseComplete();
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}
	
}

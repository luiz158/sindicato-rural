package com.sindicato.reports;

import java.io.IOException;
import java.io.InputStream;
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
	private final String CAMINHO_IMAGENS = UtilBean.getFacesContext().getExternalContext().getRealPath("/reports/imagens/");
	
	private JasperPrint jasperPrint;

	private Map<String, Object> parameters;
	private JRBeanCollectionDataSource beanCollectionDataSource;
	private String report;
	
	public GeradorReports(String report, Map<String, Object> parameters, JRBeanCollectionDataSource beanCollectionDataSource) throws JRException{
		this.parameters = parameters;
		this.parameters.put("logo", CAMINHO_IMAGENS + "/Logo_nota.jpg");
		
		this.beanCollectionDataSource = beanCollectionDataSource;
		this.report = CAMINHO_REPORTS + report;
		this.initReport();
	}

	private void initReport() throws JRException {
		InputStream reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getResourceAsStream(report);
		
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

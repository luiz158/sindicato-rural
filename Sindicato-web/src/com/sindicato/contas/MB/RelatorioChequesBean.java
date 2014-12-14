package com.sindicato.contas.MB;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
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
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.ChequeEmitidoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.report.model.RelatorioCheques;
import com.sindicato.controlefinanceiro.reports.GeradorReports;

@ManagedBean
@ViewScoped
public class RelatorioChequesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ChequeEmitidoDAO chequeDAO;
	@EJB private BancoDAO bancoDAO;
	private RelatorioCheques relatorio;
	
	private List<Banco> bancos;
	
	public void buscar(){
		relatorio = chequeDAO.getRelatorioCheques(relatorio.getFiltro());
	}
	public void imprimirRelatorio(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("valorTotal", "R$ " + NumberFormat.getInstance(new Locale("pt", "BR")).format(relatorio.getValorTotal()));
		parameters.put("titulo", relatorio.getTitulo());
		parameters.put("totalCheques", relatorio.getResultado().size());
		
		GeradorReports gerador;
		ServletOutputStream servletOutputStream = null;
		FacesContext context = null;
		try {
			gerador = new GeradorReports("relatorioCheques.jasper", parameters, new JRBeanCollectionDataSource(
					relatorio.getResultado()));
		
			context = UtilBean.getFacesContext();
	
			HttpServletResponse httpServletResponse = (HttpServletResponse) context
					.getExternalContext().getResponse();
	
			httpServletResponse.setContentType("application/pdf");
			servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(gerador.getJasperPrint(),
					servletOutputStream);
			
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		context.responseComplete();
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Banco> getBancos() {
		if(bancos == null){
			bancos = bancoDAO.getAll();
		}
		return bancos;
	}
	public RelatorioCheques getRelatorio() {
		if(relatorio == null){
			relatorio = new RelatorioCheques();
		}
		return relatorio;
	}
	public void setRelatorio(RelatorioCheques relatorio) {
		this.relatorio = relatorio;
	}
	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}


}

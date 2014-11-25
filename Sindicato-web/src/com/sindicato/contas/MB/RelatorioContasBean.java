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
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.report.model.RelatorioContas;
import com.sindicato.controlefinanceiro.reports.GeradorReports;

@ManagedBean
@ViewScoped
public class RelatorioContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ContaDAO contaDAO;
	@EJB private BancoDAO bancoDAO;
	private RelatorioContas relatorio;
	private boolean ocultarFiltro;
	
	private List<Banco> bancos;
	
	public void buscar(){
		relatorio = contaDAO.getRelatorioContas(relatorio.getFiltro());
		ocultarFiltro = true;
	}
	public void imprimirRelatorio(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("valorTotal", "R$ " + NumberFormat.getInstance(new Locale("pt", "BR")).format(relatorio.getValorTotal()));
		parameters.put("titulo", relatorio.getTitulo());
		
		GeradorReports gerador;
		ServletOutputStream servletOutputStream = null;
		FacesContext context = null;
		try {
			gerador = new GeradorReports("relatorioContas.jasper", parameters, new JRBeanCollectionDataSource(
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
	public RelatorioContas getRelatorio() {
		if(relatorio == null){
			relatorio = new RelatorioContas();
		}
		return relatorio;
	}
	public void setRelatorio(RelatorioContas relatorio) {
		this.relatorio = relatorio;
	}
	public List<Banco> getBancos() {
		if(bancos == null){
			bancos = bancoDAO.getAll();
		}
		return bancos;
	}
	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}
	public boolean isOcultarFiltro() {
		return ocultarFiltro;
	}
	public void setOcultarFiltro(boolean ocultarFiltro) {
		this.ocultarFiltro = ocultarFiltro;
	}

}

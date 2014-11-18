package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.report.model.RelatorioContas;

@ManagedBean
@ViewScoped
public class RelatorioContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ContaDAO contaDAO;
	@EJB private BancoDAO bancoDAO;
	private RelatorioContas relatorio;
	
	private List<Banco> bancos;
	
	public void buscar(){
		relatorio = contaDAO.getRelatorioContas(relatorio.getFiltro());
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

}

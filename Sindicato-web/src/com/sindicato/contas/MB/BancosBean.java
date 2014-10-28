package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.tabview.TabView;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.entity.Banco;

@ManagedBean
@ViewScoped
public class BancosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private BancoDAO bancoDAO;

	private Banco bancoSelecionado;
	private List<Banco> bancos;

	private TabView tabView;

	public void alterTab(int newTab) {
		tabView.setActiveIndex(newTab);
	}

	public void reset() {
		bancoSelecionado = new Banco();
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void salvar() {
		try {
			if (bancoSelecionado.getId() == 0) {
				bancoDAO.cadastrar(bancoSelecionado);
			} else {
				bancoDAO.alterar(bancoSelecionado);
			}
			//this.reset();
			//alterTab(0);
			bancos = bancoDAO.getAll();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Banco salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Banco getBancoSelecionada() {
		if(bancoSelecionado == null){
			bancoSelecionado = new Banco();
		}
		return bancoSelecionado;
	}

	public List<Banco> getBancos() {
		if(bancos == null){
			bancos = bancoDAO.getAll();
		}
		return bancos;
	}

	public TabView getTabView() {
		return tabView;
	}
 
	public void setBancoSelecionada(Banco bancoSelecionado) {
		this.bancoSelecionado = bancoSelecionado;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}


}

package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class BancosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private BancoDAO bancoDAO;

	private Banco bancoSelecionado;
	private List<Banco> bancos;

	private int tabIndex;

	public void alterTab(int newTab) {
		tabIndex = newTab;
	}

	public void reset() {
		bancoSelecionado = new Banco();
		bancos = null;
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void salvar() {
		try {
			ResultOperation result;
			if (bancoSelecionado.getId() == 0) {
				result = bancoDAO.cadastrar(bancoSelecionado);
			} else {
				result = bancoDAO.alterar(bancoSelecionado);
			}
			if(result.isSuccess()){
				this.reset();
				alterTab(0);
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
			} else {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
						"Atenção", result.getMessage());
			}
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

	public Banco getBancoSelecionado() {
		return bancoSelecionado;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setBancoSelecionado(Banco bancoSelecionado) {
		this.bancoSelecionado = bancoSelecionado;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void setBancoSelecionada(Banco bancoSelecionado) {
		this.bancoSelecionado = bancoSelecionado;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}


}

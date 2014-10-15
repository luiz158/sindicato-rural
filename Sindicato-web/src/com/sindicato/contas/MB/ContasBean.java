package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.tabview.TabView;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Conta;

@ManagedBean
@ViewScoped
public class ContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ContaDAO contaDAO;

	private Conta contaSelecionada;
	private List<Conta> contas;

	private TabView tabView;

	public void alterTab(int newTab) {
		tabView.setActiveIndex(newTab);
	}

	public void reset() {
		contaSelecionada = new Conta();
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void excluir(Conta conta) {
		try {
			if (conta.getId() == 0) {
				reset();
				return;
			}

			contaDAO.removeById(conta.getId());
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Conta excluída com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void salvar() {
		try {
			if (contaSelecionada.getId() == 0) {
				contaDAO.insert(contaSelecionada);
			} else {
				contaDAO.update(contaSelecionada);
			}
			//this.reset();
			//alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Conta salva com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Conta getContaSelecionada() {
		if(contaSelecionada == null){
			contaSelecionada = new Conta();
		}
		return contaSelecionada;
	}

	public List<Conta> getContas() {
		//if(contas == null){
			contas = contaDAO.getAll();
		//}
		return contas;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setContaSelecionada(Conta contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}


}

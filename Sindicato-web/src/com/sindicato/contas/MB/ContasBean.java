package com.sindicato.contas.MB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.Conta;

@ManagedBean
@ViewScoped
public class ContasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ContaDAO contaDAO;
	@EJB private BancoDAO bancoDAO;
	
	
	private Conta contaSelecionada;
	private List<Conta> contas;
	private List<Banco> bancos;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
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

			contaDAO.remover(conta);
			this.reset();
			alterTab(0);
			contas = contaDAO.listarContas();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Conta exclu�da com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void salvar() {
		try {
			if (contaSelecionada.getId() == 0) {
				contaDAO.cadastrar(contaSelecionada);
			} else {
				contaDAO.alterar(contaSelecionada);
			}
			this.reset();
			alterTab(0);
			contas = contaDAO.listarContas();
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
		if(contas == null){
			contas = contaDAO.listarContas();
		}
		return contas;
	}
	public List<Banco> getBancos() {
		if(bancos == null){
			bancos = bancoDAO.getAll();
		}
		return bancos;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}
	public void setContaSelecionada(Conta contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}
	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

}

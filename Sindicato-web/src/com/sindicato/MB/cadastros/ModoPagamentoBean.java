package com.sindicato.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.ModoPagamentoDAO;
import com.sindicato.controlefinanceiro.entity.ModoPagamento;

@ManagedBean
@ViewScoped
public class ModoPagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ModoPagamentoDAO pagamentoDAO;

	private ModoPagamento pagamentoSelecionado;
	private List<ModoPagamento> pagamentos;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		pagamentoSelecionado = new ModoPagamento();
	}

	public void salvar() {
		try {
			if (pagamentoSelecionado.getId() == 0) {
				pagamentoDAO.insert(pagamentoSelecionado);
			} else {
				pagamentoDAO.update(pagamentoSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Modo de pagamento salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void novo(){
		this.reset();
		alterTab(1);
	}
	
	public void excluir(ModoPagamento pagamento) {
		try {
			if(pagamento.getId() == 0){ return; }
			
			pagamentoDAO.removeById(pagamento.getId());
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Modo de pagamento excluído com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public ModoPagamento getPagamentoSelecionado() {
		if (pagamentoSelecionado == null) {
			pagamentoSelecionado = new ModoPagamento();
		}
		return pagamentoSelecionado;
	}

	public List<ModoPagamento> getPagamentos() {
		pagamentos = pagamentoDAO.getAll();
		return pagamentos;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}
	public void setPagamentoSelecionado(ModoPagamento pagamentoSelecionado) {
		this.pagamentoSelecionado = pagamentoSelecionado;
	}
	public void setPagamentos(List<ModoPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}


}

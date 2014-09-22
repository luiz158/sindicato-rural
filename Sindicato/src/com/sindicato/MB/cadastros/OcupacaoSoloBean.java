package com.sindicato.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.TipoOcupacaoSoloDAO;
import com.sindicato.controlefinanceiro.entity.TipoOcupacaoSolo;

@ManagedBean
@ViewScoped
public class OcupacaoSoloBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private TipoOcupacaoSoloDAO ocupacaoDAO;

	private TipoOcupacaoSolo ocupacaoSelecionado;
	private List<TipoOcupacaoSolo> ocupacoes;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		ocupacaoSelecionado = new TipoOcupacaoSolo();
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void excluir(TipoOcupacaoSolo ocupacao) {
		try {
			if (ocupacao.getId() == 0) {
				return;
			}

			ocupacaoDAO.removeById(ocupacao.getId());
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Tipo de ocupação de solo excluído com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void salvar() {
		try {
			if (ocupacaoSelecionado.getId() == 0) {
				ocupacaoDAO.insert(ocupacaoSelecionado);
			} else {
				ocupacaoDAO.update(ocupacaoSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Tipo de ocupação de solo salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public TipoOcupacaoSolo getOcupacaoSelecionado() {
		if (ocupacaoSelecionado == null) {
			ocupacaoSelecionado = new TipoOcupacaoSolo();
		}
		return ocupacaoSelecionado;
	}

	public List<TipoOcupacaoSolo> getOcupacoes() {
		ocupacoes = ocupacaoDAO.getAll();
		return ocupacoes;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public void setOcupacaoSelecionado(TipoOcupacaoSolo ocupacaoSelecionado) {
		this.ocupacaoSelecionado = ocupacaoSelecionado;
	}

	public void setOcupacoes(List<TipoOcupacaoSolo> ocupacoes) {
		this.ocupacoes = ocupacoes;
	}

}

package com.sindicato.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.ServicoDAO;
import com.sindicato.controlefinanceiro.entity.Servico;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class ServicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ServicoDAO servicoDAO;

	private Servico servicoSelecionado;
	private List<Servico> servicos;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		servicoSelecionado = new Servico();
	}
	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void excluir(Servico servico) {
		try {
			if (servico.getId() == 0) {
				this.reset();
				return;
			}

			ResultOperation result = servicoDAO.remove(servico);
			if(result.isSuccess()){
				this.reset();
				alterTab(0);
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Servico excluído com sucesso");
			}else{
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
						"Atenção", result.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void salvar() {
		try {
			if (servicoSelecionado.getId() == 0) {
				servicoDAO.insert(servicoSelecionado);
			} else {
				servicoDAO.update(servicoSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Servico salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Servico getServicoSelecionado() {
		if (servicoSelecionado == null) {
			servicoSelecionado = new Servico();
		}
		return servicoSelecionado;
	}

	public List<Servico> getServicos() {
		servicos = servicoDAO.getAll();
		return servicos;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public void setservicoSelecionado(Servico servicoSelecionado) {
		this.servicoSelecionado = servicoSelecionado;
	}

	public void setServicoSelecionado(Servico servicoSelecionado) {
		this.servicoSelecionado = servicoSelecionado;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}




}

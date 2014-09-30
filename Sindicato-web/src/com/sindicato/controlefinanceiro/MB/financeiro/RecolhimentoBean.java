package com.sindicato.controlefinanceiro.MB.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.FinanceiroDAO;
import com.sindicato.controlefinanceiro.dao.ListasCFDAO;
import com.sindicato.controlefinanceiro.dao.ModoPagamentoDAO;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.DebitoServico;
import com.sindicato.controlefinanceiro.entity.ModoPagamento;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.sindicato.controlefinanceiro.lazyDataModel.LazyDebitoDataModel;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class RecolhimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ListasCFDAO listasDAO;
	@EJB
	private FinanceiroDAO financeiroDAO;
	@EJB
	private ModoPagamentoDAO modoPagamentoDAO;

	private Debito debitoSelecionado;
	private List<DebitoServico> servicosComRetencao;
	private LazyDataModel<Debito> debitos;
	private List<ModoPagamento> modosPagamento;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		debitoSelecionado = new Debito();
	}

	public void salvar() {
		try {
			this.mergeServicosComRetencao();
			ResultOperation result = financeiroDAO
					.registrarRecolhimentos(debitoSelecionado);
			if (result.isSuccess()) {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
				this.reset();
				this.alterTab(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	private void mergeServicosComRetencao() {
		for (DebitoServico debito : servicosComRetencao) {
			for(DebitoServico debitoSelecionado : debitoSelecionado.getDebitoServicos()){
				if(debitoSelecionado.equals(debito)){
					debitoSelecionado.setRecolhimento(debito.getRecolhimento());
				}
			}
		}
	}

	public Debito getDebitoSelecionado() {
		if (debitoSelecionado == null) {
			debitoSelecionado = new Debito();
		}
		return debitoSelecionado;
	}

	public LazyDataModel<Debito> getDebitos() {
		if (debitos == null) {
			List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
			statusPermitido.add(StatusDebitoEnum.RECEBIDO);
			debitos = new LazyDebitoDataModel(statusPermitido, true);
		}
		return debitos;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public List<ModoPagamento> getModosPagamento() {
		if (modosPagamento == null) {
			modosPagamento = modoPagamentoDAO.getAll();
		}
		return modosPagamento;
	}

	public List<DebitoServico> getServicosComRetencao() {
		servicosComRetencao = new ArrayList<DebitoServico>();
		for (DebitoServico debito : debitoSelecionado.getDebitoServicos()) {
			if (debito.getServico().isRetencao()) {
				servicosComRetencao.add(debito);
			}
		}
		return servicosComRetencao;
	}

	public void setServicosComRetencao(List<DebitoServico> servicosComRetencao) {
		this.servicosComRetencao = servicosComRetencao;
	}

	public void setModosPagamento(List<ModoPagamento> modosPagamento) {
		this.modosPagamento = modosPagamento;
	}

	public void setDebitoSelecionado(Debito debitoSelecionado) {
		this.debitoSelecionado = debitoSelecionado;
	}

	public void setDebitos(LazyDataModel<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

}

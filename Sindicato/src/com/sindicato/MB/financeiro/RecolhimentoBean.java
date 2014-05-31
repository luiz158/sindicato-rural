package com.sindicato.MB.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ListasDAO;
import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ListasDAOImpl;
import com.sindicato.dao.impl.ModoPagamentoDAOImpl;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.ModoPagamento;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class RecolhimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private ListasDAO listasDAO;
	private FinanceiroDAO financeiroDAO;
	private ModoPagamentoDAO modoPagamentoDAO;

	private Debito debitoSelecionado;
	private List<DebitoServico> servicosComRetencao;
	private List<Debito> debitos;
	private List<ModoPagamento> modosPagamento;

	private int indexTab;

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		listasDAO = new ListasDAOImpl(em);
		financeiroDAO = new FinanceiroDAOImpl(em);
		modoPagamentoDAO = new ModoPagamentoDAOImpl(em);
	}

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		debitoSelecionado = new Debito();
	}

	public void salvar() {
		try {
			this.mergeServicosComRetencao();
			ResultOperation result = financeiroDAO.registrarRecolhimentos(debitoSelecionado);
			if(result.isSuccess()){
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

	private void mergeServicosComRetencao(){
		for (DebitoServico debito : servicosComRetencao) {
			int index = debitoSelecionado.getDebitoServicos().indexOf(debito);
			debitoSelecionado.getDebitoServicos().get(index).setRecolhimento(debito.getRecolhimento());
		}
	}
	
	public Debito getDebitoSelecionado() {
		if(debitoSelecionado == null){
			debitoSelecionado = new Debito();
		}
		return debitoSelecionado;
	}

	public List<Debito> getDebitos() {
		debitos = listasDAO.getDebitosNoStatus(StatusDebitoEnum.RECEBIDO);
		return debitos;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public List<ModoPagamento> getModosPagamento() {
		if(modosPagamento == null){
			modosPagamento = modoPagamentoDAO.getAll();
		}
		return modosPagamento;
	}
	public List<DebitoServico> getServicosComRetencao() {
		servicosComRetencao = new ArrayList<DebitoServico>();
		for (DebitoServico debito : debitoSelecionado.getDebitoServicos()) {
			if(debito.getServico().isRetencao()){
				servicosComRetencao.add(debito);
			}
		}
		return servicosComRetencao;
	}

	public void setServicosComRetencao(
			List<DebitoServico> servicosComRetencao) {
		this.servicosComRetencao = servicosComRetencao;
	}
	public void setModosPagamento(List<ModoPagamento> modosPagamento) {
		this.modosPagamento = modosPagamento;
	}
	public void setDebitoSelecionado(Debito debitoSelecionado) {
		this.debitoSelecionado = debitoSelecionado;
	}
	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

}
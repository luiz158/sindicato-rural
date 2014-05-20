package com.sindicato.MB.financeiro;

import java.io.Serializable;
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
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ListasDAOImpl;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.ModoPagamento;
import com.sindicato.entity.Recebimento;
import com.sindicato.entity.Recolhimento;
import com.sindicato.entity.TipoRecebimento;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class RecolhimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private ListasDAO listasDAO;
	private FinanceiroDAO financeiroDAO;

	private Debito debitoSelecionado;
	private List<Debito> debitos;
	private List<ModoPagamento> tiposRecebimento;

	private int indexTab;

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		listasDAO = new ListasDAOImpl(em);
		financeiroDAO = new FinanceiroDAOImpl(em);
	}

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		debitoSelecionado = new Debito();
	}

	public void salvarRecebimento(){
		recebimento.setDebito(debitoSelecionado);
		debitoSelecionado.getRecebimentos().add(recebimento);
		recebimento = new Recebimento();
	}
	
	public void removerRecebimento(Recebimento recebimento){
		debitoSelecionado.getRecebimentos().remove(recebimento);
	}
	
	public void salvar() {
		try {
			ResultOperation result = financeiroDAO.registrarRecebimento(debitoSelecionado);
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
	public Recebimento getRecebimento() {
		if(recebimento == null){
			recebimento = new Recebimento();
		}
		return recebimento;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public List<DestinoRecebimento> getDestinos() {
		if(destinos == null){
			destinos = listasDAO.getTodosDestinosRecebimento();
		}
		return destinos;
	}
	public List<TipoRecebimento> getTiposRecebimento() {
		if(tiposRecebimento == null){
			tiposRecebimento = listasDAO.getTodasFormasRecebimento();
		}
		return tiposRecebimento;
	}
	public void setDestinos(List<DestinoRecebimento> destinos) {
		this.destinos = destinos;
	}
	public void setTiposRecebimento(List<TipoRecebimento> tiposRecebimento) {
		this.tiposRecebimento = tiposRecebimento;
	}
	public void setRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
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

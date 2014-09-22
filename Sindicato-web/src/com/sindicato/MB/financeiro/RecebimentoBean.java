package com.sindicato.MB.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.tabview.TabView;
import org.primefaces.model.LazyDataModel;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.FinanceiroDAO;
import com.sindicato.controlefinanceiro.dao.ListasDAO;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;
import com.sindicato.controlefinanceiro.entity.Recebimento;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.sindicato.lazyDataModel.LazyDebitoDataModel;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class RecebimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private ListasDAO listasDAO;
	@EJB private FinanceiroDAO financeiroDAO;

	private Recebimento recebimento;
	
	private Debito debitoSelecionado;
	private LazyDataModel<Debito> debitos;
	private List<DestinoRecebimento> destinos;
	private List<TipoRecebimento> tiposRecebimento;

	private TabView tabView;


	public void alterTab(int newTab) {
		tabView.setActiveIndex(newTab);
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
			if(!UtilBean.confereValorRecebimentos(debitoSelecionado)){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
						"Ooops...", "Verifique os valores de recebimento, pois não confere com o valor da Nota");
				return;
			}
			
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

	public LazyDataModel<Debito> getDebitos() {
		if(debitos == null){
			List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
			statusPermitido.add(StatusDebitoEnum.NOTACOBRANCAGERADA);
			debitos = new LazyDebitoDataModel(statusPermitido);
		}
		return debitos;
	}
	public Recebimento getRecebimento() {
		if(recebimento == null){
			recebimento = new Recebimento();
		}
		return recebimento;
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
	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
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
	public void setDebitos(LazyDataModel<Debito> debitos) {
		this.debitos = debitos;
	}

}

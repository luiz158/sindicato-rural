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
import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.Servico;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.lazyDataModel.LazyDebitoDataModel;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class GravarDebitoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private FinanceiroDAO financeiroDAO;
	@EJB
	private ClienteDAO clienteDAO;
	@EJB
	private ServicoDAO servicoDAO;

	private List<Cliente> clientes;
	private List<Servico> servicos;
	private Debito debito;
	private DebitoServico debitoServico;

	private LazyDataModel<Debito> debitos;

	private TabView tabView;

	private InformacaoMensalidade situacaoCliente;

	public void alterTab(int newTab) {
		tabView.setActiveIndex(newTab);
	}

	public void selecionaCliente() {
		situacaoCliente = clienteDAO.estaEmDiaComAsMensalidades(debito
				.getCliente());
	}

	public void reset() {
		debito = new Debito();
		situacaoCliente = null;
	}

	public void novo(){
		this.reset();
		this.alterTab(1);
	}
	
	public void salvarServico() {
		debitoServico.setDebito(debito);
		debito.getDebitoServicos().add(debitoServico);
		debitoServico = null;
	}

	public void removerServico(DebitoServico servico) {
		debito.getDebitoServicos().remove(servico);
	}

	public void salvar() {
		ResultOperation result;
		try {
			result = financeiroDAO.gravarDebito(debito);
			if (result.isSuccess()) {
				this.reset();
				this.alterTab(0);
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
			} else {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
						"Atenção", result.getMessage());
			}
		} catch (Exception e) {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Nota de cobrança não foi criada, contate o administrador");
			e.printStackTrace();
		}
	}

	public void cancelar() {
		try {
			ResultOperation result = financeiroDAO.cancelarNotaDeCobranca(debito);
			if(result.isSuccess()){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Débito " + debito.getId() + " foi cancelado com sucesso");
				this.reset();
				this.alterTab(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}
		
	
	public List<Cliente> getClientes() {
		clientes = clienteDAO.getAll();
		return clientes;
	}

	public Debito getDebito() {
		if (debito == null) {
			debito = new Debito();
		}
		return debito;
	}

	public InformacaoMensalidade getSituacaoCliente() {
		/*
		 * if(situacaoCliente == null){ situacaoCliente = new
		 * InformacaoMensalidade(); }
		 */return situacaoCliente;
	}

	public List<Servico> getServicos() {
		servicos = servicoDAO.getAll();
		return servicos;
	}

	public DebitoServico getDebitoServico() {
		if (debitoServico == null) {
			debitoServico = new DebitoServico();
		}
		return debitoServico;
	}

	public LazyDataModel<Debito> getDebitos() {
		if(debitos == null){
			List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
			statusPermitido.add(StatusDebitoEnum.DEBITOCRIADO);
			debitos = new LazyDebitoDataModel(statusPermitido);
		}
		return debitos;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setDebitos(LazyDataModel<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public void setDebitoServico(DebitoServico debitoServico) {
		this.debitoServico = debitoServico;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public void setSituacaoCliente(InformacaoMensalidade situacaoCliente) {
		this.situacaoCliente = situacaoCliente;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void setDebito(Debito debito) {
		this.debito = debito;
	}

}

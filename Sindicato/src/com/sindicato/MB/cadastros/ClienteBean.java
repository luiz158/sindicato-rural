package com.sindicato.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.tabview.TabView;
import org.primefaces.model.LazyDataModel;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.TipoOcupacaoSoloDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.EstabelecimentoRural;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.entity.OcupacaoSolo;
import com.sindicato.entity.TipoOcupacaoSolo;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.lazyDataModel.LazyClienteDataModel;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.MensalidadePaga;
import com.sindicato.result.ResultOperation;

/**
 * @author Alysson
 * 
 */
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado = UtilBean.getUsuarioLogado();

	@EJB
	private ClienteDAO clienteDAO;
	@EJB
	private TipoOcupacaoSoloDAO tipoOcupacaoSoloDAO;

	private LazyDataModel<Cliente> clientes;
	private Cliente clienteSelecionado;

	private List<InformacaoSocio> informacoesSocio;
	private List<MensalidadePaga> mensalidadesPagas;
	private InformacaoMensalidade informacaoMensalidade;

	private OcupacaoSolo ocupacaoSolo;
	List<TipoOcupacaoSolo> tiposOcupacaoSolo;

	private TabView tabView;
	private Dialog dialogOcupacao;

	@PostConstruct
	public void init() {

	}

	public void selecionaCliente() {
		if (clienteSelecionado.getEstabelecimentoRural() == null) {
			clienteSelecionado
					.setEstabelecimentoRural(new EstabelecimentoRural());
		}
		alterTab(1);
		mensalidadesPagas = clienteDAO
				.getInformacoesMensalidade(clienteSelecionado);
		informacoesSocio = clienteDAO.getInformacoesSocio(clienteSelecionado);
		informacaoMensalidade = clienteDAO
				.estaEmDiaComAsMensalidades(clienteSelecionado);
	}

	public void alterTab(int newTab) {
		tabView.setActiveIndex(newTab);
	}

	public void reset() {
		clienteSelecionado = null;
		informacoesSocio = null;
		informacaoMensalidade = null;
		clientes = null;
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void salvarOcupacao() {
		clienteSelecionado.getEstabelecimentoRural().getOcupacoesSolo()
				.add(ocupacaoSolo);
		ocupacaoSolo = new OcupacaoSolo();
	}

	public void removerOcupacao(OcupacaoSolo ocupacao) {
		clienteSelecionado.getEstabelecimentoRural().getOcupacoesSolo()
				.remove(ocupacao);
	}

	public void salvar() {
		try {
			clienteSelecionado.setEmpresa(usuarioLogado.getEmpresa());
			ResultOperation result = clienteDAO.salvar(clienteSelecionado);
			if(result.isSuccess()){
				this.reset();
				alterTab(0);
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Dados do cliente salvo com sucesso");
			}else{
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
						"Atenção", result.getMessage());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Erro ao salvar alterações. Contate o administrador.");
		}
	}

	public void desativarCliente() {
		try {
			if(informacaoMensalidade.isAtrasado()){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
						"Atenção", "Cliente não pode ser desativado enquanto possuir mensalidades em atraso.");
			} else {
				clienteDAO.desativarCliente(clienteSelecionado);
				this.reset();
				alterTab(0);
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Cliente desativado com sucesso.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Erro ao salvar alterações. Contate o administrador.");
		}
	}

	public Cliente getClienteSelecionado() {
		if (clienteSelecionado == null) {
			clienteSelecionado = new Cliente();
		}
		return clienteSelecionado;
	}

	public List<InformacaoSocio> getInformacoesSocio() {
		return informacoesSocio;
	}

	public InformacaoMensalidade getInformacaoMensalidade() {
		return informacaoMensalidade;
	}

	public OcupacaoSolo getOcupacaoSolo() {
		if (ocupacaoSolo == null) {
			ocupacaoSolo = new OcupacaoSolo();
		}
		return ocupacaoSolo;
	}

	public List<TipoOcupacaoSolo> getTiposOcupacaoSolo() {
		tiposOcupacaoSolo = tipoOcupacaoSoloDAO.getAll();
		return tiposOcupacaoSolo;
	}

	public TabView getTabView() {
		return tabView;
	}

	public Dialog getDialogOcupacao() {
		return dialogOcupacao;
	}

	public List<MensalidadePaga> getMensalidadesPagas() {
		return mensalidadesPagas;
	}

	public LazyDataModel<Cliente> getClientes() {
		if (clientes == null) {
			clientes = new LazyClienteDataModel();
		}
		return clientes;
	}

	public void setClientes(LazyDataModel<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void setDialogOcupacao(Dialog dialogOcupacao) {
		this.dialogOcupacao = dialogOcupacao;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public void setMensalidadesPagas(List<MensalidadePaga> mensalidadesPagas) {
		this.mensalidadesPagas = mensalidadesPagas;
	}

	public void setTiposOcupacaoSolo(List<TipoOcupacaoSolo> tiposOcupacaoSolo) {
		this.tiposOcupacaoSolo = tiposOcupacaoSolo;
	}

	public void setOcupacaoSolo(OcupacaoSolo ocupacaoSolo) {
		this.ocupacaoSolo = ocupacaoSolo;
	}

	public void setInformacoesSocio(List<InformacaoSocio> informacoesSocio) {
		this.informacoesSocio = informacoesSocio;
	}

	public void setInformacaoMensalidade(
			InformacaoMensalidade informacaoMensalidade) {
		this.informacaoMensalidade = informacaoMensalidade;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}

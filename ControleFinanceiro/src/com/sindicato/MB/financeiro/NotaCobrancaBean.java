package com.sindicato.MB.financeiro;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ListasDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class NotaCobrancaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private FinanceiroDAO financeiroDAO;
	private ListasDAO listasDAO;

	private List<Cliente> clientes;
	private List<Debito> debitos;
	private Cliente clienteSelecionado;
	private Debito debitoSelecionado;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		financeiroDAO = new FinanceiroDAOImpl(em);
		listasDAO = new ListasDAOImpl(em);

	}

	public void selecionaCliente() {
		debitos = listasDAO.getDebitosDoClienteNoStatus(clienteSelecionado,
				StatusDebitoEnum.DEBITOCRIADO);
	}

	public void reset() {
		debitoSelecionado = new Debito();
		clienteSelecionado = new Cliente();
	}


	public void gerarNotaDeCobranca() {
		ResultOperation result;
		try {
			UtilBean.addValorSessao("debitoImpressao", debitoSelecionado);
			
			//result = financeiroDAO.gerarNotaDeCobranca(debitoSelecionado);
			//if (result.isSuccess()) {
			if (true) {
				this.reset();
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Nota de cobrança gerada com sucesso");
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

	public List<Cliente> getClientes() {
		clientes = listasDAO
				.getClientesComDebitosNoStatus(StatusDebitoEnum.DEBITOCRIADO);
		return clientes;
	}

	public List<Debito> getDebitos() {
		if (debitos == null) {
			debitos = new ArrayList<Debito>();
		}
		return debitos;
	}

	public Cliente getClienteSelecionado() {
		if (clienteSelecionado == null) {
			clienteSelecionado = new Cliente();
		}
		return clienteSelecionado;
	}

	public Debito getDebitoSelecionado() {
		/*
		 * if(debitoSelecionado == null){ debitoSelecionado = new Debito(); }
		 */
		return debitoSelecionado;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public void setDebitoSelecionado(Debito debitoSelecionado) {
		this.debitoSelecionado = debitoSelecionado;
	}

}

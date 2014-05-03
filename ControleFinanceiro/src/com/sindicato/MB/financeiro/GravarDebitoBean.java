package com.sindicato.MB.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ServicoDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.Servico;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.ResultOperation;

@ManagedBean
@ViewScoped
public class GravarDebitoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private FinanceiroDAO financeiroDAO;
	private ClienteDAO clienteDAO;
	private ServicoDAO servicoDAO;

	private List<Cliente> clientes;
	private List<Servico> servicos;
	private Debito debito;
	private DebitoServico debitoServico;
	
	
	private InformacaoMensalidade situacaoCliente;

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		financeiroDAO = new FinanceiroDAOImpl(em);
		clienteDAO = new ClienteDAOImpl(em);
		servicoDAO = new ServicoDAOImpl(em);
	}

	public void selecionaCliente() {
		situacaoCliente = clienteDAO.estaEmDiaComAsMensalidades(debito
				.getCliente());
	}

	public void reset() {
		debito = new Debito();
	}

	public void salvarServico(){
		debitoServico.setDebito(debito);
		debito.getDebitoServicos().add(debitoServico);
		debitoServico = new DebitoServico();
	}
	
	public void removerServico(DebitoServico servico){
		debito.getDebitoServicos().remove(servico);
	}
	
	public void salvar() {
		ResultOperation result;
		try {
			result = financeiroDAO.gravarDebito(debito);
			if (result.isSuccess()) {
				this.reset();
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Nota de cobrança criada com sucesso");
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
		if(debitoServico == null){
			debitoServico = new DebitoServico();
		}
		return debitoServico;
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

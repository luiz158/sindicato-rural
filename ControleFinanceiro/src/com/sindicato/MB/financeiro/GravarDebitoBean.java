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
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.result.InformacaoMensalidade;

@ManagedBean
@ViewScoped
public class GravarDebitoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private FinanceiroDAO financeiroDAO;
	private ClienteDAO clienteDAO;
	
	private List<Cliente> clientes;
	private Debito debito;
	
	private InformacaoMensalidade situacaoCliente;
	
	@PostConstruct
	public void init(){
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		financeiroDAO = new FinanceiroDAOImpl(em);
		clienteDAO = new ClienteDAOImpl(em);
	}
	
	public void reset(){
		debito = new Debito();
	}
	
	public void salvar(){
		try {
			financeiroDAO.gerarNotaDeCobranca(debito);
			this.reset();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO, "Sucesso", "Nota de cobrança criada com sucesso");
		} catch (Exception e) {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR, "Erro", "Nota de cobrança não foi criada, contate o administrador");
			e.printStackTrace();
		}
	}

	public List<Cliente> getClientes() {
		clientes = clienteDAO.getAll();
		return clientes;
	}

	public Debito getDebito() {
		if(debito == null){
			debito = new Debito();
		}
		return debito;
	}
	public InformacaoMensalidade getSituacaoCliente() {
		if(situacaoCliente == null){
			situacaoCliente = new InformacaoMensalidade();
		}	
		return situacaoCliente;
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

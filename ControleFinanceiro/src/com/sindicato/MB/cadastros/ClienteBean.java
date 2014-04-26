package com.sindicato.MB.cadastros;

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
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.entity.Cliente;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EntityManager em;
	private ClienteDAO clienteDAO;
	
	private List<Cliente> clientes;
	private Cliente clienteSelecionado;
	
	private int indexTab;
	
	@PostConstruct
	public void init(){
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		clienteDAO = new ClienteDAOImpl(em);
	}
	
	public void alterTab(int newTab){
		indexTab = newTab;
	}
	
	public void reset(){
		clienteSelecionado = null;
		alterTab(1);
	}

	public void salvar(){
		try {
			if(clienteSelecionado.getId() == 0){
				clienteDAO.insert(clienteSelecionado);
			}else{
				clienteDAO.update(clienteSelecionado);
			}
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados do cliente salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Cliente> getClientes() {
		clientes = clienteDAO.getAll();
		return clientes;
	}
	public Cliente getClienteSelecionado() {
		if(clienteSelecionado == null){
			clienteSelecionado = new Cliente();
		}
		return clienteSelecionado;
	}
	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
	


}

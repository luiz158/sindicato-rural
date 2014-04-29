package com.sindicato.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.dao.impl.ServicoDAOImpl;
import com.sindicato.entity.Servico;

@ManagedBean
@ViewScoped
public class ServicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private ServicoDAO servicoDAO;

	private Servico servicoSelecionado;
	private List<Servico> servicos;

	private int indexTab;

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		servicoDAO = new ServicoDAOImpl(em);
	}

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		servicoSelecionado = new Servico();
	}

	public void salvar() {
		try {
			if (servicoSelecionado.getId() == 0) {
				servicoDAO.insert(servicoSelecionado);
			} else {
				servicoDAO.update(servicoSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Servico salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Servico getServicoSelecionado() {
		if (servicoSelecionado == null) {
			servicoSelecionado = new Servico();
		}
		return servicoSelecionado;
	}

	public List<Servico> getServicos() {
		servicos = servicoDAO.getAll();
		return servicos;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public void setservicoSelecionado(Servico servicoSelecionado) {
		this.servicoSelecionado = servicoSelecionado;
	}

	public void setServicoSelecionado(Servico servicoSelecionado) {
		this.servicoSelecionado = servicoSelecionado;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}




}

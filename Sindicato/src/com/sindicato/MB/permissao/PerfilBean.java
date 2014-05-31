package com.sindicato.MB.permissao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.ListasDAO;
import com.sindicato.dao.PerfilDAO;
import com.sindicato.dao.impl.ListasDAOImpl;
import com.sindicato.dao.impl.PerfilDAOImpl;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;

@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private PerfilDAO perfilDAO;
	private ListasDAO listasDAO;

	private Perfil perfilSelecionado;
	private List<Perfil> perfis;

	private List<Menu> menusDisponiveis;

	private int indexTab;

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		perfilDAO = new PerfilDAOImpl(em);
		listasDAO = new ListasDAOImpl(em);
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		perfilSelecionado = new Perfil();
	}

	public void salvar() {
		try {
			if (perfilSelecionado.getId() == 0) {
				perfilDAO.insert(perfilSelecionado);
			} else {
				perfilDAO.update(perfilSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Perfil salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Perfil getUsuarioSelecionado() {
		if (perfilSelecionado == null) {
			perfilSelecionado = new Perfil();
		}
		return perfilSelecionado;
	}

	public List<Perfil> getPerfis() {
		perfis = perfilDAO.getAll();
		return perfis;
	}

	public List<Menu> getMenusDisponiveis() {
		menusDisponiveis = listasDAO.getTodosOsMenus();
		return menusDisponiveis;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public Perfil getPerfilSelecionado() {
		if(perfilSelecionado == null){
			perfilSelecionado = new Perfil();
		}
		return perfilSelecionado;
	}

	public void setPerfilSelecionado(Perfil perfilSelecionado) {
		this.perfilSelecionado = perfilSelecionado;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public void setMenusDisponiveis(List<Menu> menusDisponiveis) {
		this.menusDisponiveis = menusDisponiveis;
	}



}

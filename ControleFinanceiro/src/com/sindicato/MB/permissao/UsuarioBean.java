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
import com.sindicato.dao.UsuarioDAO;
import com.sindicato.dao.impl.ListasDAOImpl;
import com.sindicato.dao.impl.UsuarioDAOImpl;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager em;
	private UsuarioDAO usuarioDAO; 
	private ListasDAO listasDAO; 

	private Usuario usuarioSelecionado;
	private List<Usuario> usuarios;

	private List<Perfil> perfisDisponiveis;

	private int indexTab;
	
	@PostConstruct
	public void init(){
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		usuarioDAO = new UsuarioDAOImpl(em);
		listasDAO = new ListasDAOImpl(em);
	}
	
	public void alterTab(int newTab){
		indexTab = newTab;
	}
	
	public void reset(){
		usuarioSelecionado = new Usuario();
	}
	
	public void salvar(){
		
		try {
			if(usuarioSelecionado.getId() == 0){
				usuarioDAO.insert(usuarioSelecionado);
			}else{
				usuarioDAO.update(usuarioSelecionado);
			}
			usuarioSelecionado = new Usuario();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Usuario getUsuarioSelecionado() {
		if(usuarioSelecionado == null){
			usuarioSelecionado = new Usuario();
		}
		return usuarioSelecionado;
	}
	public List<Usuario> getUsuarios() {
		usuarios = usuarioDAO.getAll();
		return usuarios;
	}
	public List<Perfil> getPerfisDisponiveis() {
		perfisDisponiveis = listasDAO.getTodosOsPerfis();
		return perfisDisponiveis;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}
	public void setPerfisDisponiveis(List<Perfil> perfis) {
		this.perfisDisponiveis = perfis;
	}
	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}

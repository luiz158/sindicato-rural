package com.sindicato.MB.permissao;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.UsuarioDAO;
import com.sindicato.dao.impl.UsuarioDAOImpl;
import com.sindicato.entity.autenticacao.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	private UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em); 
	
	
	private Usuario usuarioSelecionado;
	private List<Usuario> usuarios;
	
	
	public void salvar(){
		
		try {
			if(usuarioSelecionado.getId() == 0){
				usuarioDAO.insert(usuarioSelecionado);
			}else{
				usuarioDAO.update(usuarioSelecionado);
			}
			usuarioSelecionado = new Usuario();
			
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
	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}

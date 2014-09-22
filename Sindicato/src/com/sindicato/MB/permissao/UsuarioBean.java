package com.sindicato.MB.permissao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.password.Password;
import org.primefaces.component.tabview.TabView;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.ListasDAO;
import com.sindicato.dao.UsuarioDAO;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.util.PasswordManager;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado = UtilBean.getUsuarioLogado();

	@EJB private UsuarioDAO usuarioDAO;
	@EJB private ListasDAO listasDAO;

	private Usuario usuarioSelecionado;
	private List<Usuario> usuarios;

	private List<Perfil> perfisDisponiveis;

	private TabView tabView;

	private String senhaNova;
	private Password senhaComponent;
	

	public void alterTab(int newTab) {
		tabView.setActiveIndex(newTab);
	}

	public void reset() {
		usuarioSelecionado = new Usuario();
	}

	public void novo() {
		this.reset();
		alterTab(1);
	}

	public void salvar() {
		try {
			if(!senhaComponent.isValid()){
				return;
			}
			
			usuarioSelecionado.setEmpresa(usuarioLogado.getEmpresa());
			if (usuarioSelecionado.getId() == 0) {
				if(senhaNova == null || senhaNova == ""){
					UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
							"Atenção!", "Não esqueça da senha do " + usuarioSelecionado.getNome() + ".");
					senhaComponent.setValid(false);
					return;
				}
				usuarioSelecionado.setSenha(senhaNova);
				usuarioDAO.insert(usuarioSelecionado);
			} else {
				if(senhaNova != null && senhaNova != ""){
					usuarioSelecionado.setSenha(PasswordManager.generated(senhaNova));
				}
				usuarioDAO.update(usuarioSelecionado);
			}
			usuarioSelecionado = new Usuario();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Usuário salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioSelecionado() {
		if (usuarioSelecionado == null) {
			usuarioSelecionado = new Usuario();
		}
		return usuarioSelecionado;
	}

	public List<Usuario> getUsuarios() {
		usuarios = usuarioDAO.getAll();
		return usuarios;
	}

	public List<Perfil> getPerfisDisponiveis() {
		if(perfisDisponiveis == null){
			perfisDisponiveis = listasDAO.getTodosOsPerfis();
		}
		return perfisDisponiveis;
	}

	public String getSenhaNova() {
		return senhaNova;
	}
	public Password getSenhaComponent() {
		return senhaComponent;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public void setSenhaComponent(Password senhaComponent) {
		this.senhaComponent = senhaComponent;
	}
	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
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

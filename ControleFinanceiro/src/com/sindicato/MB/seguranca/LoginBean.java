package com.sindicato.MB.seguranca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.UsuarioDAO;
import com.sindicato.dao.impl.UsuarioDAOImpl;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;


@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	
	public LoginBean(){ }

	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado;
	private String usuario;
	private String senha;
	private MenuModel model;

	private EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	
	
	public String autenticar(){
		
		UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
		usuarioLogado = null;
		String retorno = null;
		ResultOperation result = new ResultOperation();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			result = usuarioDAO.autenticar(usuario, senha);
			
			if(result.isSuccess()){
				usuarioLogado = usuarioDAO.getUsuarioAutenticado();
				
				carregaMenu();

    			retorno = "/faces/index?faces-redirect=true";
			} else {
				FacesMessage mensagem = new FacesMessage(result.getMessage());
				fc.addMessage("", mensagem);
				retorno = "";
			}
		} catch (Exception e) {
			FacesMessage mensagem = new FacesMessage("Falha na autentica��o do usu�rio. Contate o administrador do sistema.");
			fc.addMessage("", mensagem);
			e.printStackTrace();
		}
		usuarioDAO = null;
		
		return retorno;		
	}

	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginBean", null);
		return "/faces/login?faces-redirect=true";
	}
	
	
	private void carregaMenu(){
        
		model = new DefaultMenuModel();
		
		List<Menu> menusPermitidos = carregaMenusPermitidos();
		
		for (Menu menu : menusPermitidos) {
			
			if(menu.getMenuPai() == null || menu.getMenuPai().getId() == 0){
				DefaultSubMenu submenu = new DefaultSubMenu();
				submenu.setLabel(menu.getDescricao());
				submenu.setStyleClass("submenu");
				
				if(getMenusFilho(menu.getId()) != null){
					for (Menu menu01 : getMenusFilho(menu.getId())) {
						
						if(getMenusFilho(menu01.getId()) == null){
							DefaultMenuItem item = new DefaultMenuItem(menu01.getDescricao());
							item.setStyleClass("itemmenu");
							item.setAjax(false);
							item.setUrl(menu01.getUrl());
							submenu.addElement(item);
						}else{
							DefaultSubMenu submenu01 = geraSubmenu(menu01);
							submenu01.setStyleClass("submenu");
							submenu.addElement(submenu01);
						}
					}
				}
				model.addElement(submenu);
			}
		}
	} 

	public DefaultSubMenu geraSubmenu(Menu menu) {
		DefaultSubMenu submenu = new DefaultSubMenu();
		submenu.setLabel(menu.getDescricao());
		submenu.setStyleClass("submenu");
		for (Menu m : getMenusFilho(menu.getId())) {
			if (getMenusFilho(m.getId()) == null) {
				DefaultMenuItem mi = new DefaultMenuItem();
				mi.setValue(m.getDescricao());
				mi.setUrl(m.getUrl());
				mi.setStyleClass("itemmenu");
				
				submenu.addElement(mi);
			} else {
				submenu.addElement(geraSubmenu(m));
			}
		}
		return submenu;
	}	
	
	private List<Menu> carregaMenusPermitidos(){
		List<Menu> menusPermitidos = new ArrayList<Menu>();
		for (Perfil perfil : usuarioLogado.getPerfis()) {
			for (Menu menu : perfil.getMenus()) {
				if(!menusPermitidos.contains(menu))
					menusPermitidos.add(menu);
			}
		}
		return menusPermitidos;
	}	

	private List<Menu> getMenusFilho(int idMenu){
		
		List<Menu> menusPermitidos = carregaMenusPermitidos();
		List<Menu> menusFilho = null;
		
		for (Menu menu : menusPermitidos) {
			if(menu.getMenuPai() != null && menu.getMenuPai().getId() == idMenu){
				if(menusFilho == null) menusFilho = new ArrayList<Menu>();
				menusFilho.add(menu);
			}
		}
		return menusFilho;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public MenuModel getModel() {
		return model;
	}
	public void setModel(MenuModel model) {
		this.model = model;
	}
	public Usuario getUsuarioLogado() {
		if(usuarioLogado == null){
			usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}



}
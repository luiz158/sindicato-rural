package com.sindicato.painelcontrole.MB.seguranca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.painelcontrole.dao.UsuarioDAO;
import com.sindicato.painelcontrole.entity.Acao;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Modulo;
import com.sindicato.painelcontrole.entity.Perfil;
import com.sindicato.painelcontrole.entity.Usuario;
import com.sindicato.result.ResultOperation;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	UsuarioDAO usuarioDAO;
	private Usuario usuarioLogado;
	private String usuario;
	private String senha;
	private MenuModel model;
	private List<Modulo> modulosPermitidos;
	private Modulo moduloSelecionado;
	private int qtdModulos;
	private boolean manterUsuarioLogado;
	
	public String autenticar() {
		usuarioLogado = null;
		String retorno = null;
		ResultOperation result = new ResultOperation();
		try {
			result = usuarioDAO.autenticar(usuario, senha);
			if (result.isSuccess()) {
				usuarioLogado = usuarioDAO.getUsuarioAutenticado();
				if (usuarioLogado.getPerfis().size() == 0) {
					UtilBean.addMessageAndRemoveOthers(
							FacesMessage.SEVERITY_FATAL, "Oops",
							"Usu�rio n�o possui nenhum perfil de acesso");
					return null;
				}
				modulosPermitidos = usuarioDAO
						.extraiModulosPermitidos(usuarioLogado.getPerfis());
				if (modulosPermitidos.size() == 1) {
					moduloSelecionado = modulosPermitidos.get(0);
					carregaMenu();
					retorno = "/faces/index?faces-redirect=true";
				} else {
					RequestContext.getCurrentInstance().execute(
							"PF('modalDialog').show();");
					return null;
				}
			} else {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Aten��o", result.getMessage());
				retorno = "";
			}
		} catch (Exception e) {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
				"Erro",
				"Falha na autentica��o do usu�rio. Contate o administrador do sistema");
			e.printStackTrace();
		}

		return retorno;
	}

	public boolean temPermissaoParaAcao(String identAcao){
		for (Perfil perfil : usuarioLogado.getPerfis()) {
			for (Acao acao : perfil.getAcoes()) {
				if(acao.getIdentificacao().equals(identAcao)){
					return true;
				}
			}
		}
		return false;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("loginBean", null);
		return "/faces/login?faces-redirect=true";
	}

	public String selecionarModulo(Modulo modulo) {
		this.moduloSelecionado = modulo;
		carregaMenu();
		return "/faces/index?faces-redirect=true";
	}

	private void carregaMenu() {

		if (modulosPermitidos == null || modulosPermitidos.size() == 0) {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Nenhum m�dulo foi selecionado");
			return;
		}

		if (moduloSelecionado == null) {
			moduloSelecionado = modulosPermitidos.get(0);
		}

		model = new DefaultMenuModel();
		List<Menu> menusPermitidos = carregaMenusPermitidos();

		for (Menu menu : menusPermitidos) {

			// exibe apenas menus do m�dulo selecionado
			if (moduloSelecionado.equals(menu.getModulo()) == false) {
				continue;
			}

			if (menu.getMenuPai() == null || menu.getMenuPai().getId() == 0) {
				DefaultSubMenu submenu = new DefaultSubMenu();
				submenu.setLabel(menu.getDescricao());
				submenu.setStyleClass("submenu");

				if (getMenusFilho(menu.getId()) != null) {
					for (Menu menu01 : getMenusFilho(menu.getId())) {

						if (getMenusFilho(menu01.getId()) == null) {
							DefaultMenuItem item = new DefaultMenuItem(
									menu01.getDescricao());
							item.setStyleClass("itemmenu");
							item.setAjax(false);
							item.setUrl(menu01.getUrl());
							submenu.addElement(item);
						} else {
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

	private List<Menu> carregaMenusPermitidos() {
		List<Menu> menusPermitidos = new ArrayList<Menu>();
		for (Perfil perfil : usuarioLogado.getPerfis()) {
			for (Menu menu : perfil.getMenus()) {
				if (!menusPermitidos.contains(menu))
					menusPermitidos.add(menu);
			}
		}

		Collections.sort(menusPermitidos, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				Menu p1 = (Menu) o1;
				Menu p2 = (Menu) o2;
				return p1.getOrdem() < p2.getOrdem() ? -1 : (p1.getOrdem() > p2
						.getOrdem() ? +1 : 0);
			}

			@Override
			public Comparator<Object> reversed() {
				return null;
			}

			@Override
			public Comparator<Object> thenComparing(
					Comparator<? super Object> other) {
				return null;
			}

			@Override
			public <U> Comparator<Object> thenComparing(
					Function<? super Object, ? extends U> keyExtractor,
					Comparator<? super U> keyComparator) {
				return null;
			}

			@Override
			public <U extends Comparable<? super U>> Comparator<Object> thenComparing(
					Function<? super Object, ? extends U> keyExtractor) {
				return null;
			}

			@Override
			public Comparator<Object> thenComparingInt(
					ToIntFunction<? super Object> keyExtractor) {
				return null;
			}

			@Override
			public Comparator<Object> thenComparingLong(
					ToLongFunction<? super Object> keyExtractor) {
				return null;
			}

			@Override
			public Comparator<Object> thenComparingDouble(
					ToDoubleFunction<? super Object> keyExtractor) {
				return null;
			}
		});

		return menusPermitidos;
	}

	private List<Menu> getMenusFilho(int idMenu) {

		List<Menu> menusPermitidos = carregaMenusPermitidos();
		List<Menu> menusFilho = null;

		for (Menu menu : menusPermitidos) {
			if (menu.getMenuPai() != null
					&& menu.getMenuPai().getId() == idMenu) {
				if (menusFilho == null)
					menusFilho = new ArrayList<Menu>();
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
		if (usuarioLogado == null) {
			usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public List<Modulo> getModulosPermitidos() {
		if (modulosPermitidos == null) {
			modulosPermitidos = new ArrayList<Modulo>();
		}
		return modulosPermitidos;
	}

	public void setModulosPermitidos(List<Modulo> modulosPermitidos) {
		this.modulosPermitidos = modulosPermitidos;
	}

	public int getQtdModulos() {
		if (modulosPermitidos == null) {
			return 0;
		}
		qtdModulos = modulosPermitidos.size();
		return qtdModulos;
	}


	public boolean isManterUsuarioLogado() {
		return manterUsuarioLogado;
	}

	public void setManterUsuarioLogado(boolean manterUsuarioLogado) {
		this.manterUsuarioLogado = manterUsuarioLogado;
	}
}

package com.sindicato.MB.seguranca;

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

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.MenuDAO;
import com.sindicato.dao.UsuarioDAO;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB UsuarioDAO usuarioDAO;
	@EJB MenuDAO menuDAO;
	private Usuario usuarioLogado;
	private String usuario;
	private String senha;
	private MenuModel model;

	public String autenticar() {

		usuarioLogado = null;
		String retorno = null;
		ResultOperation result = new ResultOperation();

		try {
			result = usuarioDAO.autenticar(usuario, senha);

			if (result.isSuccess()) {
				usuarioLogado = usuarioDAO.getUsuarioAutenticado();

				carregaMenu();

				retorno = "index";
			} else {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Atenção", result.getMessage());
				retorno = "";
			}
		} catch (Exception e) {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Falha na autenticação do usuário. Contate o administrador do sistema");
			e.printStackTrace();
		}
		usuarioDAO = null;

		return retorno;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("loginBean", null);
		return "/faces/login?faces-redirect=true";
	}

	private void carregaMenu() {

		model = new DefaultMenuModel();

		List<Menu> menusPermitidos = carregaMenusPermitidos();

		for (Menu menu : menusPermitidos) {

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
			List<Menu> menus = menuDAO.getMenusPorPerfil(perfil);
			for (Menu menu : menus) {
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

}

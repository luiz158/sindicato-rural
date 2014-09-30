package com.sindicato.painelcontrole.MB.permissao;

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
import javax.faces.bean.ViewScoped;

import org.primefaces.component.tabview.TabView;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.painelcontrole.dao.ListasPCDAO;
import com.sindicato.painelcontrole.dao.MenuDAO;
import com.sindicato.painelcontrole.dao.PerfilDAO;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.painelcontrole.entity.Modulo;
import com.sindicato.painelcontrole.entity.Perfil;

@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private PerfilDAO perfilDAO;
	@EJB private MenuDAO menuDAO;
	@EJB private ListasPCDAO listasDAO;

	private Perfil perfilSelecionado;
	private List<Perfil> perfis;

	private List<Menu> menusDisponiveis;

	private TabView tabView;

	private List<Modulo> modulos;
	private CheckboxTreeNode menuCompleto;
	private TreeNode[] menusSelecionados;
	
	public void novo() {
		this.reset();
		tabView.setActiveIndex(1);
	}

	public void selecionaPerfil() {
		tabView.setActiveIndex(1);
		perfilSelecionado.setMenus(menuDAO.getMenusPorPerfil(perfilSelecionado));
		carregaMenu();
	}

	public void reset() {
		perfilSelecionado = new Perfil();
	}

	public void salvar() {
		try {
			List<Menu> menus = new ArrayList<Menu>();
			for (int i = 0; i < menusSelecionados.length; i++) {
				if(menusSelecionados[i].getType().equals("modulo"))
					continue;
				
				Menu menu = (Menu) menusSelecionados[i].getData();
				menus.add(menu);
			}
			
			perfilSelecionado.setMenus(menus);
			
			if (perfilSelecionado.getId() == 0) {
				perfilDAO.insert(perfilSelecionado);
			} else {
				perfilDAO.update(perfilSelecionado);
			}
			this.reset();
			tabView.setActiveIndex(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Perfil salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
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
		for (Menu menu : perfilSelecionado.getMenus()) {
			if (!menusPermitidos.contains(menu))
				menusPermitidos.add(menu);
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

		List<Menu> menusPermitidos = getMenusDisponiveis();
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

	private void carregaMenu() {

		List<Menu> menusDisponiveis = getMenusDisponiveis();
		List<Menu> menusPermitidos = carregaMenusPermitidos();

		menuCompleto = new CheckboxTreeNode();
		
		for (Modulo modulo : getModulos()) {
			
			CheckboxTreeNode menuModulo = new CheckboxTreeNode("modulo", modulo.getDescricao(), menuCompleto);
			//menuModulo.setSelectable(false);
			
			
			for (Menu menu : menusDisponiveis) {

				if(menu.getModulo().getId() != modulo.getId()){
					continue;
				}
				
				if (menu.getMenuPai() == null || menu.getMenuPai().getId() == 0) {
					TreeNode submenu = new CheckboxTreeNode("submenu", menu, menuModulo);
					submenu.setExpanded(true);
					
					if(menusPermitidos.contains(menu)){
						submenu.setSelected(true);
					}

					if (getMenusFilho(menu.getId()) != null) {
						for (Menu menu01 : getMenusFilho(menu.getId())) {
							
							if (getMenusFilho(menu01.getId()) == null) {
								new CheckboxTreeNode("itemmenu", menu01, submenu);
								
								if(menusPermitidos.contains(menu01)){
									submenu.setSelected(true);
								}
							} else {
								TreeNode item = new CheckboxTreeNode("submenu", menu01, submenu);
								item.setExpanded(true);
								if(menusPermitidos.contains(menu01)){
									submenu.setSelected(true);
								}
							}
						}
					}
				}
			}
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
		if(menusDisponiveis == null){
			menusDisponiveis = listasDAO.getTodosOsMenus();
		}
		return menusDisponiveis;
	}

	public Perfil getPerfilSelecionado() {
		if(perfilSelecionado == null){
			perfilSelecionado = new Perfil();
		}
		return perfilSelecionado;
	}
	public TabView getTabView() {
		return tabView;
	}

	public List<Modulo> getModulos() {
		if(modulos == null){
			modulos = listasDAO.getTodosModulos();
		}
		return modulos;
	}

	public CheckboxTreeNode getMenuCompleto() {
		return menuCompleto;
	}

	public TreeNode[] getMenusSelecionados() {
		return menusSelecionados;
	}

	public void setMenuCompleto(CheckboxTreeNode menuCompleto) {
		this.menuCompleto = menuCompleto;
	}

	public void setMenusSelecionados(TreeNode[] menusSelecionados) {
		this.menusSelecionados = menusSelecionados;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
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

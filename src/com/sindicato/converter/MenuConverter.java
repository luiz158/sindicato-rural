package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.MenuDAO;
import com.sindicato.dao.impl.MenuDAOImpl;
import com.sindicato.entity.autenticacao.Menu;

@FacesConverter(value = "MenuConverter")
public class MenuConverter implements Converter {

	private EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	private MenuDAO menuDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Menu menu = null;
		if (id.isEmpty()) {
			return menu;
		}
		try {
			menuDAO = new MenuDAOImpl(em);
			menu = menuDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menu;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Menu) {
			Menu menu = (Menu) obj;
			return String.valueOf(menu.getId());
		}
		return null;
	}

}

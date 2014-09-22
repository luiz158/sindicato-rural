package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.painelcontrole.dao.MenuDAO;
import com.sindicato.painelcontrole.entity.Menu;
import com.sindicato.util.Constantes;

@FacesConverter(value = "MenuConverter")
public class MenuConverter implements Converter {

	private MenuDAO menuDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Menu menu = null;
		if (id.isEmpty()) {
			return menu;
		}
		try {
			menuDAO = (MenuDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/MenuDAOImpl");
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

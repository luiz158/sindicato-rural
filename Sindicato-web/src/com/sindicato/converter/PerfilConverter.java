package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.painelcontrole.dao.PerfilDAO;
import com.sindicato.painelcontrole.entity.Perfil;
import com.sindicato.util.Constantes;

@FacesConverter(value = "PerfilConverter")
public class PerfilConverter implements Converter {

	private PerfilDAO perfilDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Perfil perfil = null;
		if (id.isEmpty()) {
			return perfil;
		}
		try {
			perfilDAO = (PerfilDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/PerfilDAOImpl");
			perfil = perfilDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return perfil;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Perfil) {
			Perfil perfil = (Perfil) obj;
			return String.valueOf(perfil.getId());
		}
		return null;
	}

}

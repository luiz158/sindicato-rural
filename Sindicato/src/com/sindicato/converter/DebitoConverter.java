package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.dao.DebitoDAO;
import com.sindicato.dao.impl.DebitoDAOImpl;
import com.sindicato.entity.Debito;

@FacesConverter(value = "DebitoConverter")
public class DebitoConverter implements Converter {

	private DebitoDAO debitoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Debito debito = null;
		if (id.isEmpty() || id.equals("Selecione a data base...")) {
			return debito;
		}
		try {
			debitoDAO = new DebitoDAOImpl();
			debito = debitoDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return debito;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Debito) {
			Debito debito = (Debito) obj;
			return String.valueOf(debito.getId());
		}
		return null;
	}

}

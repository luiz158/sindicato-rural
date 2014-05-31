package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.dao.impl.ModoPagamentoDAOImpl;
import com.sindicato.entity.ModoPagamento;

@FacesConverter(value = "ModoPagamentoConverter")
public class ModoPagamentoConverter implements Converter {

	private ModoPagamentoDAO modoPagamentoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		ModoPagamento modo = null;
		if (id.isEmpty()) {
			return modo;
		}
		try {
			modoPagamentoDAO = new ModoPagamentoDAOImpl();
			modo = modoPagamentoDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof ModoPagamento) {
			ModoPagamento modo = (ModoPagamento) obj;
			return String.valueOf(modo.getId());
		}
		return null;
	}

}

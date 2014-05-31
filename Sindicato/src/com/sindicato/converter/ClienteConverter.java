package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.entity.Cliente;

@FacesConverter(value = "ClienteConverter")
public class ClienteConverter implements Converter {

	private ClienteDAO clienteDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Cliente cliente = null;
		if (id.isEmpty() || id.equals("Selecione o cliente...")) {
			return cliente;
		}
		try {
			clienteDAO = new ClienteDAOImpl();
			cliente = clienteDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Cliente) {
			Cliente cliente = (Cliente) obj;
			return String.valueOf(cliente.getId());
		}
		return null;
	}

}

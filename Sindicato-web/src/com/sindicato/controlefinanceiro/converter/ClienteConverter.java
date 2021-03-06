package com.sindicato.controlefinanceiro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.ClienteDAO;
import com.sindicato.controlefinanceiro.entity.Cliente;
import com.sindicato.util.Constantes;

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
			clienteDAO = (ClienteDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/ClienteDAOImpl");
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

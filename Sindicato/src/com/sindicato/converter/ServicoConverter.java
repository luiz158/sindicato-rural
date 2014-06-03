package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.entity.Servico;

@FacesConverter(value = "ServicoConverter")
public class ServicoConverter implements Converter {

	private ServicoDAO servicoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Servico servico = null;
		if (id.isEmpty()) {
			return servico;
		}
		try {
			servicoDAO = (ServicoDAO) UtilBean.getClassLookup("ControleFinanceiro/ServicoDAOImpl");
			servico = servicoDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return servico;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Servico) {
			Servico servico = (Servico) obj;
			return String.valueOf(servico.getId());
		}
		return null;
	}

}

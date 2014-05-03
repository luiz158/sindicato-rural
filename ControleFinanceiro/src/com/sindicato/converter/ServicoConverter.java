package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.dao.impl.ServicoDAOImpl;
import com.sindicato.entity.Servico;

@FacesConverter(value = "ServicoConverter")
public class ServicoConverter implements Converter {

	private EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	private ServicoDAO servicoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Servico servico = null;
		if (id.isEmpty()) {
			return servico;
		}
		try {
			servicoDAO = new ServicoDAOImpl(em);
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

package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.RecebimentoDAO;
import com.sindicato.dao.impl.RecebimentoDAOImpl;
import com.sindicato.entity.DestinoRecebimento;

@FacesConverter(value = "DestinoRecebimentoConverter")
public class DestinoRecebimentoConverter implements Converter {

	private EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	private RecebimentoDAO recebimentoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		DestinoRecebimento recebimento = null;
		if (id.isEmpty()) {
			return recebimento;
		}
		try {
			recebimentoDAO = new RecebimentoDAOImpl(em);
			recebimento = recebimentoDAO.getDestinoRecebimentoById(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recebimento;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof DestinoRecebimento) {
			DestinoRecebimento recebimento = (DestinoRecebimento) obj;
			return String.valueOf(recebimento.getId());
		}
		return null;
	}

}
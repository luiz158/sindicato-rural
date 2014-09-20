package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.RecebimentoDAO;
import com.sindicato.dao.impl.RecebimentoDAOImpl;
import com.sindicato.entity.TipoRecebimento;

@FacesConverter(value = "TipoRecebimentoConverter")
public class TipoRecebimentoConverter implements Converter {

	private EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	private RecebimentoDAO recebimentoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		TipoRecebimento tipo = null;
		if (id.isEmpty()) {
			return tipo;
		}
		try {
			recebimentoDAO = new RecebimentoDAOImpl(em);
			tipo = recebimentoDAO.getTipoRecebimentoById(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof TipoRecebimento) {
			TipoRecebimento tipo = (TipoRecebimento) obj;
			return String.valueOf(tipo.getId());
		}
		return null;
	}

}

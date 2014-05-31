package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.dao.TipoOcupacaoSoloDAO;
import com.sindicato.dao.impl.TipoOcupacaoSoloDAOImpl;
import com.sindicato.entity.TipoOcupacaoSolo;

@FacesConverter(value = "TipoOcupacaoSoloConverter")
public class TipoOcupacaoSoloConverter implements Converter {

	private TipoOcupacaoSoloDAO tipoOcupacaoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		TipoOcupacaoSolo ocupacao = null;
		if (id.isEmpty()) {
			return ocupacao;
		}
		try {
			tipoOcupacaoDAO = new TipoOcupacaoSoloDAOImpl();
			ocupacao = tipoOcupacaoDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ocupacao;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof TipoOcupacaoSolo) {
			TipoOcupacaoSolo ocupacao = (TipoOcupacaoSolo) obj;
			return String.valueOf(ocupacao.getId());
		}
		return null;
	}

}

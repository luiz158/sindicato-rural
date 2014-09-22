package com.sindicato.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.TipoOcupacaoSoloDAO;
import com.sindicato.controlefinanceiro.entity.TipoOcupacaoSolo;
import com.sindicato.util.Constantes;

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
			tipoOcupacaoDAO = (TipoOcupacaoSoloDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/TipoOcupacaoSoloDAOImpl");
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

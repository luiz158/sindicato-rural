package com.sindicato.controlefinanceiro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.RecebimentoDAO;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;
import com.sindicato.util.Constantes;

@FacesConverter(value = "TipoRecebimentoConverter")
public class TipoRecebimentoConverter implements Converter {

	private RecebimentoDAO recebimentoDAO;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		TipoRecebimento tipo = null;
		if (id.isEmpty()) {
			return tipo;
		}
		try {
			recebimentoDAO = (RecebimentoDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/RecebimentoDAOImpl");
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

package com.sindicato.contas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.BancoDAO;
import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.util.Constantes;

@FacesConverter(value = "BancoConverter")
public class BancoConverter implements Converter {

	private BancoDAO bancoDAO;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Banco banco = null;
		if (id.isEmpty() || id.equals("Selecione o banco...")) {
			return banco;
		}
		try {
			bancoDAO = (BancoDAO) UtilBean
					.getClassLookup(Constantes.NOME_PROJETO + "/BancoDAOImpl");
			banco = bancoDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return banco;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Banco) {
			Banco banco = (Banco) obj;
			return String.valueOf(banco.getId());
		}
		return null;
	}

}

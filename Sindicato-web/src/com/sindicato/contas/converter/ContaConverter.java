package com.sindicato.contas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.contasapagar.dao.ContaDAO;
import com.sindicato.contasapagar.entity.Conta;
import com.sindicato.util.Constantes;

@FacesConverter(value = "ContaConverter")
public class ContaConverter implements Converter {

	private ContaDAO contaDAO;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		Conta conta = null;
		if (id.isEmpty() || id.equals("Selecione a conta...")) {
			return conta;
		}
		try {
			contaDAO = (ContaDAO) UtilBean
					.getClassLookup(Constantes.NOME_PROJETO + "/ContaDAOImpl");
			conta = contaDAO.searchByID(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conta;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj != null && obj instanceof Conta) {
			Conta conta = (Conta) obj;
			return String.valueOf(conta.getId());
		}
		return null;
	}

}

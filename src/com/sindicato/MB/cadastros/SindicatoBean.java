package com.sindicato.MB.cadastros;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.EmpresaDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.impl.EmpresaDAOImpl;
import com.sindicato.entity.Empresa;

@ManagedBean
@ViewScoped
public class SindicatoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EntityManager em;
	private EmpresaDAO empresaDAO;
	
	private Empresa empresa;
	
	@PostConstruct
	public void init(){
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		empresaDAO = new EmpresaDAOImpl(em);
		
		try {
			empresa = empresaDAO.getAll().get(0);
		} catch (Exception e) {
			empresa = new Empresa();
		}
	}

	public void salvar(){
		try {
			if(empresa.getId() == 0){
				empresaDAO.insert(empresa);
			}else{
				empresaDAO.update(empresa);
			}
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO, "Sucesso", "Alterações salvas com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Empresa getEmpresa() {
		if(empresa == null){
			empresa = new Empresa();
		}
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}

package com.sindicato.test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.sindicato.dao.EmpresaDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.impl.EmpresaDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Empresa;
import com.sindicato.entity.EstabelecimentoRural;

public class Cadastros {

	private EntityManager em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();

	private EmpresaDAO empresaDAO = new EmpresaDAOImpl(em);

	private Empresa preencheEmpresa() {
		Empresa emp = new Empresa();
		emp.setRazaoSocial("Sindicato Rural Cotia");
		return emp;
	}
	@Test
	public void cadastrarEmpresa() {
		Empresa emp = this.preencheEmpresa();
		empresaDAO.insert(emp);
		assertEquals(empresaDAO.getAll().size(), 1);
		empresaDAO.remove(emp);
	}

	private Cliente preencherCliente(Empresa emp,
			EstabelecimentoRural estabRural) {
		Cliente cli = new Cliente();
		cli.setEstabelecimentoRural(estabRural);
		cli.setEmpresa(emp);
		cli.setDataNascimento(Calendar.getInstance());

		return cli;
	}
	private EstabelecimentoRural preencherEstabRural() {
		EstabelecimentoRural estabRural = new EstabelecimentoRural();
		return estabRural;
	}
	@Test
	public void cadastrarCliente() {
		Cliente cli = this.preencherCliente(this.preencheEmpresa(),
				this.preencherEstabRural());
		
		
		

	}

}

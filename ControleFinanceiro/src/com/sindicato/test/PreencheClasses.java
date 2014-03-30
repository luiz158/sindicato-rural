package com.sindicato.test;

import java.util.Calendar;

import javax.persistence.EntityManager;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EmpresaDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.EmpresaDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Empresa;
import com.sindicato.entity.EstabelecimentoRural;

public class PreencheClasses {

	public static void populaBanco(EntityManager em) {
		Empresa emp = new Empresa();
		emp.setRazaoSocial("Sindicato Rural Cotia");
		EmpresaDAO empDAO = new EmpresaDAOImpl(em);
		empDAO.insert(emp);

		Cliente cli = new Cliente();
		cli.setEmpresa(emp);
		cli.setEstabelecimentoRural(new EstabelecimentoRural());
		cli.setNome("Alysson Rodrigues");
		cli.setDataNascimento(Calendar.getInstance());
		cli.setProdutorRuralDesde(Calendar.getInstance());
		cli.setTelefone("(11) 46141760");
		cli.setSocio(true);
		ClienteDAO cliDAO = new ClienteDAOImpl(em);
		cliDAO.insert(cli);
	}

}

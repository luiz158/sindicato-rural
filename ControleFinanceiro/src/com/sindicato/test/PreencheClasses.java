package com.sindicato.test;

import java.util.Calendar;

import javax.persistence.EntityManager;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EmpresaDAO;
import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.dao.TipoOcupacaoSoloDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.EmpresaDAOImpl;
import com.sindicato.dao.impl.ModoPagamentoDAOImpl;
import com.sindicato.dao.impl.ServicoDAOImpl;
import com.sindicato.dao.impl.TipoOcupacaoSoloDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Empresa;
import com.sindicato.entity.EstabelecimentoRural;
import com.sindicato.entity.ModoPagamento;
import com.sindicato.entity.OcupacaoSolo;
import com.sindicato.entity.Servico;
import com.sindicato.entity.TipoOcupacaoSolo;

public class PreencheClasses {

	public static void populaBanco(EntityManager em) {
		Empresa emp = new Empresa();
		emp.setRazaoSocial("Sindicato Rural Cotia");
		EmpresaDAO empDAO = new EmpresaDAOImpl(em);
		empDAO.insert(emp);

		Cliente cli = new Cliente();
		cli.setEmpresa(emp);
		
		EstabelecimentoRural estabRural = new EstabelecimentoRural();
		estabRural.setCnpj("54654654564");
		
		TipoOcupacaoSoloDAO tipoOcupSoloDAO = new TipoOcupacaoSoloDAOImpl(em);
		TipoOcupacaoSolo tipoOcupSolo = new TipoOcupacaoSolo();
		tipoOcupSolo.setDescricao("Hortaliças e Legumes");
		tipoOcupSoloDAO.insert(tipoOcupSolo);
		
		OcupacaoSolo ocup = new OcupacaoSolo();
		ocup.setTipoOcupacaoSolo(tipoOcupSoloDAO.getAll().get(0));
		ocup.setAreaOcupada(100);
		estabRural.getOcupacoesSolo().add(ocup);
		ocup = new OcupacaoSolo();
		ocup.setTipoOcupacaoSolo(tipoOcupSoloDAO.getAll().get(0));
		ocup.setAreaOcupada(150);
		estabRural.getOcupacoesSolo().add(ocup);
		
		cli.setEstabelecimentoRural(estabRural);
		
		Calendar dtNasc = Calendar.getInstance();
		dtNasc.set(Calendar.YEAR, 2000);
		cli.setDataNascimento(dtNasc);
		
		Calendar prodRural = Calendar.getInstance();
		prodRural.set(Calendar.YEAR, 2010);
		cli.setProdutorRuralDesde(prodRural);

		Calendar dataCadastro = Calendar.getInstance();
		dataCadastro.set(Calendar.YEAR, 2013);
		cli.setDataCadastro(dataCadastro);
		
		cli.setNome("Alysson Rodrigues");
		cli.setCpf("370754654");
		cli.setTelefone("(11) 46141760");
		ClienteDAO cliDAO = new ClienteDAOImpl(em);
		cliDAO.insert(cli);
		
		ServicoDAO servicoDAO = new ServicoDAOImpl(em); 
		Servico servico = new Servico();
		servico.setDescricao("INSS");
		servico.setRetencao(true);
		servicoDAO.insert(servico);
		
		ModoPagamentoDAO modoPagamDAO = new ModoPagamentoDAOImpl(em);
		ModoPagamento modoPagam = new ModoPagamento();
		modoPagam.setDescricao("Debito Banco do Brasil");
		modoPagamDAO.insert(modoPagam);
		
		modoPagam = new ModoPagamento();
		modoPagam.setDescricao("Debito Bradesco");
		modoPagamDAO.insert(modoPagam);
	}

}

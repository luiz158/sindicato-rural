package com.sindicato.test;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.dao.impl.ClienteDAOImpl;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ServicoDAOImpl;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.entity.Servico;
import com.sindicato.result.InformacaoMensalidade;

public class TestCadastros {

	private EntityManager em = EntityManagerFactorySingleton.getInstance()
			.createEntityManager();

	private ClienteDAO clienteDAO = new ClienteDAOImpl(em);
	private ServicoDAO servicoDAO = new ServicoDAOImpl(em);
	private FinanceiroDAO financeiroDAO = new FinanceiroDAOImpl(em);

	@BeforeClass
	public static void popula() {
		PreencheClasses.populaBanco(EntityManagerFactorySingleton.getInstance()
				.createEntityManager());
	}

	@Test
	public void pegarUltimoRegistroDeSocio() {
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(cliente.isSocio(),
				false);

		cliente.setSocio(true);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.searchByID(cliente.getId()).isSocio(),
				true);

		cliente.setSocio(false);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.searchByID(cliente.getId()).isSocio(),
				false);

		cliente.setSocio(true);
		clienteDAO.update(cliente);
		Assert.assertEquals(clienteDAO.searchByID(cliente.getId()).isSocio(),
				true);
		
		Assert.assertEquals(clienteDAO.getAll().get(0).isSocio(), true);
	}

	@Test
	public void cadastraERecuperacaoOcupacaoSolo() {
		Cliente cliente = clienteDAO.getAll().get(0);
		Assert.assertEquals(cliente.getEstabelecimentoRural()
				.getOcupacoesSolo().size(), 2);
	}
	
	@Test
	public void calculaQuantosMesesOClienteESocio(){
		
		em.getTransaction().begin();
		em.createQuery("DELETE FROM InformacaoSocio").executeUpdate();
		em.getTransaction().commit();
		
		Cliente cliente = clienteDAO.searchByID(1);
		
		em.getTransaction().begin();
		
		// vira socio à um ano atras
		Calendar dataEvento = Calendar.getInstance();
		dataEvento.set(Calendar.YEAR, 2013);
		InformacaoSocio infSocio = new InformacaoSocio();
		infSocio.setCliente(cliente);
		infSocio.setDataEvento(dataEvento);
		infSocio.setSocio(true);
		
		em.persist(infSocio);
		em.getTransaction().commit();
		em.getTransaction().begin();

		Assert.assertEquals(clienteDAO.calculaQuantosMesesOClienteESocio(cliente), 12);

		dataEvento = Calendar.getInstance();

		// deixa de ser sócio
		infSocio = new InformacaoSocio();
		infSocio.setCliente(cliente);
		infSocio.setDataEvento(dataEvento);
		infSocio.setSocio(false);
		
		em.persist(infSocio);
		
		em.getTransaction().commit();
		
		Assert.assertEquals(clienteDAO.calculaQuantosMesesOClienteESocio(cliente), 12);
		
	}

	@Test
	public void calculaQuantasMensalidadeForamPagas(){
		
		Cliente cliente = clienteDAO.searchByID(1);
		
		// cadastra servico de mensalidade
		Servico bimestral = new Servico();
		bimestral.setDescricao("Taxa de serviço - Bimestral");
		bimestral.setMensalidade(true);
		bimestral.setQuantosMesesVale(2);
		bimestral.setRetencao(true);
		servicoDAO.insert(bimestral);

		// cadastra servico de mensalidade
		Servico trimestral = new Servico();
		trimestral.setDescricao("Taxa de serviço - Trimestral");
		trimestral.setMensalidade(true);
		trimestral.setQuantosMesesVale(3);
		trimestral.setRetencao(true);
		servicoDAO.insert(trimestral);

		DebitoServico debitoServico = new DebitoServico();
		debitoServico.setServico(bimestral);
		debitoServico.setValor(BigDecimal.TEN);
		
		Debito debito = new Debito();
		debito.setCliente(cliente);

		debitoServico.setDebito(debito);
		debito.getDebitoServicos().add(debitoServico);

		financeiroDAO.gravarDebito(debito);
		financeiroDAO.registrarRecebimento(debito);

		Assert.assertEquals(clienteDAO.calculaQuantasMensalidadeForamPagas(cliente), 2);
		
		// cadastra novo servico
		debitoServico = new DebitoServico();
		debitoServico.setServico(trimestral);
		debitoServico.setValor(BigDecimal.TEN);
		
		Calendar dataBase = Calendar.getInstance();
		dataBase.set(Calendar.YEAR, 2012);
		
		debito = new Debito();
		debito.setCliente(cliente);
		debito.setDataBase(dataBase);

		debitoServico.setDebito(debito);
		debito.getDebitoServicos().add(debitoServico);
		
		financeiroDAO.gravarDebito(debito);
		financeiroDAO.registrarRecolhimentos(debito);
		
		
		Assert.assertEquals(clienteDAO.calculaQuantasMensalidadeForamPagas(cliente), 5);
		
	}
	
	@Test
	public void estaEmDiaComAsMensalidades(){

		Cliente cliente = clienteDAO.searchByID(1);
		InformacaoMensalidade infMensalidade = clienteDAO.estaEmDiaComAsMensalidades(cliente);
		
//		Assert.assertEquals(infMensalidade.isAtrasado(), true);
		
	}
}

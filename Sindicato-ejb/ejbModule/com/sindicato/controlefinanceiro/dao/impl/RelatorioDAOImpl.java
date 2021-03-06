package com.sindicato.controlefinanceiro.dao.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.sindicato.controlefinanceiro.dao.ClienteDAO;
import com.sindicato.controlefinanceiro.dao.DestinoRecebimentoDAO;
import com.sindicato.controlefinanceiro.dao.RelatorioDAO;
import com.sindicato.controlefinanceiro.entity.Cliente;
import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;
import com.sindicato.controlefinanceiro.entity.Servico;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.sindicato.controlefinanceiro.report.model.ClienteRecolhimentosAberto;
import com.sindicato.controlefinanceiro.report.model.ClienteRetencoesRecolher;
import com.sindicato.controlefinanceiro.report.model.DetalheInscricaoEstadual;
import com.sindicato.controlefinanceiro.report.model.DetalheNotasEmitidas;
import com.sindicato.controlefinanceiro.report.model.DetalhesAssociado;
import com.sindicato.controlefinanceiro.report.model.DetalhesClienteRecolhimentos;
import com.sindicato.controlefinanceiro.report.model.DetalhesDestinoRecebimento;
import com.sindicato.controlefinanceiro.report.model.DetalhesNotaRecolhimentosAberto;
import com.sindicato.controlefinanceiro.report.model.DetalhesServico;
import com.sindicato.controlefinanceiro.report.model.DetalhesServicosRecolhimentos;
import com.sindicato.controlefinanceiro.report.model.FiltroAssociados;
import com.sindicato.controlefinanceiro.report.model.NotasEmitidasDia;
import com.sindicato.controlefinanceiro.report.model.RecebimentoDia;
import com.sindicato.controlefinanceiro.report.model.RelatorioAssociados;
import com.sindicato.controlefinanceiro.report.model.RelatorioFolhaVotacao;
import com.sindicato.controlefinanceiro.report.model.RelatorioInscricaoEstadual;
import com.sindicato.controlefinanceiro.report.model.RelatorioNotasEmitidas;
import com.sindicato.controlefinanceiro.report.model.RelatorioRecolhimentosAberto;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoRecebimentos;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoRecolhimentos;
import com.sindicato.controlefinanceiro.report.model.RelatorioResumoServico;
import com.sindicato.controlefinanceiro.report.model.RelatorioRetencoesRecolher;
import com.sindicato.controlefinanceiro.report.model.ServicoRecolhimentosAberto;
import com.sindicato.controlefinanceiro.report.model.ServicoRetencoesRecolher;
import com.sindicato.report.util.FiltroBooleanEnum;
import com.sindicato.result.InformacaoMensalidade;
import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

@Stateful
public class RelatorioDAOImpl implements RelatorioDAO {
	@PersistenceContext(name = "ControleFinanceiro")
	private EntityManager em;
	@EJB
	private ClienteDAO clienteDAO;
	@EJB
	private DestinoRecebimentoDAO destinoDAO;
	SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public RelatorioAssociados getAssociados(FiltroAssociados filtro) {
		RelatorioAssociados relatorio = new RelatorioAssociados();
		relatorio.setFiltro(filtro);
		
		EasyCriteria<Cliente> criteria = EasyCriteriaFactory.createQueryCriteria(em, Cliente.class);
		criteria.innerJoin("informacoesSocio");
		criteria.setDistinctTrue();
		
		if(filtro.getNome() != null && filtro.getNome() != ""){
			criteria.andStringLike(true, "nome", "%" + filtro.getNome() + "%");
		}
		
		if(filtro.getMatricula() != 0){
			criteria.andEquals("id", filtro.getMatricula());
		}

		if(filtro.getClientesAtivos().equals(FiltroBooleanEnum.NAO)){
			criteria.andEquals("ativo", false);
		}
		if(filtro.getClientesAtivos().equals(FiltroBooleanEnum.SIM)){
			criteria.andEquals("ativo", true);
		}
		
		List<Cliente> todosClientes = criteria.getResultList();
		List<Cliente> clientesFiltrados = new ArrayList<Cliente>();
		
		// carrega informa��es de mensalidades
		for (Cliente cliente : todosClientes) {
			cliente.setInformacaoMensalidade(clienteDAO.estaEmDiaComAsMensalidades(cliente));
			
			if(filtro.getPendentes().equals(FiltroBooleanEnum.TODOS)){
				clientesFiltrados.add(cliente);
			} else if(cliente.getInformacaoMensalidade().isAtrasado() && filtro.getPendentes().equals(FiltroBooleanEnum.SIM)){
				clientesFiltrados.add(cliente);
			} else if(!cliente.getInformacaoMensalidade().isAtrasado() && filtro.getPendentes().equals(FiltroBooleanEnum.NAO)){
				clientesFiltrados.add(cliente);
			}
		}
		
		for (Cliente cliente : clientesFiltrados) {
			if(cliente.isSocio() && cliente.isAtivo()){
				relatorio.addTotalAssociados();
			}
			
			Calendar dataSocio = cliente.getDataCadastro();
			
			DetalhesAssociado detalhes = new DetalhesAssociado();
			if (cliente.getInformacaoMensalidade().isAtrasado()) {
				detalhes.setStatusAssociado("Com pend�ncia");
				relatorio.addTotalAssociadosEmAtraso();
				
				if(cliente.isAtivo() == false){
					relatorio.addTotalClientesDesativadosComDebito();
				}
			} else {
				detalhes.setStatusAssociado("Sem pend�ncia");
				
				if(cliente.isAtivo()){
					relatorio.addTotalAssociadosEmDia();
				}
			}
			detalhes.setSituacao(cliente.getInformacaoMensalidade().getMensagem());
			detalhes.setMatricula(cliente.getId());
			detalhes.setNome(cliente.getNome());
			detalhes.setObservacao(cliente.getObservacao());
			detalhes.setTelefone(cliente.getTelefone());
			if (cliente.getProdutorRuralDesde() != null)
				detalhes.setProdutorRuralDesde(formatData.format(cliente
						.getProdutorRuralDesde().getTime()));
			if (dataSocio != null)
				detalhes.setSocioDesde(formatData.format(dataSocio.getTime()));
			relatorio.getDetalhesAssociado().add(detalhes);
		}
		
		return relatorio;
	}

	private List<StatusDebitoEnum> getStatusPermitidosResumoServicos() {
		List<StatusDebitoEnum> statusPermitidos = new ArrayList<StatusDebitoEnum>();
		statusPermitidos.add(StatusDebitoEnum.NOTACOBRANCAGERADA);
		statusPermitidos.add(StatusDebitoEnum.RECEBIDO);
		statusPermitidos.add(StatusDebitoEnum.RECOLHIDO);
		return statusPermitidos;
	}

	private String getJPQLResumoServicos() {
		return "select ds.servico.descricao, SUM(ds.valor) from DebitoServico ds "
				+ " where ds.debito.dataEmissaoNotaCobranca BETWEEN :dataDe and :dataAte "
				+ " and ds.debito.cliente.socio = :socio "
				+ "	and ds.servico.retencao = :retencao "
				+ " and ds.debito.status in (:status) "
				+ " group by ds.servico.descricao "
				+ " order by ds.servico.descricao ";
	}

	@Override
	public RelatorioResumoServico getResumoServico(Calendar dataDe,
			Calendar dataAte) {
		RelatorioResumoServico relResumoServico = new RelatorioResumoServico();
		relResumoServico.setRetencoesSocios(this.getValoresServicosPeriodo(
				dataDe, dataAte, true, true));
		relResumoServico.setRetencoesNaoSocios(this.getValoresServicosPeriodo(
				dataDe, dataAte, false, true));
		relResumoServico.setReceitasSocios(this.getValoresServicosPeriodo(
				dataDe, dataAte, true, false));
		relResumoServico.setReceitasNaoSocios(this.getValoresServicosPeriodo(
				dataDe, dataAte, false, false));
		return relResumoServico;
	}

	private List<DetalhesServico> getValoresServicosPeriodo(Calendar dataDe,
			Calendar dataAte, boolean socio, boolean retencao) {
		TypedQuery<Object[]> query = em.createQuery(
				this.getJPQLResumoServicos(), Object[].class);
		query.setParameter("dataDe", dataDe);
		query.setParameter("dataAte", dataAte);
		query.setParameter("socio", socio);
		query.setParameter("retencao", retencao);
		query.setParameter("status", this.getStatusPermitidosResumoServicos());
		List<Object[]> resultSet = query.getResultList();
		List<DetalhesServico> result = new ArrayList<DetalhesServico>();
		for (Object[] item : resultSet) {
			DetalhesServico detalhe = new DetalhesServico();
			detalhe.setServico(item[0].toString());
			detalhe.setValor((BigDecimal) item[1]);
			result.add(detalhe);
		}
		return result;
	}

	@Override
	public RelatorioResumoRecebimentos getResumoRecebimentos(Calendar dataDe,
			Calendar dataAte) {
		List<DestinoRecebimento> destinos = destinoDAO.getAll();
		RelatorioResumoRecebimentos relatorio = new RelatorioResumoRecebimentos();
		String jpql = " select r.dataRecebimento, SUM(r.valor), chequePre, dataLiquidacao "
				+ " from Recebimento r "
				+ " where r.debito.dataEmissaoNotaCobranca between :dataDe and :dataAte "
				+ "	and r.destino.id = :destino and r.debito.status != :status"
				+ " group by r.dataRecebimento, chequePre, dataLiquidacao "
				+ " order by r.dataRecebimento, chequePre, dataLiquidacao ";
		for (DestinoRecebimento destino : destinos) {
			TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
			query.setParameter("dataDe", dataDe);
			query.setParameter("dataAte", dataAte);
			query.setParameter("destino", destino.getId());
			query.setParameter("status", StatusDebitoEnum.CANCELADO);
			List<Object[]> result = query.getResultList();
			// caso o destino n�o tenha ainda nenhum recebimento
			if (result.size() == 0) {
				continue;
			}
			DetalhesDestinoRecebimento detalhe = new DetalhesDestinoRecebimento();
			detalhe.setDestino(destino.getDescricao());
			for (Object[] row : result) {
				Calendar data = (Calendar) row[0];
				BigDecimal valor = (BigDecimal) row[1];
				boolean chequePre = (boolean) row[2];
				Calendar dataLiquidacao = (Calendar) row[3];
				RecebimentoDia recebimentoDia = new RecebimentoDia();
				recebimentoDia.setDataRecebimento(data);
				recebimentoDia.setValorRecebido(valor);
				recebimentoDia.setChequePre(chequePre);
				recebimentoDia.setDataLiquidacao(dataLiquidacao);
				detalhe.getRecebimentosDestino().add(recebimentoDia);
			}
			relatorio.getDetalhesDestino().add(detalhe);
		}
		return relatorio;
	}

	private List<Servico> getServicosRecolhimento(Calendar dataDe,
			Calendar dataAte) {
		String jpql = " select DISTINCT(ds.servico) from DebitoServico ds "
				+ " Where (ds.recolhimento is not null and ds.recolhimento.id <> 0) "
				+ "	and ds.recolhimento.data BETWEEN :dataDe and :dataAte "
				+ " and ds.debito.status != :cancelado ";
		TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
		query.setParameter("dataDe", dataDe);
		query.setParameter("dataAte", dataAte);
		query.setParameter("cancelado", StatusDebitoEnum.CANCELADO);
		return query.getResultList();
	}

	@Override
	public RelatorioResumoRecolhimentos getResumoRecolhimentos(Calendar dataDe,
			Calendar dataAte) {
		RelatorioResumoRecolhimentos relatorio = new RelatorioResumoRecolhimentos();
		List<Servico> servicosRecolhimento = getServicosRecolhimento(dataDe,
				dataAte);
		String jpql = "select "
				+ "	ds.debito.cliente.id, ds.debito.cliente.nome, "
				+ " ds.recolhimento.valor, ds.recolhimento.data, "
				+ " ds.debito.dataBase, ds.debito.numeroNota, "
				+ " ds.debito.dataEmissaoNotaCobranca, ds.valor "
				+ " from DebitoServico ds "
				+ " Where ds.servico = :servico "
				+ " and (ds.recolhimento is not null and ds.recolhimento.id <> 0) "
				+ "	and ds.recolhimento.data BETWEEN :dataDe and :dataAte "
				+ " and ds.debito.status != :cancelado "
				+ " order by ds.debito.cliente.nome ";
		for (Servico servico : servicosRecolhimento) {
			TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
			query.setParameter("servico", servico);
			query.setParameter("dataDe", dataDe);
			query.setParameter("dataAte", dataAte);
			query.setParameter("cancelado", StatusDebitoEnum.CANCELADO);
			List<Object[]> recolhimentos = query.getResultList();
			DetalhesServicosRecolhimentos detalhes = new DetalhesServicosRecolhimentos();
			detalhes.setDescricao(servico.getDescricao());
			for (Object[] r : recolhimentos) {
				DetalhesClienteRecolhimentos detalhesCliente = new DetalhesClienteRecolhimentos();
				detalhesCliente.setMatricula((int) r[0]);
				detalhesCliente.setNome((String) r[1]);
				detalhesCliente.setValorRecolhido((BigDecimal) r[2]);
				detalhesCliente.setDataRecolhimento((Calendar) r[3]);
				detalhesCliente.setDataBase((Calendar) r[4]);
				detalhesCliente.setNumeroNota((int) r[5]);
				detalhesCliente.setDataEmissaoNota((Calendar) r[6]);
				detalhesCliente.setValorDoServico((BigDecimal) r[7]);
				detalhes.getDetalhesCliente().add(detalhesCliente);
			}
			relatorio.getDetalhes().add(detalhes);
		}
		return relatorio;
	}

	@Override
	public RelatorioRecolhimentosAberto getRelatorioRecolhimentosAberto(
			Calendar dataAte) {
		RelatorioRecolhimentosAberto relatorio = new RelatorioRecolhimentosAberto();

		String jpql = "select " + " ds.servico.descricao," + " ds.servico.id,"
				+ " ds.valor," + " r.valor, " + " ds.debito.numeroNota, "
				+ " ds.debito.dataBase, " + " ds.debito.cliente.id, "
				+ " ds.debito.cliente.nome " + " from DebitoServico ds "
				+ " left join ds.recolhimento r "
				+ " where ds.debito.dataEmissaoNotaCobranca <= :dataAte "
				+ " and ds.debito.status = :status "
				+ " and ds.servico.retencao = :retencao "
				+ " order by ds.debito.cliente.nome,ds.debito.numeroNota,ds.servico.descricao ";
		TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
		query.setParameter("dataAte", dataAte);
		query.setParameter("status", StatusDebitoEnum.RECEBIDO);
		query.setParameter("retencao", true);
		List<Object[]> debitoServicos = query.getResultList();

		boolean novoCliente = true;
		boolean novoDetalheNota = true;
		ClienteRecolhimentosAberto clienteRA = new ClienteRecolhimentosAberto();
		DetalhesNotaRecolhimentosAberto detalhesNota = new DetalhesNotaRecolhimentosAberto();

		for (Object[] debitoServico : debitoServicos) {

			ServicoRecolhimentosAberto servico = new ServicoRecolhimentosAberto();
			servico.setDescricao((String) debitoServico[0]);
			servico.setId((int) debitoServico[1]);
			servico.setValorARecolher((BigDecimal) debitoServico[2]);
			servico.setValorJaRecolhido((debitoServico[3] != null) ? (BigDecimal) debitoServico[3] : BigDecimal.ZERO);

			int numeroNota = (int) debitoServico[4];
			novoDetalheNota = detalhesNota.getNumeroNota() != numeroNota;
			if (novoDetalheNota) {
				if (detalhesNota.getNumeroNota() != 0) {
					clienteRA.getListaNotasPendentes().add(detalhesNota);
				}
				detalhesNota = new DetalhesNotaRecolhimentosAberto();
				detalhesNota.setDataBase((Calendar) debitoServico[5]);
				detalhesNota.setNumeroNota((int) debitoServico[4]);
			}
			int matricula = (int) debitoServico[6];
			novoCliente = clienteRA.getMatricula() != matricula;
			if (novoCliente) {
				if (clienteRA.getMatricula() != 0) {
					relatorio.getListaPendenciasPorCliente().add(clienteRA);
				}
				clienteRA = new ClienteRecolhimentosAberto();
				clienteRA.setMatricula(matricula);
				clienteRA.setNome((String) debitoServico[7]);
			}
			detalhesNota.getServicos().add(servico);
		}
		clienteRA.getListaNotasPendentes().add(detalhesNota);
		relatorio.getListaPendenciasPorCliente().add(clienteRA);
		return relatorio;
	}

	@Override
	public RelatorioRetencoesRecolher getRelatorioRetencoesRecolher(Calendar dataDe, Calendar dataAte) {
		RelatorioRetencoesRecolher relatorio = new RelatorioRetencoesRecolher();

		String jpql = " select  "
					+ " ds.servico.descricao, "
					+ " ds.debito.cliente.id, "
					+ " ds.debito.cliente.nome, "
					+ " ds.valor, "
					+ " r.valor, "
					+ " ds.debito.dataBase, "
					+ " ds.debito.numeroNota, "
					+ " ds.debito.dataEmissaoNotaCobranca "
					+ " from DebitoServico ds "
					+ " left join ds.recolhimento r "
					+ " Where ds.servico.retencao = :retencao "
					+ " and ds.debito.status in (:status) "
					+ " and ds.debito.dataEmissaoNotaCobranca between :dataDe and :dataAte "
					
					+ " group by "
					+ " ds.servico.descricao, "
					+ " ds.debito.cliente.id, "
					+ " ds.debito.cliente.nome, "
					+ " ds.valor, "
					+ " r.valor, "
					+ " ds.debito.dataBase, "
					+ " ds.debito.numeroNota, "
					+ " ds.debito.dataEmissaoNotaCobranca "

					+ " order by ds.servico.descricao, ds.debito.cliente.nome ";
		
		TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
		query.setParameter("dataDe", dataDe);
		query.setParameter("dataAte", dataAte);
		
		List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
		statusPermitido.add(StatusDebitoEnum.NOTACOBRANCAGERADA);
		statusPermitido.add(StatusDebitoEnum.DEBITOCRIADO);
		statusPermitido.add(StatusDebitoEnum.RECEBIDO);

		query.setParameter("status", statusPermitido);
		
		query.setParameter("retencao", true);
		List<Object[]> debitoServicos = query.getResultList();

		boolean novoServico = true;
		ServicoRetencoesRecolher servico = new ServicoRetencoesRecolher();
		

		for (Object[] ds : debitoServicos) {
			ClienteRetencoesRecolher cliente = new ClienteRetencoesRecolher();
			cliente.setDataBase((Calendar) ds[5]);
			cliente.setDataEmissaoNota((Calendar) ds[7]);
			cliente.setMatricula((int) ds[1]);
			cliente.setNome((String) ds[2]);
			cliente.setNumeroNota((int) ds[6]);

			BigDecimal valorDebito = (BigDecimal) ds[3];
			BigDecimal valorRecolhido = (BigDecimal) ds[4];
			// caso o servi�o ja tenha sido recolhido, subtrai o valor
			if (valorRecolhido != null
					&& valorRecolhido.compareTo(BigDecimal.ZERO) != 0) {
				valorDebito = valorDebito.subtract(valorRecolhido, MathContext.DECIMAL32);
			}
			cliente.setValor(valorDebito);

			String nomeServico = (String) ds[0];
			novoServico = (servico.getNomeServico() == null || !servico.getNomeServico().equals(nomeServico));
			if (novoServico) {
				if(servico.getNomeServico() != null){
					relatorio.getListaServicos().add(servico);
				}
				servico = new ServicoRetencoesRecolher();
				servico.setNomeServico(nomeServico);
			}
			servico.getListaClientes().add(cliente);
		}
		relatorio.getListaServicos().add(servico);
		return relatorio;
	}

	@Override
	public RelatorioNotasEmitidas getRelatorioNotasEmitidas(Calendar dataDe, Calendar dataAte){
		RelatorioNotasEmitidas relatorio = new RelatorioNotasEmitidas();
		
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select    \n");
		jpql.append(" 	d.dataEmissaoNotaCobranca,   \n");
		jpql.append(" 	s.descricao, \n");
		jpql.append(" 	SUM(ds.valor),   \n");
		jpql.append(" 	numeronotas.menor,   \n");
		jpql.append(" 	numeronotas.maior, \n");
		jpql.append(" 	totaldia.valor   \n");
		jpql.append(" from debito d   \n");
		
		jpql.append(" join ( \n");
		jpql.append(" 	select dataEmissaoNotaCobranca, min(numeronota) menor, max(numeronota) maior \n");
		jpql.append(" 	from debito \n");
		jpql.append(" 	where debito.dataEmissaoNotaCobranca between ? and ?   \n");
		jpql.append(" 	and debito.status in (1,2,3)   \n");
		jpql.append(" 	group by dataEmissaoNotaCobranca \n");
		jpql.append(" ) numeronotas on numeronotas.dataEmissaoNotaCobranca = d.dataEmissaoNotaCobranca \n");
		
		jpql.append(" join ( \n");
		jpql.append(" 	select dataEmissaoNotaCobranca, SUM(ds.valor) valor \n");
		jpql.append(" 	from debito d \n");
		jpql.append(" 	left join debitoservico ds on d.id = ds.debito_id \n");
		jpql.append(" 	where d.dataEmissaoNotaCobranca between ? and ?   \n");
		jpql.append(" 	and d.status in (1,2,3)   \n");
		jpql.append(" 	group by dataEmissaoNotaCobranca \n");
		jpql.append(" ) totaldia on totaldia.dataEmissaoNotaCobranca = d.dataEmissaoNotaCobranca \n");
		
		jpql.append(" left join debitoservico ds on d.id = ds.debito_id \n");
		jpql.append(" left join servico s on s.id = ds.servico_id \n");
		jpql.append(" Where 1 = 1 \n");
		jpql.append(" 	and d.dataEmissaoNotaCobranca between ? and ?   \n");
		jpql.append(" 	and d.status in (1,2,3)   \n");
		jpql.append(" group by d.dataEmissaoNotaCobranca, totaldia.valor, s.descricao,numeronotas.menor,numeronotas.maior \n");
		jpql.append(" order by d.dataEmissaoNotaCobranca, s.descricao \n");

		Query query = em.createNativeQuery(jpql.toString());
		query.setParameter(1, dataDe);
		query.setParameter(2, dataAte);
		query.setParameter(3, dataDe);
		query.setParameter(4, dataAte);
		query.setParameter(5, dataDe);
		query.setParameter(6, dataAte);
		
		@SuppressWarnings("unchecked")
		List<Object[]> rs = query.getResultList();

		boolean mudarDia = false;
		NotasEmitidasDia notasDia = new NotasEmitidasDia();
		
		for (Object[] notas : rs) {
			Calendar dataEmissaoNota = Calendar.getInstance();
			dataEmissaoNota.setTime((Date) notas[0]);
			
			if(notasDia.getDataEmissaoNota() == null || dataEmissaoNota.compareTo(notasDia.getDataEmissaoNota()) != 0){
				mudarDia = true;
			}
			
			if(mudarDia){
				mudarDia = false;
				notasDia = new NotasEmitidasDia();
				notasDia.setDataEmissaoNota(dataEmissaoNota);
				notasDia.setValorTotalDia((BigDecimal) notas[5]);
				relatorio.getNotasEmitidasDia().add(notasDia);
			}
			
			DetalheNotasEmitidas detalhe = new DetalheNotasEmitidas();
			detalhe.setServico((String) notas[1]);
			detalhe.setPrimeiraNota((int) notas[3]);
			detalhe.setUltimaNota((int) notas[4]);
			detalhe.setValorTotalNota((BigDecimal) notas[2]);
			notasDia.getNotas().add(detalhe);
		}
		return relatorio;
	}

	@Override
	public RelatorioInscricaoEstadual getRelatorioInscricaoEstadual(){
		RelatorioInscricaoEstadual relatorio = new RelatorioInscricaoEstadual();
		
		String jpql = " select  "
				+ " c.estabelecimentoRural.validadeInscricaoEstadual, "
				+ " c.id, "
				+ " c.nome, "
				+ " c.cpf "
				+ " from Cliente c "
				+ " Where c.socio = :socio "
				+ " and c.estabelecimentoRural.validadeInscEstIndeter = :validadeIndeter "
				+ " and c.estabelecimentoRural.validadeInscricaoEstadual <= :dataAviso "
				+ " order by c.estabelecimentoRural.validadeInscricaoEstadual, c.nome ";

		Calendar dataAviso = Calendar.getInstance();
		dataAviso.add(Calendar.MONTH, 2);
		
		TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
		query.setParameter("socio", true);
		query.setParameter("validadeIndeter", false);
		query.setParameter("dataAviso", dataAviso);
		List<Object[]> rs = query.getResultList();
		
		for (Object[] row : rs) {
			DetalheInscricaoEstadual detalhe = new DetalheInscricaoEstadual();
			detalhe.setDocumento((String) row[3]);
			detalhe.setMatricula((int) row[1]);
			detalhe.setNome((String) row[2]);
			detalhe.setVencimentoInscricao((Calendar) row[0]);
			
			relatorio.getDetalhesAssociados().add(detalhe);
		}
		
		return relatorio;
	}

	@Override
	public RelatorioFolhaVotacao getRelatorioFolhaVotacao(){
		RelatorioFolhaVotacao relatorio = new RelatorioFolhaVotacao();
		List<Cliente> clientes = clienteDAO.getAll();
		for (Cliente cliente : clientes) {
			if (!cliente.isSocio()) {
				continue;
			}
			InformacaoMensalidade infMensalid = clienteDAO
					.estaEmDiaComAsMensalidades(cliente);
			
			if (infMensalid.isAtrasado()) {
				continue;
			}
			
			if(infMensalid.getMesesComoSocio() < 6){
				continue;
			}
			
			DetalhesAssociado detalhes = new DetalhesAssociado();
			Calendar dataSocio = cliente.getDataCadastro();
			detalhes.setMatricula(cliente.getId());
			detalhes.setNome(cliente.getNome());
			detalhes.setObservacao(cliente.getObservacao());
			if (cliente.getProdutorRuralDesde() != null)
				detalhes.setProdutorRuralDesde(formatData.format(cliente
						.getProdutorRuralDesde().getTime()));
			if (dataSocio != null)
				detalhes.setSocioDesde(formatData.format(dataSocio.getTime()));
			relatorio.getDetalhesAssociado().add(detalhes);
		}
		return relatorio;
	}
}

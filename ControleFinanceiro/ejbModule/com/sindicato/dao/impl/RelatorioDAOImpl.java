package com.sindicato.dao.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.DestinoRecebimentoDAO;
import com.sindicato.dao.RelatorioDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.Servico;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.report.model.ClienteRecolhimentosAberto;
import com.sindicato.report.model.ClienteRetencoesRecolher;
import com.sindicato.report.model.DetalheInscricaoEstadual;
import com.sindicato.report.model.DetalheNotasEmitidas;
import com.sindicato.report.model.DetalhesAssociado;
import com.sindicato.report.model.DetalhesClienteRecolhimentos;
import com.sindicato.report.model.DetalhesDestinoRecebimento;
import com.sindicato.report.model.DetalhesNotaRecolhimentosAberto;
import com.sindicato.report.model.DetalhesServico;
import com.sindicato.report.model.DetalhesServicosRecolhimentos;
import com.sindicato.report.model.RecebimentoDia;
import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.report.model.RelatorioFolhaVotacao;
import com.sindicato.report.model.RelatorioInscricaoEstadual;
import com.sindicato.report.model.RelatorioNotasEmitidas;
import com.sindicato.report.model.RelatorioRecolhimentosAberto;
import com.sindicato.report.model.RelatorioResumoRecebimentos;
import com.sindicato.report.model.RelatorioResumoRecolhimentos;
import com.sindicato.report.model.RelatorioResumoServico;
import com.sindicato.report.model.RelatorioRetencoesRecolher;
import com.sindicato.report.model.ServicoRecolhimentosAberto;
import com.sindicato.report.model.ServicoRetencoesRecolher;
import com.sindicato.result.InformacaoMensalidade;

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
	public RelatorioAssociados getAssociados() {
		RelatorioAssociados relatorio = new RelatorioAssociados();
		int associadosEmDia = 0;
		int associadosEmAtraso = 0;
		int totalAssociados = 0;
		List<Cliente> clientes = clienteDAO.getAll();
		for (Cliente cliente : clientes) {
			if (!cliente.isSocio()) {
				continue;
			}
			totalAssociados++;
			Calendar dataSocio = cliente.getDataCadastro();
			InformacaoMensalidade infMensalid = clienteDAO
					.estaEmDiaComAsMensalidades(cliente);
			DetalhesAssociado detalhes = new DetalhesAssociado();
			if (infMensalid.isAtrasado()) {
				detalhes.setStatusAssociado("Em atraso");
				associadosEmAtraso++;
			} else {
				detalhes.setStatusAssociado("Em dia");
				associadosEmDia++;
			}
			detalhes.setSituacao(infMensalid.getMensagem());
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
		relatorio.setTotalAssociados(totalAssociados);
		relatorio.setTotalAssociadosEmAtraso(associadosEmAtraso);
		relatorio.setTotalAssociadosEmDia(associadosEmDia);
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
		String jpql = " select r.dataRecebimento, SUM(r.valor) "
				+ " from Recebimento r "
				+ " where r.debito.dataEmissaoNotaCobranca between :dataDe and :dataAte "
				+ "	and r.destino.id = :destino and r.debito.status != :status"
				+ " group by r.dataRecebimento "
				+ " order by r.dataRecebimento ";
		for (DestinoRecebimento destino : destinos) {
			TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
			query.setParameter("dataDe", dataDe);
			query.setParameter("dataAte", dataAte);
			query.setParameter("destino", destino.getId());
			query.setParameter("status", StatusDebitoEnum.CANCELADO);
			List<Object[]> result = query.getResultList();
			// caso o destino não tenha ainda nenhum recebimento
			if (result.size() == 0) {
				continue;
			}
			DetalhesDestinoRecebimento detalhe = new DetalhesDestinoRecebimento();
			detalhe.setDestino(destino.getDescricao());
			for (Object[] row : result) {
				Calendar data = (Calendar) row[0];
				BigDecimal valor = (BigDecimal) row[1];
				RecebimentoDia recebimentoDia = new RecebimentoDia();
				recebimentoDia.setDataRecebimento(data);
				recebimentoDia.setValorRecebido(valor);
				detalhe.getRecebimentosDestino().add(recebimentoDia);
			}
			relatorio.getDetalhesDestino().add(detalhe);
		}
		return relatorio;
	}

	private List<Servico> getServicosRecolhimento(Calendar dataDe,
			Calendar dataAte) {
		String jpql = " select DISTINCT(ds.servico) from DebitoServico ds "
				+ " Where (ds.recolhimento is not null and ds.recolhimento.valor > 0) "
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
				+ " and (ds.recolhimento is not null and ds.recolhimento.valor > 0) "
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
				+ " order by ds.debito.cliente.nome ";
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
			servico.setValor((BigDecimal) debitoServico[2]);
			if (servico.getValor() != null) {
				BigDecimal valorRecolhido = (BigDecimal) debitoServico[3];
				// caso o serviço ja tenha sido recolhido, subtrai o
				// valor
				if (valorRecolhido != null
						&& valorRecolhido.compareTo(BigDecimal.ZERO) != 0) {
					servico.setValor(servico.getValor().subtract(
							valorRecolhido, MathContext.DECIMAL32));
				}
			}
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
					+ " and ds.debito.status = :status "
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
		query.setParameter("status", StatusDebitoEnum.RECEBIDO);
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
			// caso o serviço ja tenha sido recolhido, subtrai o valor
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
		return relatorio;
	}

	@Override
	public RelatorioNotasEmitidas getRelatorioNotasEmitidas(Calendar dataDe, Calendar dataAte){
		RelatorioNotasEmitidas relatorio = new RelatorioNotasEmitidas();
		
		String jpql = " select  "
				+ " d.dataEmissaoNotaCobranca, "
				+ " SUM(ds.valor), "
				+ " MIN(d.numeroNota), "
				+ " MAX(d.numeroNota) "
				+ " from Debito d "
				+ " left join d.debitoServicos ds "
				+ " Where d.status not in (:status) "
				+ " and d.dataEmissaoNotaCobranca between :dataDe and :dataAte "
				+ " group by d.dataEmissaoNotaCobranca "
				+ " order by d.dataEmissaoNotaCobranca ";

		List<StatusDebitoEnum> statusNaoPermitidos = new ArrayList<StatusDebitoEnum>();	
		statusNaoPermitidos.add(StatusDebitoEnum.DEBITOCRIADO);
		statusNaoPermitidos.add(StatusDebitoEnum.CANCELADO);
			
		TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
		query.setParameter("dataDe", dataDe);
		query.setParameter("dataAte", dataAte);
		query.setParameter("status", statusNaoPermitidos);
		List<Object[]> rs = query.getResultList();

		for (Object[] notas : rs) {
			DetalheNotasEmitidas detalhe = new DetalheNotasEmitidas();
			detalhe.setDataEmissaoNota((Calendar) notas[0]);
			detalhe.setValorTotalDia((BigDecimal) notas[1]);
			detalhe.setPrimeiraNota((int) notas[2]);
			detalhe.setUltimaNota((int) notas[3]);
			
			relatorio.getNotasEmitidas().add(detalhe);
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

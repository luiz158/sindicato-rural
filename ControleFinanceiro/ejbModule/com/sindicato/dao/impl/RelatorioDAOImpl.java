package com.sindicato.dao.impl;

import java.math.BigDecimal;
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
import com.sindicato.report.model.DetalhesAssociado;
import com.sindicato.report.model.DetalhesClienteRecolhimentos;
import com.sindicato.report.model.DetalhesDestinoRecebimento;
import com.sindicato.report.model.DetalhesServico;
import com.sindicato.report.model.DetalhesServicosRecolhimentos;
import com.sindicato.report.model.RecebimentoDia;
import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.report.model.RelatorioResumoRecebimentos;
import com.sindicato.report.model.RelatorioResumoRecolhimentos;
import com.sindicato.report.model.RelatorioResumoServico;
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
			InformacaoMensalidade infMensalid = clienteDAO.estaEmDiaComAsMensalidades(cliente);
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
				detalhes.setProdutorRuralDesde(formatData.format(cliente.getProdutorRuralDesde().getTime()));
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
				+ " and ds.debito.cliente.socio = :socio " + "	and ds.servico.retencao = :retencao "
				+ " and ds.debito.status in (:status) " + " group by ds.servico.descricao "
				+ " order by ds.servico.descricao ";
	}
	@Override
	public RelatorioResumoServico getResumoServico(Calendar dataDe, Calendar dataAte) {
		RelatorioResumoServico relResumoServico = new RelatorioResumoServico();
		relResumoServico.setRetencoesSocios(this.getValoresServicosPeriodo(dataDe, dataAte, true, true));
		relResumoServico.setRetencoesNaoSocios(this.getValoresServicosPeriodo(dataDe, dataAte, false, true));
		relResumoServico.setReceitasSocios(this.getValoresServicosPeriodo(dataDe, dataAte, true, false));
		relResumoServico.setReceitasNaoSocios(this.getValoresServicosPeriodo(dataDe, dataAte, false, false));
		return relResumoServico;
	}
	private List<DetalhesServico> getValoresServicosPeriodo(Calendar dataDe, Calendar dataAte, boolean socio,
			boolean retencao) {
		TypedQuery<Object[]> query = em.createQuery(this.getJPQLResumoServicos(), Object[].class);
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
	public RelatorioResumoRecebimentos getResumoRecebimentos(Calendar dataDe, Calendar dataAte) {
		List<DestinoRecebimento> destinos = destinoDAO.getAll();
		RelatorioResumoRecebimentos relatorio = new RelatorioResumoRecebimentos();
		String jpql = " select r.dataRecebimento, SUM(r.valor) " + " from Recebimento r "
				+ " where r.debito.dataEmissaoNotaCobranca between :dataDe and :dataAte "
				+ "	and r.destino.id = :destino " + " group by r.dataRecebimento " + " order by r.dataRecebimento ";
		for (DestinoRecebimento destino : destinos) {
			TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
			query.setParameter("dataDe", dataDe);
			query.setParameter("dataAte", dataAte);
			query.setParameter("destino", destino.getId());
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
	private List<Servico> getServicosRecolhimento(Calendar dataDe, Calendar dataAte){
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
	public RelatorioResumoRecolhimentos getResumoRecolhimentos(Calendar dataDe, Calendar dataAte) {
		RelatorioResumoRecolhimentos relatorio = new RelatorioResumoRecolhimentos();
		List<Servico> servicosRecolhimento = getServicosRecolhimento(dataDe, dataAte);
		String jpql = "select " + "	ds.debito.cliente.id, ds.debito.cliente.nome, "
				+ " ds.recolhimento.valor, ds.recolhimento.data, " + " ds.debito.dataBase, ds.debito.numeroNota, "
				+ " ds.debito.dataEmissaoNotaCobranca, ds.valor " + " from DebitoServico ds "
				+ " Where ds.servico = :servico " + " and (ds.recolhimento is not null and ds.recolhimento.valor > 0) "
				+ "	and ds.recolhimento.data BETWEEN :dataDe and :dataAte " + " and ds.debito.status != :cancelado "
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
}

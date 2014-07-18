package com.sindicato.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.RelatorioDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.report.model.DetalhesAssociado;
import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.report.model.RelatorioResumoServico;
import com.sindicato.result.InformacaoMensalidade;

@Stateful
public class RelatorioDAOImpl implements RelatorioDAO {

	@PersistenceContext(name = "ControleFinanceiro")
	private EntityManager em;

	@EJB
	private ClienteDAO clienteDAO;

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
				+ " where ds.debito.dataBase BETWEEN :dataDe and :dataAte "
				+ " and ds.debito.cliente.socio = :socio "
				+ "	and ds.servico.retencao = :retencao "
				+ " and ds.debito.status in (:status) "
				+ " group by ds.servico.descricao ";
	}
	@Override
	public RelatorioResumoServico getResumoServico(Calendar dataDe,
			Calendar dataAte) {

		RelatorioResumoServico relResumoServico = new RelatorioResumoServico();

		relResumoServico.setRetencoesSocios(this.getValoresServicosPeriodo(dataDe, dataAte, true, true));
		relResumoServico.setRetencoesNaoSocios(this.getValoresServicosPeriodo(dataDe, dataAte, false, true));

		relResumoServico.setReceitasSocios(this.getValoresServicosPeriodo(dataDe, dataAte, true, false));
		relResumoServico.setReceitasNaoSocios(this.getValoresServicosPeriodo(dataDe, dataAte, false, false));

		return relResumoServico;
	}

	private Map<String, BigDecimal> getValoresServicosPeriodo(
			Calendar dataDe, Calendar dataAte, boolean socio, boolean retencao) {

		TypedQuery<Object[]> query = em.createQuery(
				this.getJPQLResumoServicos(), Object[].class);
		query.setParameter("dataDe", dataDe);
		query.setParameter("dataAte", dataAte);
		query.setParameter("socio", socio);
		query.setParameter("retencao", retencao);
		query.setParameter("status", this.getStatusPermitidosResumoServicos());

		List<Object[]> retencoesSocios = query.getResultList();
		HashMap<String, BigDecimal> hashMap = new HashMap<String, BigDecimal>();
		for (Object[] item : retencoesSocios) {
			hashMap.put(item[0].toString(), (BigDecimal) item[1]);
		}
		return hashMap;
	}

}

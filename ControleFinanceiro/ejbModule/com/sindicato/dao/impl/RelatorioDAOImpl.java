package com.sindicato.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.RelatorioDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.report.model.DetalhesAssociado;
import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.result.InformacaoMensalidade;

public class RelatorioDAOImpl implements RelatorioDAO {

	@PersistenceContext(name="ControleFinanceiro")
	private EntityManager em;
	
	@EJB private ClienteDAO clienteDAO;
	
	SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public RelatorioAssociados getAssociados() {

		RelatorioAssociados relatorio = new RelatorioAssociados();
		
		int associadosEmDia = 0;
		int associadosEmAtraso = 0;
		
		String strQuery = "Select distinct i.cliente, MIN(i.dataEvento) from InformacaoSocio i "
				+ "join fetch cliente "
				+ "order by i.cliente.nome asc ";
		TypedQuery<Object[]> query = null;
		query = em.createQuery(strQuery, Object[].class);

		List<Object[]> objects = query.getResultList();
		for (Object[] o : objects) {
			Cliente cliente = (Cliente) o[0];
			Calendar dataSocio = (Calendar) o[1];
			
			InformacaoMensalidade infMensalid = clienteDAO.estaEmDiaComAsMensalidades(cliente);
			DetalhesAssociado detalhes = new DetalhesAssociado();

			if(infMensalid.isAtrasado()){
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
			detalhes.setProdutorRuralDesde(formatData.format(cliente.getProdutorRuralDesde().getTime()));
			detalhes.setSocioDesde(formatData.format(dataSocio.getTime()));
			detalhes.setTelefone(cliente.getTelefone());
			
			relatorio.getDetalhesAssociado().add(detalhes);
		}

		relatorio.setTotalAssociados(objects.size());
		relatorio.setTotalAssociadosEmAtrazo(associadosEmAtraso);
		relatorio.setTotalAssociadosEmDia(associadosEmDia);
		return relatorio;
	}

}

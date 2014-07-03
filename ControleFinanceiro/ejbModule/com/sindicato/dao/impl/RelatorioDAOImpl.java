package com.sindicato.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.dao.RelatorioDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.report.model.DetalhesAssociado;
import com.sindicato.report.model.RelatorioAssociados;
import com.sindicato.result.InformacaoMensalidade;

@Stateful
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
		int totalAssociados = 0;
		
		List<Cliente> clientes = clienteDAO.getAll();
		for (Cliente cliente : clientes) {
			if(!cliente.isSocio()){
				continue;
			}
				
			totalAssociados++;
			
			Calendar dataSocio = cliente.getDataCadastro();
			
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
			detalhes.setTelefone(cliente.getTelefone());

			if(cliente.getProdutorRuralDesde() != null)
				detalhes.setProdutorRuralDesde(formatData.format(cliente.getProdutorRuralDesde().getTime()));
			
			if(dataSocio != null)
				detalhes.setSocioDesde(formatData.format(dataSocio.getTime()));
			
			relatorio.getDetalhesAssociado().add(detalhes);
		}

		relatorio.setTotalAssociados(totalAssociados);
		relatorio.setTotalAssociadosEmAtraso(associadosEmAtraso);
		relatorio.setTotalAssociadosEmDia(associadosEmDia);
		return relatorio;
	}

}

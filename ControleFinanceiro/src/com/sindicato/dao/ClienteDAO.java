package com.sindicato.dao;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.MensalidadePaga;

public interface ClienteDAO extends DAO<Cliente, Integer> {

	boolean isSocio(Cliente cliente);
	
	int calculaQuantosMesesOClienteESocio(Cliente cliente);

	int calculaQuantasMensalidadeForamPagas(Cliente cliente);
	
	InformacaoMensalidade estaEmDiaComAsMensalidades(Cliente cliente);

	List<InformacaoSocio> getInformacoesSocio(Cliente cliente);
	
	List<MensalidadePaga> getInformacoesMensalidade(Cliente cliente);
	
	List<Cliente> getResultListFiltered(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters);

	int count(Map<String, String> filters);
}

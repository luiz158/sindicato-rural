package com.sindicato.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.MensalidadePaga;

@Remote
public interface ClienteDAO extends DAO<Cliente, Integer> {

	boolean isSocio(Cliente cliente);
	
	int calculaQuantosMesesOClienteESocio(Cliente cliente);

	int calculaQuantasMensalidadeForamPagas(Cliente cliente);
	
	InformacaoMensalidade estaEmDiaComAsMensalidades(Cliente cliente);

	List<InformacaoSocio> getInformacoesSocio(Cliente cliente);
	
	List<MensalidadePaga> getInformacoesMensalidade(Cliente cliente);
	
	List<Cliente> getResultListFiltered(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters);

	int count(Map<String, Object> filters);
}

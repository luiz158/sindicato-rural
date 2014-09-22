package com.sindicato.controlefinanceiro.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.sindicato.controlefinanceiro.entity.Cliente;
import com.sindicato.controlefinanceiro.entity.InformacaoSocio;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.MensalidadePaga;
import com.sindicato.result.ResultOperation;

@Remote
public interface ClienteDAO extends DAO<Cliente, Integer> {

	int calculaQuantosMesesOClienteESocio(Cliente cliente);

	int calculaQuantasMensalidadeForamPagas(Cliente cliente);
	
	InformacaoMensalidade estaEmDiaComAsMensalidades(Cliente cliente);

	List<InformacaoSocio> getInformacoesSocio(Cliente cliente);
	
	List<MensalidadePaga> getInformacoesMensalidade(Cliente cliente);
	
	List<Cliente> getResultListFiltered(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters);

	int count(Map<String, Object> filters);

	void desativarCliente(Cliente cliente);
	
	ResultOperation salvar(Cliente cliente);
}

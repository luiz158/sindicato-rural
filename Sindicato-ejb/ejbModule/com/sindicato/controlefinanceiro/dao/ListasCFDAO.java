package com.sindicato.controlefinanceiro.dao;

import java.util.List;

import javax.ejb.Local;

import com.sindicato.controlefinanceiro.entity.Cliente;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.DebitoServico;
import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;

@Local
public interface ListasCFDAO {

	List<Cliente> getClientesComDebitosNoStatus(StatusDebitoEnum status);
	List<Debito> getDebitosDoClienteNoStatus(Cliente cliente, StatusDebitoEnum status);
	List<Debito> getDebitosNoStatus(List<StatusDebitoEnum> status);
	
	List<Debito> getTodasNotasDeCobranca();
	
	List<TipoRecebimento> getTodasFormasRecebimento();
	List<DestinoRecebimento> getTodosDestinosRecebimento();
	
	List<DebitoServico> getTodosOsServicosComRetencaoDoDebito(Debito debito);

}

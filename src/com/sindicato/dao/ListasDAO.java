package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.Cliente;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.TipoRecebimento;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.entity.autenticacao.Menu;
import com.sindicato.entity.autenticacao.Perfil;

public interface ListasDAO {

	List<Cliente> getClientesComDebitosNoStatus(StatusDebitoEnum status);
	List<Debito> getDebitosDoClienteNoStatus(Cliente cliente, StatusDebitoEnum status);
	List<Debito> getDebitosNoStatus(StatusDebitoEnum status);
	
	List<Debito> getTodasNotasDeCobranca();
	
	List<TipoRecebimento> getTodasFormasRecebimento();
	List<DestinoRecebimento> getTodosDestinosRecebimento();
	
	List<DebitoServico> getTodosOsServicosComRetencaoDoDebito(Debito debito);

	List<Perfil> getTodosOsPerfis();
	List<Menu> getTodosOsMenus();
	
}

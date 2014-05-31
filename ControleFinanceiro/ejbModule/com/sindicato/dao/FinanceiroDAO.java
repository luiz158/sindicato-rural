package com.sindicato.dao;

import javax.ejb.Remote;

import com.sindicato.entity.Debito;
import com.sindicato.result.ResultOperation;


@Remote
public interface FinanceiroDAO{

	ResultOperation gravarDebito(Debito debito);
	
	ResultOperation gerarNotaDeCobranca(Debito debito);
	
	ResultOperation cancelarNotaDeCobranca(Debito debito);

	ResultOperation registrarRecebimento(Debito debito);

	ResultOperation registrarRecolhimentos(Debito debito);
	
	ResultOperation salvarAlteracaoNotaCobranca(Debito debito);
	
}

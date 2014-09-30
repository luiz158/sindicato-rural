package com.sindicato.controlefinanceiro.dao;

import javax.ejb.Local;

import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.result.ResultOperation;


@Local
public interface FinanceiroDAO{

	ResultOperation gravarDebito(Debito debito);
	
	ResultOperation gerarNotaDeCobranca(Debito debito);
	
	ResultOperation cancelarNotaDeCobranca(Debito debito);

	ResultOperation registrarRecebimento(Debito debito);

	ResultOperation registrarRecolhimentos(Debito debito);
	
	ResultOperation salvarAlteracaoNotaCobranca(Debito debito);
	
}

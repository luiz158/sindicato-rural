package com.sindicato.dao;

import java.util.List;

import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.result.ResultOperation;


public interface FinanceiroDAO{

	ResultOperation gravarDebito(Debito debito);
	
	ResultOperation gerarNotaDeCobranca(Debito debito);
	
	ResultOperation cancelarNotaDeCobranca(Debito debito);

	ResultOperation registrarRecebimento(Debito debito);

	ResultOperation registrarRecolhimentos(Debito debito, List<DebitoServico> servicos);
	
}

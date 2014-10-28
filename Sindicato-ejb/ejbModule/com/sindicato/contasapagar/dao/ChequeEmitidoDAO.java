package com.sindicato.contasapagar.dao;

import javax.ejb.Local;

import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.result.ResultOperation;

@Local
public interface ChequeEmitidoDAO {
	
	ResultOperation emitirCheque(ChequeEmitido cheque);
	ResultOperation cancelarCheque(ChequeEmitido cheque);
	
	Long getNumeroUltimoChequeEmitido(Banco banco);
	
}

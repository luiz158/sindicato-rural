package com.sindicato.contasapagar.dao;

import java.util.List;

import javax.ejb.Local;

import com.sindicato.contasapagar.entity.Banco;
import com.sindicato.contasapagar.entity.ChequeEmitido;
import com.sindicato.contasapagar.report.model.FiltroRelatorioCheques;
import com.sindicato.contasapagar.report.model.RelatorioCheques;
import com.sindicato.result.ResultOperation;

@Local
public interface ChequeEmitidoDAO {
	
	ResultOperation emitirCheque(ChequeEmitido cheque);
	ResultOperation cancelarCheque(ChequeEmitido cheque);
	ResultOperation salvarCheque(ChequeEmitido cheque);
	
	Long getNumeroUltimoChequeEmitido(Banco banco);
	List<ChequeEmitido> listarCheques();
	
	RelatorioCheques getRelatorioCheques(FiltroRelatorioCheques filtro);
	
	ChequeEmitido getChequePorId(int id);
}

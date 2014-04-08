package com.sindicato.dao;

import com.sindicato.entity.Debito;
import com.sindicato.result.ResultOperation;


public interface FinanceiroDAO{

	ResultOperation gravarDebito(Debito debito);
	
	
}

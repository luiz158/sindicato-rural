package com.sindicato.controlefinanceiro.lazyDataModel;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.DebitoDAO;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.sindicato.util.Constantes;

public class LazyDebitoDataModel extends LazyDataModel<Debito> {

	private static final long serialVersionUID = 1L;
	private List<Debito> datasource;
	private List<StatusDebitoEnum> statusPermitidos;
	
	private DebitoDAO debitoDAO;
	private boolean listaRecolhimento;
	
	
    public LazyDebitoDataModel(List<StatusDebitoEnum> statusPermitidos, boolean listaRecolhimento) {
		debitoDAO = (DebitoDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/DebitoDAOImpl");
		this.statusPermitidos = statusPermitidos;
		this.listaRecolhimento = listaRecolhimento;
    }
    public LazyDebitoDataModel(List<StatusDebitoEnum> statusPermitidos) {
		debitoDAO = (DebitoDAO) UtilBean.getClassLookup(Constantes.NOME_PROJETO + "/DebitoDAOImpl");
		this.statusPermitidos = statusPermitidos;
		this.listaRecolhimento = false;
    }
	
    @Override
    public Debito getRowData(String rowKey) {
        for(Debito debito : datasource) {
            if(debito.getId() == Integer.parseInt(rowKey))
                return debito;
        }
        return null;
    }

    @Override
    public Object getRowKey(Debito debito) {
        return debito.getId();
    }
 
    @Override
    public List<Debito> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        
    	if(listaRecolhimento){
    		filters.put("debitoServicos.servico.retencao", true);
    	}
    	
    	//filter
    	datasource = debitoDAO.getResultListFiltered(first, pageSize, sortField, sortOrder.toString(), filters, statusPermitidos);
 
        //rowCount
        this.setRowCount(debitoDAO.count(filters, statusPermitidos));
 
        return datasource;
    }

	@Override
	public void forEach(Consumer<? super Debito> action) { }

	@Override
	public Spliterator<Debito> spliterator() { return null; }

}

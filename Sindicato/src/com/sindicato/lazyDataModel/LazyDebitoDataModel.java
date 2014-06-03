package com.sindicato.lazyDataModel;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.DebitoDAO;
import com.sindicato.entity.Debito;
import com.sindicato.entity.Enum.StatusDebitoEnum;

public class LazyDebitoDataModel extends LazyDataModel<Debito> {

	private static final long serialVersionUID = 1L;
	private List<Debito> datasource;
	private List<StatusDebitoEnum> statusPermitidos;
	
	private DebitoDAO debitoDAO;
	
    public LazyDebitoDataModel(List<StatusDebitoEnum> statusPermitidos) {
		debitoDAO = (DebitoDAO) UtilBean.getClassLookup("ControleFinanceiro/DebitoDAOImpl");
		this.statusPermitidos = statusPermitidos;
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

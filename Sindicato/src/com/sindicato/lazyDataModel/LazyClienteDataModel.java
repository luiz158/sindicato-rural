package com.sindicato.lazyDataModel;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.ClienteDAO;
import com.sindicato.entity.Cliente;

public class LazyClienteDataModel extends LazyDataModel<Cliente> {

	private static final long serialVersionUID = 1L;
	private List<Cliente> datasource;
 
	private ClienteDAO clienteDAO;
	
    public LazyClienteDataModel() {
		clienteDAO = (ClienteDAO) UtilBean.getClassLookup("ControleFinanceiro/ClienteDAOImpl");
    }
     
    @Override
    public Cliente getRowData(String rowKey) {
        for(Cliente cliente : datasource) {
            if(cliente.getId() == Integer.parseInt(rowKey))
                return cliente;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Cliente cliente) {
        return cliente.getId();
    }
 
    @Override
    public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        
    	//filter
    	datasource = clienteDAO.getResultListFiltered(first, pageSize, sortField, sortOrder.toString(), filters);
 
        //rowCount
        this.setRowCount(clienteDAO.count(filters));
 
        //paginate
        return datasource;
    }

	@Override
	public void forEach(Consumer<? super Cliente> action) { }

	@Override
	public Spliterator<Cliente> spliterator() { return null; }
}

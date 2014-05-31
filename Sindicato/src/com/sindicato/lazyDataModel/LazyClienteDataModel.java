package com.sindicato.lazyDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sindicato.entity.Cliente;

public class LazyClienteDataModel extends LazyDataModel<Cliente> {

	private static final long serialVersionUID = 1L;
	private List<Cliente> datasource;
    
    public LazyClienteDataModel(List<Cliente> datasource) {
        this.datasource = datasource;
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
    public Object getRowKey(Cliente Cliente) {
        return Cliente.getId();
    }
 
    @Override
    public List<Cliente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<Cliente> data = new ArrayList<Cliente>();
 
        //filter
        for(Cliente Cliente : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(Cliente.getClass().getField(filterProperty).get(Cliente));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(Cliente);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazyClienteSorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }

	@Override
	public void forEach(Consumer<? super Cliente> action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spliterator<Cliente> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}
}

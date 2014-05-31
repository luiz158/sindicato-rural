package com.sindicato.lazyDataModel;
 
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.primefaces.model.SortOrder;

import com.sindicato.entity.Cliente;
 
public class LazyClienteSorter implements Comparator<Cliente> {
 
	private String sortField;
    
    private SortOrder sortOrder;
     
    public LazyClienteSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings("unchecked")
	public int compare(Cliente cliente1, Cliente cliente2) {
        try {
            Object value1 = Cliente.class.getField(this.sortField).get(cliente1);
            Object value2 = Cliente.class.getField(this.sortField).get(cliente2);
 
			@SuppressWarnings("rawtypes")
			int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

	@Override
	public Comparator<Cliente> reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Cliente> thenComparing(Comparator<? super Cliente> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> Comparator<Cliente> thenComparing(
			Function<? super Cliente, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<Cliente> thenComparing(
			Function<? super Cliente, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Cliente> thenComparingInt(
			ToIntFunction<? super Cliente> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Cliente> thenComparingLong(
			ToLongFunction<? super Cliente> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<Cliente> thenComparingDouble(
			ToDoubleFunction<? super Cliente> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}





}
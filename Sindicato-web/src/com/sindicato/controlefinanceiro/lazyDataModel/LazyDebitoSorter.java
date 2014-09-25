package com.sindicato.controlefinanceiro.lazyDataModel;
 
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.primefaces.model.SortOrder;

import com.sindicato.controlefinanceiro.entity.Debito;
 
public class LazyDebitoSorter implements Comparator<Debito> {
 
	private String sortField;
    
    private SortOrder sortOrder;
     
    public LazyDebitoSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings("unchecked")
	public int compare(Debito debito1, Debito debito2) {
        try {
            Object value1 = Debito.class.getField(this.sortField).get(debito1);
            Object value2 = Debito.class.getField(this.sortField).get(debito2);
 
			@SuppressWarnings("rawtypes")
			int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

	@Override
	public Comparator<Debito> reversed() {

		return null;
	}

	@Override
	public Comparator<Debito> thenComparing(Comparator<? super Debito> other) {
		
		return null;
	}

	@Override
	public <U> Comparator<Debito> thenComparing(
			Function<? super Debito, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		
		return null;
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<Debito> thenComparing(
			Function<? super Debito, ? extends U> keyExtractor) {
		
		return null;
	}

	@Override
	public Comparator<Debito> thenComparingInt(
			ToIntFunction<? super Debito> keyExtractor) {
		
		return null;
	}

	@Override
	public Comparator<Debito> thenComparingLong(
			ToLongFunction<? super Debito> keyExtractor) {
		
		return null;
	}

	@Override
	public Comparator<Debito> thenComparingDouble(
			ToDoubleFunction<? super Debito> keyExtractor) {
		
		return null;
	}





}
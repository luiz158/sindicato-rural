package com.sindicato.dao;

import java.util.List;

public interface DAO<T, K> {

	void insert(T entity);
	
	void remove(T entity);
	
	void removeById(K id);
	
	T searchByID(K id);
	
	void update(T entity);
	
	List<T> getAll();
	

}

package com.sindicato.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sindicato.dao.DAO;

public class DAOImpl<T, K> implements DAO<T, K> {

	protected EntityManager em;

	private Class<T> entityClass;

	@SuppressWarnings("all")
	public DAOImpl(EntityManager entityManager) {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.em = entityManager;
	}

	@Override
	public void insert(T entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();

	}

	@Override
	public void removeById(K id) {
		em.getTransaction().begin();
		T entity = em.find(entityClass, id);
		em.remove(entity);
		em.getTransaction().commit();
	}

	@Override
	public void remove(T entity) {
		em.getTransaction().begin();
		// para forçar a entidade ser gerenciada pelo em
		em.merge(entity);
		em.remove(entity);
		em.getTransaction().commit();
	}

	@Override
	public T searchByID(K id) {
		return em.find(entityClass, id);
	}

	@Override
	public void update(T entity) {
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
	}

	@Override
	public List<T> getAll() {
		String strQuery = "Select u from " + entityClass.getName() + " u ";
		TypedQuery<T> query = null;
		query = em.createQuery(strQuery, entityClass);
		return query.getResultList();
	}


}

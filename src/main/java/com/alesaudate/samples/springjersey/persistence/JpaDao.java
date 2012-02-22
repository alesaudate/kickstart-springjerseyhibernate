package com.alesaudate.samples.springjersey.persistence;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alesaudate.samples.springjersey.entities.BaseEntity;

public abstract class JpaDao <T extends BaseEntity> extends GenericDao<T> {
	
	@Autowired
	private JpaTemplate jpaTemplate;
	
	private Class<T> managedClass;
	
	
	
	public JpaDao(Class<T> managedClass) {
		this.managedClass = managedClass;
	}
	
	public JpaDao(Class<T> managedClass, JpaTemplate jpaTemplate) {
		this.managedClass = managedClass;
		this.jpaTemplate = jpaTemplate;
	}
	
	
	@Transactional
	public T create(T entity) {
		this.jpaTemplate.persist(entity);
		this.jpaTemplate.flush();
		return entity;
	}
	
	public T retrieve(Long id) {
		T found = this.jpaTemplate.find(managedClass, id);
		if (found != null && !found.getActive()) {
			return null;
		}
		return found;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<T> retrieve() {
		StringBuilder query = new StringBuilder("select entity from ").append(managedClass.getSimpleName()).append(" entity where entity.active=true");
		return this.jpaTemplate.find(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<T> retrieve(final int pageNumber) {
		final StringBuilder query = new StringBuilder("select entity from ").append(managedClass.getSimpleName()).append(" entity where entity.active=true");
		return this.jpaTemplate.executeFind(new JpaCallback<Collection<T>>() {
			@Override
			public Collection<T> doInJpa(EntityManager arg0) throws PersistenceException {
				Query jpaQuery = arg0.createQuery(query.toString()).setFirstResult(pageNumber * getPageSize()).setMaxResults(getPageSize());
				return jpaQuery.getResultList();
			}
		});
		
		
	}
	
	
	@Override
	public long count() {
		final StringBuilder query = new StringBuilder("select count(entity) from ").append(managedClass.getSimpleName()).append(" entity where entity.active=true");
		return this.jpaTemplate.execute(new JpaCallback<Long>() {
			@Override
			public Long doInJpa(EntityManager em)
					throws PersistenceException {
				Query jpaQuery = em.createQuery(query.toString());
				return (Long)jpaQuery.getSingleResult();
			}
		});
		
	}
	
	
	@Transactional
	public T update(T entity) {
		
		entity.setUpdateDate(new Date());
		jpaTemplate.merge(entity);
		return entity;
	}
	
	
	@Transactional
	public void delete (T entity) {
		//Doesn't really delete, only deactivate it
		entity.setActive(false);
		update(entity);
	}
	
	

	
	
}

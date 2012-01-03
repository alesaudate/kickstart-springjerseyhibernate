package com.alesaudate.samples.springjersey.persistence;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alesaudate.samples.springjersey.entities.BaseEntity;

public abstract class JpaDao<T extends BaseEntity> extends GenericDao<T> {
	
	@Autowired
	private JpaTemplate jpaTemplate;
	
	private Class<T> managedClass;
	
	public JpaDao(Class<T> managedClass) {
		this.managedClass = managedClass;
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
	
	
	@Transactional
	public T update(T entity) {
		
		entity.setUpdateDate(new Date());
		entity = this.jpaTemplate.merge(entity);
		this.jpaTemplate.flush();
		return entity;
	}
	
	
	@Transactional
	public void delete (T entity) {
		//Doesn't really delete, only deactivate it
		entity.setActive(false);
		update(entity);
	}

}

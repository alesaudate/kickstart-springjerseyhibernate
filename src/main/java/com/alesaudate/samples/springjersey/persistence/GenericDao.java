package com.alesaudate.samples.springjersey.persistence;

import java.util.Collection;

import com.alesaudate.samples.springjersey.entities.BaseEntity;

public abstract class GenericDao<T extends BaseEntity> {
	
	
	public abstract T create(T entity);
	
	public abstract T retrieve(Long id) ;
	
	public abstract Collection<T> retrieve() ;
	
	public abstract Collection<T> retrieve(int pageNumber);
	
	public abstract T update(T entity) ;
	
	public abstract void delete (T entity) ;
	
	
	

}

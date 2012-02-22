package com.alesaudate.samples.springjersey.persistence;

import java.util.Collection;

import com.alesaudate.samples.springjersey.entities.BaseEntity;

public abstract class GenericDao<T extends BaseEntity> {
	
	
	private int pageSize = 20;
	
	public abstract T create(T entity);
	
	public abstract T retrieve(Long id) ;
	
	public abstract Collection<T> retrieve() ;
	
	public abstract Collection<T> retrieve(int pageNumber);
	
	public abstract T update(T entity) ;
	
	public abstract void delete (T entity) ;
	
	public abstract long count();
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	

}

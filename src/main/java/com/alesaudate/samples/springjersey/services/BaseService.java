package com.alesaudate.samples.springjersey.services;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.security.access.prepost.PreAuthorize;

import com.alesaudate.samples.springjersey.entities.BaseEntity;
import com.alesaudate.samples.springjersey.persistence.GenericDao;


@Consumes(MediaType.APPLICATION_XML) //Could easily be changed to JSON
@Produces(MediaType.APPLICATION_XML)
public abstract class BaseService<T extends BaseEntity> {
	
	
	
	private GenericDao<T> dao;
	
	public GenericDao<T> getDao() {
		return dao;
	}
	
	public void setDao(GenericDao<T> dao) {
		this.dao = dao;
	}
	
	
	@POST
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response create(T entity) {
		dao.create(entity);
		return Response.created(UriBuilder.fromPath("/" + entity.getId()).build()).build();
	}
	
	@GET
	@Path("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public T retrieve(@PathParam("id") Long id) {
		return dao.retrieve(id);
	}
	
	
	@GET
	@com.alesaudate.samples.springjersey.services.Collection
	@PreAuthorize("hasRole('ROLE_USER')")
	public Collection<T> retrieve() {
		return dao.retrieve();
	}
	
	
	@PUT
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void update(T entity) {
		dao.update(entity);
	}
	
	
	@DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(T entity) {
		dao.delete(entity);
	}
	
	
	@DELETE
	@Path("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(@PathParam("id")Long id) {
		T entity = retrieve(id);
		delete(entity);
	}
	
	 

	
	
}

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.springframework.security.access.prepost.PreAuthorize;

import com.alesaudate.samples.springjersey.entities.HATEOASEntity;
import com.alesaudate.samples.springjersey.persistence.GenericDao;


@Consumes(MediaType.APPLICATION_XML) //Could easily be changed to JSON
@Produces(MediaType.APPLICATION_XML)
public abstract class BaseService<T extends HATEOASEntity> {
	
	
	
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
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		entity.setId(null);
		dao.create(entity);
		return Response.created(UriBuilder.fromPath("/" + entity.getId()).build()).build();
	}
	
	@GET
	@Path("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Response retrieve(@PathParam("id") Long id) {
		T entity = dao.retrieve(id);
		if (entity != null) {
			entity.createSelfLink();
			return Response.ok(entity).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	
	@GET
	@com.alesaudate.samples.springjersey.services.Collection
	@PreAuthorize("hasRole('ROLE_USER')")
	public Collection<T> retrieve(@QueryParam("page") int page) {
		Collection<T> entities = dao.retrieve(page);
		for (T entity : entities) {
			entity.createSelfLink();
		}
		return entities;
	}	
	
	@PUT
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response update(T entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		dao.update(entity);
		return Response.ok(entity).build();
	}
	
	
	@DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response delete(T entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		dao.delete(entity);
		return Response.ok().build();
	}
	
	
	@SuppressWarnings("unchecked")
	@DELETE
	@Path("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Response delete(@PathParam("id")Long id) {
		T entity = (T)retrieve(id).getEntity();
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		delete(entity);
		return Response.ok().build();
	}
	
	
}

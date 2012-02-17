package com.alesaudate.samples.springjersey.example;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alesaudate.samples.springjersey.services.BaseService;


@Path("/person")
@Service
public class PersonResource extends BaseService<Person, People> {
	
	
	
	public PersonResource() {
		super(People.class);
	}
	
	
	public PersonDAO getPersonDAO() {
		return (PersonDAO)getDao();
	}
	
	@Autowired
	public void setPersonDAO(PersonDAO dao) {
		setDao(dao);
	}	
	
	
	@PUT
	@Path("/{id}/portrait")
	@Consumes("image/*")
	public Response addPortrait(@PathParam("id") Long id, @QueryParam("description") String description, byte[] data, @Context HttpServletRequest request) {
		Person person = getPersonDAO().retrieve(id);
		if (person == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Portrait portrait = new Portrait();
		portrait.setData(data);
		portrait.setDescription(description);
		portrait.setMimeType(request.getContentType());
		person.setPortrait(portrait);
		getPersonDAO().update(person);
		return Response.ok().build();
	}
	
	
	@GET
	@Path("{id}/portrait")
	public Response getPortrait(@PathParam("id") Long id) {
		Person person = getPersonDAO().retrieve(id);
		if (person == null || person.getPortrait() == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(person.getPortrait().getData()).type(person.getPortrait().getMimeType()).build();
		
	}
	

}

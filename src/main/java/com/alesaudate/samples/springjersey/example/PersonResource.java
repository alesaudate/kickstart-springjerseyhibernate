package com.alesaudate.samples.springjersey.example;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alesaudate.samples.springjersey.services.BaseService;


@Path("/person")
@Component()
public class PersonResource extends BaseService<Person> {
	
	
	
	public PersonResource() {
		
	}
	
	
	public PersonDAO getPersonDAO() {
		return (PersonDAO)getDao();
	}
	
	@Autowired
	public void setPersonDAO(PersonDAO dao) {
		setDao(dao);
	}

}

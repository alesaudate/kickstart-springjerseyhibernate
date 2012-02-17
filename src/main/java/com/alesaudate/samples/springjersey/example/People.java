package com.alesaudate.samples.springjersey.example;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alesaudate.samples.springjersey.entities.EntityCollection;


@XmlRootElement
public class People extends EntityCollection<Person> {

	
	
	@Override
	@XmlElement(name="person")
	public Collection<Person> getEntities() {
		return super.getEntities();
	}
	
	
	/*
	 * Overriden to prevent JAXB's NPE
	 */
	@Override
	public void setEntities(Collection<Person> entities) {
		// TODO Auto-generated method stub
		super.setEntities(entities);
	}
}

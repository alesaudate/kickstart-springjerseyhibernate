package com.alesaudate.samples.springjersey.entities;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class EntityCollection<T> {
	
	
	private Collection<T> entities;
	
	private Collection<Link> links = new HashSet<Link>();
	
	
	@XmlElement(name="link", namespace="http://www.w3.org/1999/xlink")
	public Collection<Link> getLinks() {
		return links;
	}
	
	public void setLinks(Collection<Link> links) {
		this.links = links;
	}
	
	public Collection<T> getEntities() {
		return entities;
	}
	
	public void setEntities(Collection<T> entities) {
		this.entities = entities;
	}
	
	@Override
	public String toString() {
		try {
			JAXBContext context = JAXBContext.newInstance(getClass());
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(this, writer);
			return writer.getBuffer().toString();
		} catch (JAXBException e) {
			return super.toString();
		}
		
	}

}

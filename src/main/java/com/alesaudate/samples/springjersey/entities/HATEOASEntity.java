package com.alesaudate.samples.springjersey.entities;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;

public abstract class HATEOASEntity extends BaseEntity{
	
	@Transient
	private Collection<Link> links = new HashSet<Link>();
	
	
	@XmlElement(name="link", namespace="http://www.w3.org/1999/xlink")
	public Collection<Link> getLinks() {
		return links;
	}
	
	public void setLinks(Collection<Link> links) {
		this.links = links;
	}
	
	public HATEOASEntity addLink(Link link) {
		links.add(link);
		return this;
	}
	
	
	//The purpose of this method is to be overriden
	public void createStandardLinks() {
		createSelfLink();
	}
	
	public void createSelfLink() {
		addLink(new Link(UriBuilder.fromPath("/" + getId()).build().toASCIIString(), "self"));
	}

}

package com.alesaudate.samples.springjersey.example;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alesaudate.samples.springjersey.entities.BaseEntity;
import com.alesaudate.samples.springjersey.entities.HATEOASEntity;
import com.alesaudate.samples.springjersey.entities.Link;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person extends BaseEntity implements HATEOASEntity {
	
	private String name;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	private Portrait portrait;
	
	
	@Transient
	private Collection<Link> links = new HashSet<Link>();
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	@XmlTransient
	public Portrait getPortrait() {
		return portrait;
	}
	
	public void setPortrait(Portrait portrait) {
		this.portrait = portrait;
	}
	
	@Override
	public void createStandardLinks() {
		addLink(new Link(UriBuilder.fromPath(getId().toString()).build().toASCIIString(), "self"));
		if (getPortrait() != null) {
			Link portraitLink = new Link(UriBuilder.fromPath("{id}/portrait").build(getId()).toASCIIString(), "portrait");
			addLink(portraitLink);
			setPortrait(null);
		}
	}

	@Override
	public HATEOASEntity addLink(Link link) {
		this.links.add(link);
		return this;
	}
	
	@Override
	@XmlElement(name="link", namespace="http://www.w3.org/1999/xlink")
	public Collection<Link> getLinks() {
		return this.links;
	}
	
	@Override
	public void setLinks(Collection<Link> links) {
		this.links = links;
	}
	
}

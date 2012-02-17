package com.alesaudate.samples.springjersey.example;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alesaudate.samples.springjersey.entities.HATEOASEntity;
import com.alesaudate.samples.springjersey.entities.Link;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person extends HATEOASEntity {
	
	private String name;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	private Portrait portrait;
	
	
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
		super.createStandardLinks();
		if (getPortrait() != null) {
			//Link portraitLink = new Link(UriBuilder.fromResource(PersonResource.class).path("/" + getPortrait().getId()).build().toASCIIString(), "portrait");
			Link portraitLink = new Link(UriBuilder.fromPath("{id}/portrait").build(getId()).toASCIIString(), "portrait");
			addLink(portraitLink);
			setPortrait(null);
		}
	}

}

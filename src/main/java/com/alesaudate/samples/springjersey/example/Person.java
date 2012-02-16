package com.alesaudate.samples.springjersey.example;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.alesaudate.samples.springjersey.entities.HATEOASEntity;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Person extends HATEOASEntity {
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}

package com.alesaudate.samples.springjersey.example;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.alesaudate.samples.springjersey.entities.HATEOASEntity;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Portrait extends HATEOASEntity{
	
	
	private String mimeType;
	
	private byte[] data;
	
	private String description;

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}

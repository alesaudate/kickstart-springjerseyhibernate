package com.alesaudate.samples.springjersey.example;

import javax.persistence.Entity;

import com.alesaudate.samples.springjersey.entities.BaseEntity;


@Entity
public class Portrait extends BaseEntity {
	
	
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

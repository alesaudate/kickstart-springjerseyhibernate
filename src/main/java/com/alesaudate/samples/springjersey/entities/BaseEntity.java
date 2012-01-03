package com.alesaudate.samples.springjersey.entities;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public abstract class BaseEntity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate = new Date();
	
	private Boolean active = Boolean.TRUE;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date date) {
		this.updateDate = new Date();
	}
	
	public Boolean getActive() {
		return active;
	}
	
	

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}

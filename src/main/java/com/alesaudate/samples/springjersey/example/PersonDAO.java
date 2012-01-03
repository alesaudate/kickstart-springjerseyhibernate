package com.alesaudate.samples.springjersey.example;

import org.springframework.stereotype.Component;

import com.alesaudate.samples.springjersey.persistence.JpaDao;


@Component
public class PersonDAO extends JpaDao<Person>{

	
	public PersonDAO() {
		super(Person.class);
	}
	
}

package com.alesaudate.samples.springjersey.example;

import org.springframework.stereotype.Repository;

import com.alesaudate.samples.springjersey.persistence.JpaDao;


@Repository
public class PersonDAO extends JpaDao<Person>{

	
	public PersonDAO() {
		super(Person.class);
	}
	
}

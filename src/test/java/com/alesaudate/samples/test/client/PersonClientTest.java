package com.alesaudate.samples.test.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.security.core.codec.Base64;

import com.alesaudate.samples.springjersey.example.People;
import com.alesaudate.samples.springjersey.example.Person;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;

public class PersonClientTest extends BaseTestClient{
	
	
	@Test
	public void testInsertPerson() {
		WebResource resource = resource();
		Person person = new Person();
		person.setName("Test person");
		ClientResponse clientResponse = resource.post(ClientResponse.class, person);
		String location = clientResponse.getHeaders().getFirst("Location");
		Assert.assertNotNull(location);
		
		location = location.substring(getPath().length() + getResourcePath().length());
		
		Person newPerson = resource.path(location).get(Person.class);
		
		Assert.assertNotNull(newPerson);
		Assert.assertEquals(person.getName(), newPerson.getName());
		
	}
	
	
	@Test
	public void testInsertPeople() {
		
		Person p1 = createPerson("Person 1");
		Person p2 = createPerson("Person 2");
		
		People people = resource().get(People.class);
		
		Assert.assertTrue(people.getEntities().contains(p1));
		Assert.assertTrue(people.getEntities().contains(p2));
		
	}
	
	@Test
	public void testPagingInsertPeople() {
		for (int i = 0; i < 21; i++) { //Each page is 20 in size
			createPerson(UUID.randomUUID().toString());
		}
		
		People people = resource().queryParam("page", "0").get(People.class);
		Assert.assertNotNull(people);
		Assert.assertNotNull(people.getEntities());
		Assert.assertEquals(20, people.getEntities().size());
		
		people = resource().queryParam("page", "1").get(People.class);
		Assert.assertNotNull(people);
		Assert.assertNotNull(people.getEntities());
		Assert.assertEquals(1, people.getEntities().size());
		
		
	}
	
	@Test
	public void testInsertPeopleWithSameName() {
		Person p1 = createPerson("Person");
		Person p2 = createPerson(p1.getName());
		
		Assert.assertFalse(p1.getId().equals(p2.getId()));
		
		
		People people = resource().get(People.class);
		
		
		Assert.assertTrue(people.getEntities().contains(p1));
		Assert.assertTrue(people.getEntities().contains(p2));
	}
	
	
	@Test
	public void testUpdatePerson() {
		Person p1 = createPerson("Person");
		p1.setName("Person2");
		
		
		Person p2 = resource().put(Person.class, p1);
		Assert.assertEquals(p1.getId(), p2.getId());
		Assert.assertEquals(p1.getName(), p2.getName());
		Assert.assertEquals(p1.getCreationDate(), p2.getCreationDate());
		Assert.assertFalse(p1.getUpdateDate().equals(p2.getUpdateDate()));
	}
	
	
	@Test
	public void testDeletePerson() {
		Person p1 = createPerson("Person");
		ClientResponse clientResponse = resource().path(p1.getId().toString()).delete(ClientResponse.class);
		
		Assert.assertEquals(Status.OK.getStatusCode(), clientResponse.getStatus());
		clientResponse = resource().path(p1.getId().toString()).delete(ClientResponse.class);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), clientResponse.getStatus());
		
		clientResponse = resource().path(p1.getId().toString()).get(ClientResponse.class);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), clientResponse.getStatus());		
	}
	
	
	@Test
	public void testAddPortrait() throws IOException {
		BufferedInputStream bis = new BufferedInputStream(PersonClientTest.class.getResourceAsStream("/smurf-daddy.jpg"));
		byte[] data = new byte[bis.available()];
		bis.read(data);
		Person p1 = createPerson("Smurf Daddy");
		ClientResponse response = resource().path("/" + p1.getId() + "/portrait").type("image/jpeg").put(ClientResponse.class, data);
		Assert.assertEquals(200, response.getStatus());
		response = resource().path("/" + p1.getId() + "/portrait").get(ClientResponse.class);
		Assert.assertEquals(200, response.getStatus());
		byte[] returnedData = response.getEntity(byte[].class);
		Assert.assertTrue(Arrays.equals(returnedData, data));
		
		
	}
	
	protected Person createPerson(String name) {
		WebResource resource = resource();
		Person person = new Person();
		person.setName(name);
		ClientResponse clientResponse = resource.post(ClientResponse.class, person);
		String location = clientResponse.getHeaders().getFirst("Location");		
		location = location.substring(getPath().length() + getResourcePath().length());
		Person newPerson = resource.path(location).get(Person.class);
		Assert.assertNotNull(newPerson);
		return newPerson;
	}
	
	
	protected String getResourcePath() {
		return "/person";
	}

	
	
	/*
	 * Para ver a aplicação em funcionamento, inicialize o conteiner com a aplicação e, em seguida,
	 * rode este método. Na sequencia, visite as seguintes URL's (pelo browser):
	 * 
	 * http://localhost:8080/springjerseyhibernate/person/1
	 * http://localhost:8080/springjerseyhibernate/person/1/portrait
	 */
	public static void main(String[] args) throws IOException {
		new PersonClientTest() {
			@Override
			public WebResource resource() {
				Client client = Client.create();
				client.addFilter(new ClientFilter() {
					
					@Override
					public ClientResponse handle(ClientRequest cr)
							throws ClientHandlerException {
						
						cr.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode("admin:admin".getBytes())));
						return getNext().handle(cr);
					}
				});
				return client.resource("http://localhost:8080/springjerseyhibernate/person");
			}
		}.testAddPortrait();
	}
	
	
	
}

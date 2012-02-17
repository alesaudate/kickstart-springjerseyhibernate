package com.alesaudate.samples.test.client;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

import com.alesaudate.samples.springjersey.example.People;
import com.alesaudate.samples.springjersey.example.Person;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;




public abstract class BaseTestClient extends JerseyTest {
	
	private String contextPath;
	
	public BaseTestClient() {
		super(new WebAppDescriptor.Builder("com.alesaudate.samples")
        .contextPath("/springjerseyhibernate")
        .contextParam("contextConfigLocation", "/applicationContext.xml")
        .servletClass(SpringServlet.class)
        .contextListenerClass(ContextLoaderListener.class)
        .build());
		this.contextPath = "/springjerseyhibernate";
	}
	
	protected String getPath() {
		return super.getBaseURI().toASCIIString() + this.contextPath;
	}
	
	@Override
	protected TestContainerFactory getTestContainerFactory()
			throws TestContainerException {
		return new GrizzlyWebTestContainerFactory();
	}
	
	
	@Override
	public WebResource resource() {
		
		return super.resource().path(getResourcePath());
	}
	
	protected abstract String getResourcePath();

}

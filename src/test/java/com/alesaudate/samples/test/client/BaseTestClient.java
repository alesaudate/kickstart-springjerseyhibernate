package com.alesaudate.samples.test.client;

import org.springframework.web.context.ContextLoaderListener;

import com.sun.jersey.api.client.WebResource;
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
        .contextPath("/springhibernate")
        .contextParam("contextConfigLocation", "/applicationContext.xml")
        .servletClass(SpringServlet.class)
        .contextListenerClass(ContextLoaderListener.class)
        .build());
		this.contextPath = "/springhibernate";
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

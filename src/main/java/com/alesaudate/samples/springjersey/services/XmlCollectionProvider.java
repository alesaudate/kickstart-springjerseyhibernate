package com.alesaudate.samples.springjersey.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Component;


@Provider
@Produces(MediaType.APPLICATION_XML)
@Component
public class XmlCollectionProvider implements MessageBodyWriter<Collection<?>> {

	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		boolean canWrite = Collection.class.isAssignableFrom(type);
		boolean containsAnnotation = false;
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(com.alesaudate.samples.springjersey.services.Collection.class)) {
				containsAnnotation = true;
			}
		}
		if (canWrite && !containsAnnotation) {
			System.err.println("Não é possível escrever o resultado de uma Collection porque a anotação @Collection não foi detectada.");
		}
		return  canWrite && containsAnnotation;
	}

	public long getSize(Collection<?> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1L;
	}

	@SuppressWarnings("rawtypes")
	public void writeTo(Collection<?> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		try {
			ParameterizedType param = (ParameterizedType)genericType;
			Type actualType = param.getActualTypeArguments()[0];
			
			JAXBContext context = JAXBContext.newInstance((Class)actualType);
			StringBuilder builder = new StringBuilder();
			
			com.alesaudate.samples.springjersey.services.Collection annotation = null;
			
			for (Annotation ann : annotations) {
				if (ann.annotationType().equals(com.alesaudate.samples.springjersey.services.Collection.class)) {
					annotation = (com.alesaudate.samples.springjersey.services.Collection)ann;
					break;
				}
			}
			
			String name = annotation.value();
			if (name.trim().equals("")) {
				name = ((Class)actualType).getSimpleName().toLowerCase() + "s";
			}
			
			if (!t.isEmpty()) {
				
				
				builder.append("<").append(name).append(">");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
				for (Object value : t ) {
					marshaller.marshal(value, baos);
				}
				String xmlNode = new String(baos.toByteArray());
				builder.append(xmlNode);
				builder.append("</").append(name).append(">");
			}
			else {
				builder.append("<").append(name).append("/>");
			}
			byte[] bytes = builder.toString().getBytes();
			entityStream.write(bytes);
			entityStream.flush();
			
			
		} catch (Exception ex) {
			throw new WebApplicationException(ex, 500);
		}
		
	}
	
	



}

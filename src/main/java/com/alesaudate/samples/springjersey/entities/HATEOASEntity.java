package com.alesaudate.samples.springjersey.entities;

import java.util.Collection;

public interface HATEOASEntity {
	
	public Collection<Link> getLinks() ;
	
	public void setLinks(Collection<Link> links) ;
	
	public HATEOASEntity addLink(Link link) ;
	
	public void createStandardLinks() ;

}

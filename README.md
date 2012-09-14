## Notes of changes

This is a quick start guide for developing and running a REST application
with JAX-RS backed by Jersey. 

Feel free to modify it to your own needs. The package ::com.alesaudate.samples.springjersey.example:: contains stuff to demonstrate how to use this quickstart - you should delete it to use in your projects. 

Also, if you want to rename the packages, just make it sure that spring will keep track of the changes. Spring files to be modified are applicationContext.xml and security-context.xml. 

If you want to change database stuff, just place your modifications at persistence.xml file. 

## Running the sample

In order to see the app running, initialize the container with this app
(you may use mvn jetty:run), and, next, run the main method in the class
PersonClientTest. 

Then, visit the following URL's (on browser - you will need to use 
username and password. Use admin - admin):
 
 * http://localhost:8080/springhibernate/person
 * http://localhost:8080/springhibernate/person/1
 * http://localhost:8080/springhibernate/person/1/portrait

Also, you may want to check out the application's WADL. Just point your browser to http://localhost:8080/springhibernate/application.wadl to see it (you must not authenticate to do so). 

## What is built-in

The following stuff are used:

 * JAX-RS (REST Services backed by Jersey - JAX-RS's Reference Implementation)
 * JPA (backed by Hibernate)
 * Spring core (to wire everything)
 * Spring security (to protect the resources).

These stuff will give you some of the blueprints on REST concepts, like using a WADL and HATEOAS. The HATEOAS stuff is not fully finished (it needs little adjustments when referencing other stuff), but it definitely helps giving you a hint on what is HATEOAS and why it is a blueprint on REST. 

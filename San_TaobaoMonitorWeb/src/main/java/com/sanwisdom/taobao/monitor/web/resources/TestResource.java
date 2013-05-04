package com.sanwisdom.taobao.monitor.web.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Path("/hellosanwisdom")
@Component
@Scope("request")
public class TestResource {
	 // The Java method will process HTTP GET requests
	 @GET 
     // The Java method will produce content identified by the MIME Media
     // type "text/plain"
     @Produces("text/plain")
     public String getClichedMessage() {
         return "Hello Sanwisdom!";
     }
}

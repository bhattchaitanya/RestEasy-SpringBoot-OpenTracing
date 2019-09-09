package com.sample.app.endpoint.Phone;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Echo REST endpoint class
 **/
@Component
@Path("/phone")
public interface Phone {

    @GET
    @Path("/phone")
    @Produces({ MediaType.APPLICATION_JSON })
    public String phone();

}

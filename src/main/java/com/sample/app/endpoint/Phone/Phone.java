package com.sample.app.endpoint.address;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Echo REST endpoint class
 **/
@Component
@Path("/timestamp")
public interface Address {

    @GET
    @Path("/timestamp")
    @Produces({ MediaType.APPLICATION_JSON })
    public long timestamp();

}

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
@Path("/address")
public interface Address {

    @GET
    @Path("/address")
    @Produces({ MediaType.APPLICATION_JSON })
    public String getAddress();

}

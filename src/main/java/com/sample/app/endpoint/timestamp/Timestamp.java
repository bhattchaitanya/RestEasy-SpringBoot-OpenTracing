package com.sample.app.endpoint.timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Echo REST endpoint class
 **/
@Component
public interface Timestamp {

    @GET
    @Path("/timestamp")
    @Produces({ MediaType.APPLICATION_JSON })
    public long timestamp();

}

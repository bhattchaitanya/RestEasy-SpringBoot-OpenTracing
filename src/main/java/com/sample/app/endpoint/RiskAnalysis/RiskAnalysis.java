package com.sample.app.endpoint.RiskAnalysis;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Echo REST endpoint class
 **/
@Component
@Path("/riskanalysis")
public interface RiskAnalysis {

    @GET
    @Path("/riskanalysis")
    @Produces({ MediaType.APPLICATION_JSON })
    public String riskanalysis();

}

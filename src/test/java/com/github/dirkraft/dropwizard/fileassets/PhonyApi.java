package com.github.dirkraft.dropwizard.fileassets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api")
public class PhonyApi {

    @GET
    public String hello() {
        return "Hi, you've reached all of the API!";
    }
}

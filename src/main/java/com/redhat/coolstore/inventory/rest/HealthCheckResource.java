package com.redhat.coolstore.inventory.rest;

import org.wildfly.swarm.health.Health;
import org.wildfly.swarm.health.HealthStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/health")
public class HealthCheckResource {

    @GET
    @Path("/status")
    @Health
    public HealthStatus check(){
        return HealthStatus.named("server-state").up();
    }
}

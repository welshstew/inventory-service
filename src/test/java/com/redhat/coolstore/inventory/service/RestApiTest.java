package com.redhat.coolstore.inventory.service;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.redhat.coolstore.inventory.RestApplication;
import com.redhat.coolstore.inventory.model.Inventory;
import com.redhat.coolstore.inventory.rest.InventoryResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RunWith(Arquillian.class)
public class RestApiTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, RestApplication.class.getPackage())
//                .addPackages(true, Inventory.class.getPackage())
//                .addPackages(true, InventoryResource.class.getPackage())
//                .addPackages(true, InventoryService.class.getPackage())
                .addAsResource("project-local.yml", "project-local.yml")
                .addAsResource("META-INF/test-persistence.xml",  "META-INF/persistence.xml")
                .addAsResource("META-INF/test-load.sql",  "META-INF/test-load.sql");
    }

    @CreateSwarm
    public static Swarm newContainer() throws Exception {
        return new Swarm().withProfile("local");
    }

    @Test
    @RunAsClient
    public void testRestService(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("/inventory").path("/123456");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        JsonObject value = Json.parse(response.readEntity(String.class)).asObject();

        assert(value.getInt("quantity", 0) == 99);
    }

    @Test
    @RunAsClient
    public void testHealthCheck(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("/health").path("/status");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        JsonObject value = Json.parse(response.readEntity(String.class)).asObject();
        assert(value.getString("result", "no").equalsIgnoreCase("up"));
    }

    @Test
    @RunAsClient
    public void testCreateInventory(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("/inventory");

        String input = "{\"itemId\": \"11111\", \"link\": \"hello\", \"location\": \"UK\", \"quantity\": 12 }";
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON), Response.class);

        System.out.println(response);
        assert(response.getStatus() == 200);
        if (response.getStatus() == 200) {
            System.out.println("postess");

        }
    }

}

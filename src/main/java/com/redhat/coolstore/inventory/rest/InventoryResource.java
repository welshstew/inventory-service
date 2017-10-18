package com.redhat.coolstore.inventory.rest;

import com.redhat.coolstore.inventory.model.Inventory;
import com.redhat.coolstore.inventory.service.InventoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/inventory")
public class InventoryResource {

    @Inject
    InventoryService inventoryService;

    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Inventory getInventory(@PathParam("itemId") String itemId) {
        return inventoryService.getInventory(itemId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(Inventory item) {
        inventoryService.createInventory(item);
        return Response.ok().build();
    }
}

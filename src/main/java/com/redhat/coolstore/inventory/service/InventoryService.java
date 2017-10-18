package com.redhat.coolstore.inventory.service;

import com.redhat.coolstore.inventory.model.Inventory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class InventoryService {

    @PersistenceContext(name = "primary")
    EntityManager em;

    public Inventory getInventory(String itemId) {
        Inventory inventory = em.find(Inventory.class, itemId);
        return inventory;
    }

    @Transactional
    public void createInventory(Inventory item){
        em.persist(item);
    }

}

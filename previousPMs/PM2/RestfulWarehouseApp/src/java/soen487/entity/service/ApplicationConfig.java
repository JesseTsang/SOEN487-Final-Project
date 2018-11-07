/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soen487.entity.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Moh
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(soen487.entity.service.InventoryFacadeREST.class);
        resources.add(soen487.entity.service.ManufacturerFacadeREST.class);
        resources.add(soen487.entity.service.ProductFacadeREST.class);
        resources.add(soen487.entity.service.PurchaseOrderFacadeREST.class);
        resources.add(soen487.entity.service.RetailerFacadeREST.class);
        resources.add(soen487.entity.service.WarehouseFacadeREST.class);
    }
    
}

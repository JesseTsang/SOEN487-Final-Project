/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retailer.entity.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import retailer.entity.Inventory;
import retailer.entity.Retailer;
import retailer.entity.Warehouse;

/**
 *
 * @author edwinyachoui
 */
@Stateless
@Path("retailer.entity.inventory")
public class InventoryFacadeREST extends AbstractFacade<Inventory> {

    @PersistenceContext(unitName = "RetailerWebServicePU")
    private EntityManager em;

    public InventoryFacadeREST() {
        super(Inventory.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Inventory entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Inventory entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Inventory find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Inventory> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Inventory> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
@GET
    @Path("warehouses/{YYY}/products")
    @Produces({"application/json"})
    public List<Inventory> getProductByType(@PathParam("YYY") Integer warehouseID) {
       Warehouse w = new Warehouse();
       w.setId(warehouseID);
       
       TypedQuery<Inventory> warehouseProducts = em.createNamedQuery("Inventory.findByWarehouseID", Inventory.class)
                .setParameter("warehouseId", w);
       
       List<Inventory> warehouseResult = warehouseProducts.getResultList();
       
       return warehouseResult;
    }
    
    @GET
    @Path("retailer/products/{XXX}")
    @Produces({"application/json"})
    public List<Retailer> getProductByRetailerId(@PathParam("XXX") Integer retailerid) {
       Retailer r = new Retailer();
       r.setId(retailerid);
       
       TypedQuery<Retailer> retailerProducts = em.createNamedQuery("Retailer.findProductsByRetailerID", Retailer.class)
                .setParameter("retailerid", r);
       
       List<Retailer> retailerResult = retailerProducts.getResultList();
       
       return retailerResult;
    }
    
}

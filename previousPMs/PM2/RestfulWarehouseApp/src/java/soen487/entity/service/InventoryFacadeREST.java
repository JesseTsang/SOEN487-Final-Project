package soen487.entity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
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
import soen487.entity.Inventory;
import soen487.entity.Manufacturer;
import soen487.entity.Product;
import soen487.entity.Retailer;
import soen487.entity.Warehouse;

/**
 *
 * @author Moh
 */
@Stateless
@Path("soen487.entity.inventory")
//@Path("warehouses")
public class InventoryFacadeREST extends AbstractFacade<Inventory> {
    @EJB
    private RetailerFacadeREST retailerFacadeREST;
    @PersistenceContext(unitName = "RestfulWarehouseAppPU")
    private EntityManager em;

    public InventoryFacadeREST() {
        super(Inventory.class);
    }

    // a retailer establishes a relationship with a warehouse (submits retailer’s id and warehouse id)
    @POST
    @Path("warehouses/{YYY}/retailer/add/{RRR}")
    @Consumes({"text/plain" , "application/xml", "application/json"})
    public void createRetailer(@PathParam("RRR") Integer retailerID, @PathParam("YYY") Integer warehouseID) {
        Warehouse w = new Warehouse();
        w.setId(warehouseID);
        Retailer r = new Retailer();        
        r.setRetailerid(retailerID);
        r.setWarehouseid(warehouseID);
        retailerFacadeREST.create(r);
    }   
    
    // a retailer disassociates from the warehouse (submits retailer id)
    @DELETE
    @Path("warehouses/{YYY}/retailer/delete/{RRR}")
    public void removeRetailerWarehouseID(@PathParam("YYY") Integer warehouseID, @PathParam("RRR") Integer retailerID) {
        TypedQuery<Retailer> query = em.createNamedQuery("Retailer.removeRetailerWarehouseRelationship", Retailer.class)
            .setParameter("retailerid", retailerID);
        query.executeUpdate();       
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Override
    public void create(Inventory inventory) {
        super.create(inventory);
    }
   
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
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
    @Produces({"application/xml", "application/json"})
    public Inventory find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
     // 2.3 (b.i)
    // TO-TEST uri = warehouses/1/product/81
    @GET
    @Path("warehouses/{YYY}/product/{PPP}")
    @Produces({"application/xml","text/plain", "application/json"})
    public Inventory getProductByID(@PathParam("YYY") Integer warehouseID, @PathParam("PPP") Integer productID) {
       Warehouse w = new Warehouse();
       w.setId(warehouseID);
       Product p  = new Product();
       p.setId(productID);
       TypedQuery<Inventory> query1 = em.createNamedQuery("Inventory.findByWarehouseIDAndProductID", Inventory.class)
                .setParameter("warehouseId", w)
                .setParameter("productId", p);
       List<Inventory> result = query1.getResultList();
       for (Inventory inv : result) {
        return super.find(inv.getInventoryid());
       }  
       return null;
    }
    // 2.3 (b.ii)
    // TO-TEST uri = warehouses/1/products/TV
    @GET
    @Path("warehouses/{YYY}/products/{TTT}")
    @Produces({"application/xml", "text/plain", "application/json"})
    public List<Inventory> getProductByType(@PathParam("YYY") Integer warehouseID, @PathParam("TTT") String productType) {
       Warehouse w = new Warehouse();
       w.setId(warehouseID);
       Product p  = new Product();
       p.setProductType(productType);
       
       TypedQuery<Inventory> query1 = em.createNamedQuery("Inventory.findByWarehouseID", Inventory.class)
                .setParameter("warehouseId", w);
       List<Inventory> result1 = query1.getResultList();
       
        TypedQuery<Product> query2 = em.createNamedQuery("Product.findByProductType", Product.class)
                .setParameter("productType", productType);
        List<Product> result2 = query2.getResultList();
        
        List<Inventory> resultSet = new ArrayList();
        
        for (Inventory inv : result1) {
            for (Product pro : result2) {
                if (Objects.equals(inv.getProductId().getId(), pro.getId())) {
                    resultSet.add(inv);
                }
            }
        }            
       return resultSet;
    }
    
    // 2.3 (b.iii)
    // TO-TEST uri = warehouses/1/manufacturers/30
    @GET
    @Path("warehouses/{YYY}/manufacturers/{ZZZ}")
    @Produces({"application/xml", "text/plain", "application/json"})
    public List<Inventory> getManufacturerByWarehouseId(@PathParam("YYY") Integer warehouseID, @PathParam("ZZZ") Integer manufacturerID) {
        Warehouse w = new Warehouse();
        w.setId(warehouseID);
        Product p  = new Product();
        
        TypedQuery<Inventory> query1 = em.createNamedQuery("Inventory.findByWarehouseID", Inventory.class)
            .setParameter("warehouseId", w);
        List<Inventory> result1 = query1.getResultList();
       
        TypedQuery<Product> query2 = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> result2 = query2.getResultList();
        
        TypedQuery<Manufacturer> query3 = em.createNamedQuery("Manufacturer.findById", Manufacturer.class)
                  .setParameter("id", manufacturerID);
        List<Manufacturer> result3 = query3.getResultList();        
        
        List<Inventory> resultSet = new ArrayList();
        
        for (Inventory inv : result1) {
            for (Product pro : result2) {
                for (Manufacturer manu : result3) {
                    if (Objects.equals(inv.getProductId().getId(), pro.getId()) && 
                            Objects.equals(pro.getManufacturerid(), manu.getId())) {
                        resultSet.add(inv);
                    }
                }
            }
        }            
       return resultSet;
    }
    
    // 2.3 (b.iv)
    // TO-TEST uri = warehouses/1/manufacturers/30/products/20
    @GET
    @Path("warehouses/{YYY}/manufacturers/{ZZZ}/products/{PPP}")
    @Produces({"application/xml", "text/plain", "application/json"})
    public List<Inventory> getManufacturerByWarehouseId(@PathParam("YYY") Integer warehouseID, @PathParam("ZZZ") Integer manufacturerID, @PathParam("PPP") Integer productID) {
        Warehouse w = new Warehouse();
        w.setId(warehouseID);
        Product p  = new Product();
        p.setId(productID);
        TypedQuery<Inventory> query1 = em.createNamedQuery("Inventory.findByWarehouseIDAndProductID", Inventory.class)
            .setParameter("warehouseId", w)
            .setParameter("productId", p);
        List<Inventory> result1 = query1.getResultList();
       
        TypedQuery<Product> query2 = em.createNamedQuery("Product.findAll", Product.class);
        List<Product> result2 = query2.getResultList();
        
        TypedQuery<Manufacturer> query3 = em.createNamedQuery("Manufacturer.findById", Manufacturer.class)
            .setParameter("id", manufacturerID);
        List<Manufacturer> result3 = query3.getResultList();        
        
        List<Inventory> resultSet = new ArrayList();
        
        for (Inventory inv : result1) {
            for (Product pro : result2) {
                for (Manufacturer manu : result3) {
                    if (Objects.equals(inv.getProductId().getId(), pro.getId()) && 
                            Objects.equals(pro.getManufacturerid(), manu.getId())) {
                        resultSet.add(inv);
                    }
                }
            }
        }            
       return resultSet;
    }
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Inventory> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Inventory> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
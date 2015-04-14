/**
 * File: PriceWatchWS.java
 * 
 * This class contains the two operations of Price Watch.
 * 
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author Sushil Mohite
 */
@Path("resource")
public class PriceWatchWS {
    
    PWLogic pwLogic;

    /**
     * Creates a new instance of PriceWatchWS
     */
    public PriceWatchWS() {
        pwLogic = new PWLogic();
    }

    /**
     * Retrieves representation of an instance of PriceWatch.PriceWatchWS
     * @return an instance of java.lang.String
     */
//    @GET
//    @Produces("text/plain")
//    public String getText() {
//        //TODO return proper representation object
//        throw new UnsupportedOperationException();
//    }

    /**
     * PUT method for updating or creating an instance of PriceWatchWS
     * @param productId
     * @param price
     */
    @PUT
    @Consumes("text/plain")
    public void updatePrice(@QueryParam("productID") int productId, @QueryParam("price") double price) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(productId);
        productInfo.setPrice(price);
        pwLogic.setLatestPrice(productInfo.getProductId(), productInfo.getPrice());
        if (!pwLogic.containers[productInfo.getProductId()].contains(productInfo.getPrice())) {
            pwLogic.evaluate(productInfo);
        }
    }
    
    /**
     *
     */
    @PUT
    @Path("client")
    @Consumes("text/plain")
    public void subscribe() {
        
    }
}

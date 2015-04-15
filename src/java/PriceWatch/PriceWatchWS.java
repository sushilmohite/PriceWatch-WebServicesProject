/**
 * File: PriceWatchWS.java
 *
 * This class contains the two operations of Price Watch.
 *
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

import java.util.ArrayList;
import java.util.List;
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
     *
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
     *
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
    public void subscribe(@QueryParam("subscription") String subscription) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = new Predicate();
        String[] components = subscription.split(",");
        predicate.setDistance(Integer.parseInt(components[0]));
        predicate.setLatitude(Double.parseDouble(components[1]));
        predicate.setLongitude(Double.parseDouble(components[2]));
        int i = 3;
        while (true) {
            if (i >= components.length) {
                break;
            }
            predicate.setProductId(Integer.parseInt(components[i]));
            predicate.setPriceLow(Double.parseDouble(components[++i]));
            predicate.setPriceHigh(Double.parseDouble(components[++i]));
            predicates.add(predicate);
        }
        pwLogic.addTrigger(predicates);
    }
}

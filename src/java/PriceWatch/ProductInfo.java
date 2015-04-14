/**
 * File: ProductInfo.java
 * 
 * This class represents information of a product.
 * 
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

public class ProductInfo {
    
    private int productId;
    private double price;
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public double getPrice() {
        return this.price;
    }
    
}

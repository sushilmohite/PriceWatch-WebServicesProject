/**
 * File: Predicate.java
 * 
 * This class represents the predicate set by the user.
 * 
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

public class Predicate {
    
    private int userId;
    private int productId;
    //private String name
    private double priceLow;
    private double priceHigh;
    private int distance;
    private double latitude;
    private double longitude;
    private double targetLatitude;
    private double targetLongitude;
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public void setPriceLow(double priceLow) {
        this.priceLow = priceLow;
    }
    
    public void setPriceHigh(double priceHigh) {
        this.priceHigh = priceHigh;
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public void setTargetLatitude(double targetLatitude) {
        this.targetLatitude = targetLatitude;
    }
    
    public void setTargetLongitude(double targetLongitude) {
        this.targetLongitude = targetLongitude;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public double getPriceLow() {
        return this.priceLow;
    }
    
    public double getPriceHigh() {
        return this.priceHigh;
    }            
    
    public int getDistance() {
        return this.distance;
    }
    
    public double getLatitude() {
        return this.latitude;
    }
    
    public double getLongitude() {
        return this.longitude;
    }
    
    public double getTargetLatitude() {
        return this.targetLatitude;
    }
    
    public double getTargetLongitude() {
        return this.targetLongitude;
    }
    
    public int getUserId() {
        return this.userId;
    }
    
}

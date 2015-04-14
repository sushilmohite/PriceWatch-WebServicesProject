/**
 * File: SafeValueContainer.java
 * 
 * This class represents a safe value container of the SLIM architecture.
 * 
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

public class SafeValueContainer {
    
    private double low;
    private double high;
    
    public SafeValueContainer() {
        low = -1;
        high = -1;
    }
    
    public void set(double low, double high) {
        this.low = low;
        this.high = high;
    }
    
    public boolean contains(double value) {
        if ((low == -1) && (high == -1)) {
            return false;
        }
        
        return (value >= this.low) && (value <= this.high);
    }
} 

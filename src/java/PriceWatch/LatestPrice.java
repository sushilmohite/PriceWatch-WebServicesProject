/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PriceWatch;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author varun
 */
public class LatestPrice {

    Map<Integer, Double> prices;

    public LatestPrice() {
        prices = new HashMap<>();
    }
    public void setPrice(int productID, double price){
        prices.put(productID, price);
    }
    public double getPrice(int productID){
        return prices.get(productID);
    }
}

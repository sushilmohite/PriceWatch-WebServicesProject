/**
 * File: PWLogic.java
 *
 * This class contains the main logic of Price Watch implementation.
 *
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class PWLogic {

    List<Trigger> triggers;
    SafeValueContainer[] containers;
    LatestPrice latestprice;
    int products;

    public PWLogic() {
        products = 4;
        latestprice = new LatestPrice();
        triggers = new ArrayList<>();
        containers = new SafeValueContainer[4];
        for (int i = 0; i < containers.length; i++) {
            containers[i] = new SafeValueContainer();
        }
    }

    /**
     * This method adds a new trigger with a predicate
     *
     * @param predicate
     */
    public void addTrigger(List<Predicate> predicates) {
        Trigger trigger = new Trigger();
        for (Predicate predicate : predicates) {
            trigger.setPredicate(predicate);
        }
        triggers.add(trigger);
    }

    /**
     * Evaluates whether any triggers should be fired or not.
     *
     * @param productInfo
     */
    public void evaluate(ProductInfo productInfo) {
        Predicate predicate;
        BitSet bitset = null;
        boolean firstBit = false;
        boolean secondBit = false;
        int counter = 0;
        for (Trigger trigger : triggers) {
            predicate = trigger.getPredicate(productInfo.getProductId());
            if (predicate == null) {
                continue;
            }
            if (predicate.getPriceLow() <= productInfo.getPrice() && productInfo.getPrice() <= predicate.getPriceHigh()) {
                trigger.setBitSet(updateTriggerBits(trigger));
                bitset = trigger.getBitSet();
                bitset.set(productInfo.getProductId());
                trigger.setBitSet(bitset);
                firstBit = true;
                counter++;
            }

        }

        // Safe value container construction. type 1
        if (!firstBit) {
            setSafeValueForFirstCase(productInfo);
        }

        for (Trigger trigger : triggers) {
            predicate = trigger.getPredicate(productInfo.getProductId());
            if (predicate == null) {
                continue;
            }
            bitset = trigger.getBitSet();
            if (bitset.cardinality() == trigger.getPredicateSize()) {
                continue;
            }
            if (bitset.get(productInfo.getProductId())) {
                double distance = calculateDistance(predicate.getLatitude(), predicate.getLongitude(), predicate.getTargetLatitude(), predicate.getTargetLongitude());
                if (predicate.getDistance() <= distance) {
                    // if calculatedDistance is less than distance
                    trigger.setFireTrigger(true);
                    bitset.set(4);
                    trigger.setBitSet(bitset);
                    secondBit = true;
                }
            }
        }

        // Safe value container construction type 2
        if (firstBit && !secondBit) {
            if (counter == 1) {
                setSafeValueForSecondCase(productInfo);
            } else {
                setSafeValueForThirdCase(productInfo);
            }
        }

        // reset all the bitset values...
    }

    private void setSafeValueForThirdCase(ProductInfo productInfo) {
        Predicate predicate;
        double lowMin = Double.MAX_VALUE;
        double lowPrice = 0;
        double highMin = Double.MAX_VALUE;
        double highPrice = Double.MAX_VALUE;
        double value;
        for (Trigger trigger : triggers) {
            predicate = trigger.getPredicate(productInfo.getProductId());
            if (predicate == null) {
                continue;
            }
            value = productInfo.getPrice() - predicate.getPriceHigh();
            if ((value > 0) && (value < lowMin)) {
                lowMin = value;
                lowPrice = predicate.getPriceHigh();
            }

            value = productInfo.getPrice() - predicate.getPriceLow();
            if ((value > 0) && (value < lowMin)) {
                lowMin = value;
                lowPrice = predicate.getPriceLow();
            }

            value = predicate.getPriceHigh() - productInfo.getPrice();
            if ((value > 0) && (value < highMin)) {
                highMin = value;
                highPrice = predicate.getPriceHigh();
            }

            value = predicate.getPriceLow() - productInfo.getPrice();
            if ((value > 0) && (value < highMin)) {
                highMin = value;
                highPrice = predicate.getPriceLow();
            }

        }

        if ((lowPrice != 0) && (highPrice != Double.MAX_VALUE)) {
            containers[productInfo.getProductId()].set(lowPrice, highPrice);
        }
    }

    private void setSafeValueForSecondCase(ProductInfo productInfo) {
        setSafeValueForFirstCase(productInfo);
    }

    private void setSafeValueForFirstCase(ProductInfo productInfo) {
        Predicate predicate;
        double lowMin = Double.MAX_VALUE;
        double lowPrice = 0;
        double highMin = Double.MAX_VALUE;
        double highPrice = Double.MAX_VALUE;
        double value;
        for (Trigger trigger : triggers) {
            predicate = trigger.getPredicate(productInfo.getProductId());
            if (predicate == null) {
                continue;
            }
            value = productInfo.getPrice() - predicate.getPriceHigh();
            if ((value > 0) && (value < lowMin)) {
                lowMin = value;
                lowPrice = predicate.getPriceHigh();
            }

            value = predicate.getPriceLow() - productInfo.getPrice();
            if ((value > 0) && (value < highMin)) {
                highMin = value;
                highPrice = predicate.getPriceLow();
            }
        }
        if ((lowPrice != 0) && (highPrice != Double.MAX_VALUE)) {
            containers[productInfo.getProductId()].set(lowPrice, highPrice);
        }
    }
    /*
     * To set the latest price for a product 
     */

    public void setLatestPrice(int productId, double price) {
        latestprice.setPrice(productId, price);
    }

    /*
     * Updating the bitset for a trigger according as per the latest prices
     * @param trigger
     * @return bitset
     */
    public BitSet updateTriggerBits(Trigger trigger) {
        BitSet bitset = trigger.getBitSet();
        Predicate predicate;
        for (int i = 0; i < products; i++) {
            predicate = trigger.getPredicate(i);
            if (predicate == null) {
                continue;
            }
            if (latestprice.getPrice(i) >= predicate.getPriceLow() && latestprice.getPrice(i) <= predicate.getPriceHigh()) {
                bitset.set(i);
            } else {
                bitset.clear(i);
            }
        }
        return bitset;
    }

    /*
     * To calculate distance between user and Market
     */
    private double calculateDistance(double latitudeMobile, double longitudeMobile, double latitudeMarket, double longitudeMarket) {
        double t = longitudeMobile - longitudeMarket;
        double distance = Math.sin(degree2radians(latitudeMobile)) * Math.sin(degree2radians(latitudeMarket)) + Math.cos(degree2radians(latitudeMobile)) * Math.cos(degree2radians(latitudeMarket)) * Math.cos(degree2radians(t));
        distance = Math.acos(distance);
        distance = (distance * 180.0 / Math.PI);
        distance = distance * 60 * 1.1515;
        return (distance);
    }

    /*
     * To convert degrees to radians            
     */
    private double degree2radians(double deg) {
        double radians = (deg * Math.PI / 180.0);
        return radians;
    }
}

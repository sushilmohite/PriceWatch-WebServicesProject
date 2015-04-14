/**
 * File: Trigger.java
 *
 * This object of this class represents a trigger created for a user.
 *
 * @author Sushil Mohite, Varun Hegde and Siddesh Pillai
 */
package PriceWatch;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Trigger {

    private Map<Integer, Predicate> predicates;
    private BitSet bitset;
    private boolean fireTrigger;

    public Trigger() {
        predicates = new HashMap<Integer, Predicate>();
        bitset = new BitSet(5);
        fireTrigger = false;
    }

    public void setPredicate(Predicate predicate) {
        predicates.put(predicate.getProductId(), predicate);
    }

    public void setBitSet(BitSet bitset) {
        this.bitset = bitset;
    }

    public void setFireTrigger(boolean fireTrigger) {
        this.fireTrigger = fireTrigger;
    }

    public BitSet getBitSet() {
        return this.bitset;
    }

    public Predicate getPredicate(int productId) {
        return predicates.get(productId);
    }

    public boolean getFireTrigger() {
        return this.fireTrigger;
    }

    public int getPredicateSize() {
        return predicates.size();
    }
}

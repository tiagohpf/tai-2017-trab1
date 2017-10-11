package Utils;

/**
 * TAI, October 2017
 *
 * Assignment 1 - Finite-context model and automatic text generation
 *
 * @author Bárbara Jael, 73241, barbara.jael@ua.pt
 * @author Miriam Cardoso, 72181, miriamcardoso@ua.pt
 * @author Tiago Faria, 73714, tiagohpf@ua.pt
 */

// Class that creates a data structure to use a Pair<Key, Value>
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Constructor
     *
     * @param key
     * @param value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Get key of pair
     *
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Get value of pair
     *
     * @return value
     */
    public V getValue() {
        return value;
    }

    /**
     * Print the Pair
     *
     * @return result
     */
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}

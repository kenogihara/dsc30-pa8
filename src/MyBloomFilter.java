/*
 * Name: Ken Ogihara
 * PID: A16969236
 */

/**
 * MyBloomFilter implementation.
 *
 * @author Ken Ogihara
 * @since ${3/15/24}
 */

public class MyBloomFilter implements KeyedSet {

    private static final int DEFAULT_M = (int) 1e7;
    private static final int CONVERTER = 27;

    boolean[] bits;

    /**
     * Initialize MyBloomFilter with the default number of bits
     */
    public MyBloomFilter() {
        bits = new boolean[DEFAULT_M];
    }

    /**
     * Insert the string key into the bloom filter.
     *
     * @param key key to insert
     * @throws NullPointerException if value is null
     * @return true if the key was inserted, false if the key was already
     *         present
     */
    public boolean insert(String key) {
        if (key == null) {
            throw new NullPointerException("value is null");
        }
        if (bits[hashFuncA(key)] && bits[hashFuncB(key)] && bits[hashFuncC(key)]) {
            return true;
        }
        else {
            int hashedA = hashFuncA(key);
            int hashedB = hashFuncB(key);
            int hashedC = hashFuncC(key);

            bits[hashedA] = true;
            bits[hashedB] = true;
            bits[hashedC] = true;
            return true;
        }
    }

    /**
     * Check if the given key is present in the bloom filter.
     * @param key key to look up
     * @throws NullPointerException if value is null
     * @return true if the key was found, false if the key was not found
     */
    public boolean lookup(String key) {
        if (key == null) {
            throw new NullPointerException("value is null");
        }
        int hashedA = hashFuncA(key);
        int hashedB = hashFuncB(key);
        int hashedC = hashFuncC(key);

        return bits[hashedA] && bits[hashedB] && bits[hashedC];
    }

    /**
     * First hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncA(String value) {
        int hashValue = 0;
        for (int i = 0; i < value.length(); i++) {
            int leftShiftedValue = hashValue << 5;
            int rightShiftedValue = hashValue >>> 27;
            hashValue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        return Math.abs(hashValue % bits.length);
    }

    /**
     * Second hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncB(String value) {
        int hashVal = 0;
        for (int j = 0; j < value.length(); j++) {
            int letter = value.charAt(j);
            hashVal = (hashVal * CONVERTER + letter) % bits.length;
        }
        return hashVal;
    }

    /**
     * Third hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncC(String value) {
        int hashVal = 0;
        for (int j = 0; j < value.length(); j++) {
            int letter = value.charAt(j);
            hashVal = ((hashVal << 8) + letter) % bits.length;
        }
        return Math.abs(hashVal % bits.length);
    }
}

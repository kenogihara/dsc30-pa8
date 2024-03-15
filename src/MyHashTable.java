/*
 * Name: Ken Ogihara
 * PID: A16969236
 */

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Hash Table Implementation.
 * 
 * @author Ken Ogihara
 * @since ${3/7/24}
 */

public class MyHashTable implements KeyedSet {

    /* instance variables */
    private int size; // number of elements stored
    private LinkedList<String>[] table; // data table
    private int rehashCount;
    private int collisionCount;
    String statsLog = "";

    /* magic numbers */
    public static final int DEFAULT_CAPACITY = 20;
    public static final int MINIMUM_CAPACITY = 5;
    public static final int DOUBLE_SIZE = 2;
    //public static final int STARTING_POS = 0;

    /**
     * Initializes a hash table with capacity = 20.
     */
    public MyHashTable() {
        this(DEFAULT_CAPACITY);
        Arrays.setAll(table, i -> new LinkedList<String>());
    }

    /**
     * Initializes a hash table with a give capacity.
     *
     * @param capacity The initial capacity of the hash table.
     * @throws IllegalArgumentException if initial capacity is less than 5.
     */
    @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        if (capacity < MINIMUM_CAPACITY) {
            throw new IllegalArgumentException("initial total capacity must be greater than 5");
        }
        table = new LinkedList[capacity];
        Arrays.setAll(table, i -> new LinkedList<String>());
    }

    /**
     * Method that adds an item to the hash table.
     *
     * @param value String item.
     * @return whether or not the String value was inserted.
     * @throws NullPointerException if value is null.
     */
    public boolean insert(String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        int hashed = hashString(value);
        if (table[hashed].contains(value)) {
            return false;
        }
        if ((double) size / table.length > 1) {
            size = 0;
            rehash();
        }
        if (table[hashed] == null) {
            table[hashed] = new LinkedList<>();
        }
        if (!table[hashed].isEmpty()) {
            collisionCount++;
        }
        table[hashed].add(value);
        size++;
        return true;
    }

    /**
     * Method that removes the given value from the hash table.
     *
     * @param value String item.
     * @return whether or not the String value was deleted.
     * @throws NullPointerException if the value is null.
     */
    public boolean delete(String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        int hashed = hashString(value);
        if (table[hashed] == null) {
            return false;
        }
        if (!table[hashed].contains(value)) {
            return false;
        }
        if (table[hashed].contains(value)) {
            table[hashed].remove(value);
            size--;
        }
        return true;
    }

    /**
     * Method that checks is a particular value is in the hash table.
     *
     * @param value String item to be checked.
     * @return whether or not the value is in the hash table.
     * @throws NullPointerException if the value is null.
     */
    public boolean lookup(String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (table[hashString(value)] == null) {
            return false;
        }
        return (table[hashString(value)]).contains(value);
    }

    /**
     * Method that outputs an array of strings in the order in which they are located in the
     * hash table.
     *
     * @return an array of Strings.
     */
    public String[] returnAll() {
        int index = 0;
        String[] output = new String[size()];
        for (LinkedList<String> bucket : table) {
            if (bucket == null) {
                continue;
            }
            for (String string : bucket) {
                if (string == null) {
                    continue;
                }
                output[index] = string;
                index++;
            }
        }
        return output;
    }

    /**
     * Method that returns the number of elements in the hash table.
     *
     * @return an integer.
     */
    public int size() {
        return size;
    }

    /**
     * Method that returns the length of the hash table.
     *
     * @return an integer.
     */
    public int capacity() {
        return table.length;
    }

    /**
     * Method that returns statistics regarding the rehash count, load factor, and collision count.
     *
     * @return a String.
     */
    public String getStatsLog() {
        return statsLog;
    }


    /**
     * Utility function provided to help with debugging
     */
    public void printTable() {
        String s = "";
        for (int i = 0; i < table.length; i++) {
            s = s + i + ":";
            if (!table[i].isEmpty()) {
                for (String t : table[i])
                    s = s + " " + t + ",";
                // remove trailing comma
                s = s.substring(0, s.length() - 1);
            }
            s = s + "\n";
        }
        // remove trailing newline
        s = s.substring(0, s.length() - 1);
        System.out.println(s);
    }

    /**
     * Method that doubles the size of the capacity of the hash table once the load factor
     * exceeds 1.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        double loadFactor = (double) size / table.length;
        statsLog += String.format("Before rehash # %d: load factor %.2f, %s collision(s).\n",
                rehashCount, loadFactor, collisionCount);

        System.out.println(statsLog);
        LinkedList<String>[] newTable = table;
        table = new LinkedList[capacity() * DOUBLE_SIZE];
        Arrays.setAll(table, i -> new LinkedList<String>());
        for (LinkedList<String> bucket : newTable) {
            for (String string : bucket) {
                this.insert(string);
            }
        }
//        LinkedList<String>[] newTable = table;
//        table = new LinkedList[table.length * DOUBLE_SIZE];
//        Arrays.setAll(table, i -> new LinkedList<String>());
//        System.arraycopy(table, STARTING_POS, newTable, STARTING_POS, size);
        collisionCount = 0;
        rehashCount++;
    }

    /**
     * Hash function method (Cyclical Redundancy Check) that computes the hash value of the
     * given string value.
     *
     * @param value a String item.
     * @return the index location of the value.
     */
    private int hashString(String value) {
        int hashValue = 0;
        for (int i = 0; i < value.length(); i++) {
            int leftShiftedValue = hashValue << 5;
            int rightShiftedValue = hashValue >>> 27;
            hashValue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        return Math.abs(hashValue % table.length);
    }
}

/*
 * Name: Ken Ogihara
 * PID: A16969236
 */

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
    public static final int DEFAULT_CAPACITY = 20;
    public static final int MINIMUM_CAPACITY = 5;
    public static final int DOUBLE_SIZE = 2;
    public static final int STARTING_POS = 0;

    public MyHashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        if (capacity < MINIMUM_CAPACITY) {
            throw new IllegalArgumentException("initial total capacity must be greater than 5");
        }
        table = new LinkedList[capacity];
    }

    public boolean insert(String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if ((double) size / table.length > 1) {
            //getStatsLog();
            rehash();
            rehashCount++;
        }
        int hashed = hashString(value);
        if (table[hashed] == null) {
            table[hashed] = new LinkedList<>();
        }
        if (table[hashed].contains(value)) {
            return false;
        }
        if (!table[hashed].isEmpty()) {
            collisionCount++;
        }
        table[hashed].add(value);
        size++;
        return true;
    }

    public boolean delete(String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        int hashed = hashString(value);
        if (table[hashed] == null) {
            return false;
        }
        if (table[hashed].contains(value)) {
            table[hashed].remove(value);
            size--;
        }
        return true;
    }

    public boolean lookup(String value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        if (table[hashString(value)] == null) {
            return false;
        }
        return (table[hashString(value)]).contains(value);
    }

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

    public int size() {
        return size;
    }

    public int capacity() {
        return table.length;
    }

    public String getStatsLog() {
        double loadFactor = (double) size / table.length;
        String statsLog = String.format("Before rehash # %d: load factor %.2f, %s collision(s).\n",
                rehashCount, loadFactor, collisionCount);
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

    @SuppressWarnings("unchecked")
    private void rehash() {
        LinkedList<String>[] newTable = new LinkedList[table.length * DOUBLE_SIZE];
        for (LinkedList<String> bucket : table) {
            for (String string : bucket) {
                insert(string);
            }
        }
        table = newTable;
        collisionCount = 0;
    }

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

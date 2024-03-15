import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyHashTableTester {
    MyHashTable employees;
    MyHashTable countries;
    MyHashTable test;
    String[] names = {"aria", "ken", "ricky", "andrew", "arthur", "annie", "dom", "noah", "nacho"};
    String[] areas = {"america", "new zealand", "china", "switzerland", "ireland", "greenland",
            "japan", "zimbabwe", "nigeria", "korea"};
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        employees = new MyHashTable(10);
        countries = new MyHashTable(10);
        test = new MyHashTable();
    }
    @Test
    void constructorOne() {
        MyHashTable objectOne = new MyHashTable();
        assertEquals(20, objectOne.capacity());
        assertFalse(objectOne.lookup("random"));
        objectOne.insert("random");
        assertTrue(objectOne.lookup("random"));
    }
    @Test
    void constructorTwo() {
        MyHashTable objectTwo;
        assertThrows(IllegalArgumentException.class, () -> new MyHashTable(3));
        objectTwo = new MyHashTable(10);
        assertEquals(10, objectTwo.capacity());
        objectTwo.insert("random");
        assertEquals(1, objectTwo.size());
        objectTwo.insert("another random string");
        assertEquals(2, objectTwo.size());
    }
    @org.junit.jupiter.api.Test
    void insert() {
        for (String name : names) {
            employees.insert(name);
        }
        assertEquals(9, employees.size());
        assertEquals(1, employees.capacity() - employees.size());
        assertThrows(NullPointerException.class, () -> employees.insert(null));
        assertTrue(employees.insert("makoto")); //this is the 10th element we're inserting
        assertFalse(employees.insert("andrew"));
        assertTrue(employees.insert("leo")); //11th element; load factor should be 1.1
        assertEquals(11, employees.size());
        assertEquals(1, employees.size() / employees.capacity());
        assertTrue(employees.insert("harshil")); //12th element; should be rehashed
        System.out.println(employees.size()); // this should be 12
        System.out.println(employees.capacity()); // this should be 20
    }

    @org.junit.jupiter.api.Test
    void delete() {
        for (String country : areas) {
            countries.insert(country);
        }
        assertThrows(NullPointerException.class, () -> countries.delete(null));
        assertTrue(countries.delete("korea")); // so there should be 9 elements after this
        assertFalse(countries.delete("russia"));

        assertEquals(1, countries.capacity() - countries.size());
        countries.insert("france");
        countries.insert("germany");
        countries.insert("kazakstan");
        assertEquals(12, countries.size());
        assertEquals(20, countries.capacity());
        assertFalse(countries.delete("francee"));
    }

    @org.junit.jupiter.api.Test
    void lookup() {
        for (String country : areas) {
            countries.insert(country);
        }
        assertThrows(NullPointerException.class, () -> countries.lookup(null));
        assertTrue(countries.lookup("japan"));
        assertTrue(countries.lookup("china"));
        assertFalse(countries.lookup("United States of America"));
    }

    @org.junit.jupiter.api.Test
    void returnAll() {
        for (String name : names) {
            employees.insert(name);
        }
        assertArrayEquals(new String[] {"ken", "noah", "aria", "andrew", "nacho", "arthur", "ricky", "annie", "dom"},
        (employees.returnAll()));
        employees.insert("harshil");
        //System.out.println(Arrays.toString(employees.returnAll()));
        assertArrayEquals(new String[] {"ken", "noah", "aria", "andrew", "harshil", "nacho",
                        "arthur", "ricky", "annie", "dom"},
                (employees.returnAll()));
        countries.insert("United States of America");
        assertArrayEquals(new String[] {"United States of America"}, countries.returnAll());
    }

    @org.junit.jupiter.api.Test
    void size() {
        for (String country : areas) {
            countries.insert(country);
        }
        countries.insert("portugal");
        assertEquals(11, countries.size());
        countries.insert("argentina");
        assertEquals(12, countries.size());
        countries.insert("chile");
        assertEquals(13, countries.size());
    }

    @org.junit.jupiter.api.Test
    void capacity() {
        assertEquals(20, test.capacity());
        for (String country : areas) {
            test.insert(country);
        }
        for (String name : names) {
            test.insert(name);
        }
        //there should be 19 elements currently in test object
        String[] characters = {"mario", "luigi", "wario"};
        for (String character : characters) {
            test.insert(character);
        }
        assertEquals(40, test.capacity());
        assertEquals(22, test.size());
        assertEquals(10, countries.capacity());
        assertEquals(10, employees.capacity());
    }

    @org.junit.jupiter.api.Test
    void getStatsLog() {
        for (String country : areas) {
            countries.insert(country);
        }
        countries.insert("canada");
        countries.insert("germany");
        System.out.println(employees.getStatsLog());

    }

    @org.junit.jupiter.api.Test
    void printTable() {
        employees.printTable();
    }
}
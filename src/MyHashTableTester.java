import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyHashTableTester {
    MyHashTable employees;
    MyHashTable countries;
    String[] names = {"aria", "ken", "ricky", "andrew", "arthur", "annie", "dom", "noah", "nacho"};
    String[] areas = {"america", "new zealand", "china", "switzerland", "ireland", "greenland",
            "japan", "zimbabwe", "nigeria", "korea"};
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        employees = new MyHashTable(10);
        countries = new MyHashTable(10);
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
    }
    @org.junit.jupiter.api.Test
    void insert() {
        for (String name : names) {
            employees.insert(name);
        }
        assertEquals(9, employees.size());
        assertEquals(1, employees.capacity() - employees.size());
        assertThrows(NullPointerException.class, () -> employees.insert(null));
        assertTrue(employees.insert("makoto"));
        assertFalse(employees.insert("andrew"));
        employees.insert("leo");
        System.out.println(employees.size());
        System.out.println(employees.capacity());
        System.out.println(employees.getStatsLog());
    }

    @org.junit.jupiter.api.Test
    void delete() {
    }

    @org.junit.jupiter.api.Test
    void lookup() {
    }

    @org.junit.jupiter.api.Test
    void returnAll() {
    }

    @org.junit.jupiter.api.Test
    void size() {
    }

    @org.junit.jupiter.api.Test
    void capacity() {
    }

    @org.junit.jupiter.api.Test
    void getStatsLog() {
    }

    @org.junit.jupiter.api.Test
    void printTable() {
    }
}
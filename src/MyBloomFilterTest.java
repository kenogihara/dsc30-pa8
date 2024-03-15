import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyBloomFilterTest {

    MyBloomFilter testing;
    String[] names = {"aria", "ken", "ricky", "andrew", "arthur", "annie", "dom", "noah", "nacho"};


    @BeforeEach
    void setUp() {
        testing = new MyBloomFilter();
    }

    @Test
    void insert() {
        for (String name : names) {
            assertTrue(testing.insert(name));
        }
    }

    @Test
    void lookup() {
        for (String name : names) {
            assertTrue(testing.insert(name));
        }
        for (String name : names) {
            assertTrue(testing.lookup(name));
        }
        assertFalse(testing.lookup("RNAfnfdsfdsuofjsdoifs"));
    }
}
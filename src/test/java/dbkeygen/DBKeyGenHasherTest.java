package dbkeygen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBKeyGenHasherTest {

    @Test
    void testIndex() {
        String output = DBKeyGenHasher.createIndexHash("my_table", new String[]{"col1", "col2"});
        assertFalse(output.isEmpty());
        assertTrue(output.startsWith("IDX"), "Output should start with IDX, but was: " + output);
        // Hibernate 5.6.15.Final generates a hash. Let's check length at least.
        assertTrue(output.length() > 5);
        assertEquals("IDX5f5og2p8wvf3p3shlhrb8fx2x", output);
    }

    @Test
    void testUnique() {
        String output = DBKeyGenHasher.createUniqueHash("my_table", new String[]{"col1"});
        assertFalse(output.isEmpty());
        assertTrue(output.startsWith("UK"), "Output should start with UK, but was: " + output);
        assertTrue(output.length() > 5);
        assertEquals("UK332ld5510d7vnvpktjjap15kp", output);
    }

    @Test
    void testForeign() {
        String output = DBKeyGenHasher.createForeignKeyHash("my_table", "ref_table", new String[]{"col1"});
        assertFalse(output.isEmpty());
        assertTrue(output.startsWith("FK"), "Output should start with FK, but was: " + output);
        assertTrue(output.length() > 5);
        assertEquals("FKs723aqufpqcu75juluyhxw8rx", output);
    }

}

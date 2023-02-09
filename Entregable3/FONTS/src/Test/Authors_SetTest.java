package Test;

import Domain.Author;
import Domain.AuthorsSet;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class Authors_SetTest extends TestCase {

    private AuthorsSet auths;
    @Before
    public void setup() {
        auths = new AuthorsSet();
    }

    @Test
    public void testAdd() {
        setup();
        boolean add = auths.add(null);
        assertFalse(add);
        boolean add1 = auths.add("Marta");
        assertTrue(add1);
    }

    @Test
    public void testExistsAutor() {
        setup();
        auths.add("Pere");
        auths.add("Carmen");
        assertTrue(auths.exist("Pere"));
        assertTrue(auths.exist("Carmen"));
        assertFalse(auths.exist("Pepe"));
        assertFalse(auths.exist("Marta"));
    }


    @Test
    public void testGetAutor() {
        setup();
        auths.add("Pere");
        auths.add("Marta");
        Author aut = new Author("Pere");
        Author aut1 = new Author("Marta");
        Author autres1 = auths.get("Pepe");
        Author autres2 = auths.get("Pere");
        Author autres3 = auths.get("Marta");
        assertEquals(aut,autres2);
        assertEquals(aut1,autres3);
        assertNull(autres1);
        // TODO review the generated test code and remove the default call to fail.
    }


    @Test
    public void testRemoveAutor() {
        setup();
        auths.add("Pere");
        auths.add("Marta");
        auths.add("Pepe");
        auths.add("Joan");
        auths.remove("Pere");
        boolean rm = auths.exist("Pere");
        assertFalse(rm);
        boolean rm1 = auths.exist("Marta");
        assertTrue(rm1);
        auths.remove("Marta");
        boolean rm2= auths.exist("Marta");
        assertFalse(rm2);

    }

    @Test
    public void testList() {
        setup();
        auths.add("Arnau");
        auths.add("Boqueron");
        auths.add("Carlitos");
        auths.add("Fujie");
        ArrayList authsnames = auths.list();
        ArrayList<String> names = new ArrayList<>();
        names.add("Arnau");
        names.add("Boqueron");
        names.add("Carlitos");
        names.add("Fujie");
        assertEquals(names,authsnames);
    }

    @Test
    public void testListPre() {
        setup();
        auths.add("Arnau");
        auths.add("Ariana");
        auths.add("Aranjuez");
        auths.add("Fujie");
        auths.add("Pedro");
        auths.add("Arpia");
        ArrayList authsnames = auths.list("Ar");
        ArrayList<String> names = new ArrayList<>();
        names.add("Aranjuez");
        names.add("Ariana");
        names.add("Arnau");
        names.add("Arpia");
        assertEquals(names,authsnames);
    }





}
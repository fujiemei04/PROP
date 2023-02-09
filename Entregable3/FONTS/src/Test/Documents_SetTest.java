package Test;

import Domain.*;
import org.junit.Before;
import org.junit.Test;
import std_extend.Pair;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Documents_SetTest {

    private DocumentsSet docs;

    @Before
    public void setup() {
        docs = new DocumentsSet();
    }

    @Test
    public void testExists() {
        Author a = new Author("Fujie");
        Author b = new Author("Jan");
        docs.add(a,"doc1",1);
        docs.add(b,"doc2",2);
        boolean b0 = docs.exist("Fujie","doc1");
        boolean b1 = docs.exist("Fujie","doc2");
        boolean b2 = docs.exist("Jan","doc2");
        boolean b3 = docs.exist("Jan","doc3");
        boolean b4 = docs.exist("Fuj","doc3");
        assertTrue(b0);
        assertFalse(b1);
        assertTrue(b2);
        assertFalse(b3);
        assertFalse(b4);
    }

    @Test
    public void testlistdocs() {
        setup();
        Author a = new Author("Fujie");
        Author b = new Author("Jana");
        Author c = new Author("Hector");
        Author d = new Author("Jano");
        docs.add(a,"doca",1);
        docs.add(b,"docb",2);
        docs.add(b,"docc",3);
        docs.add(c,"docd",4);
        docs.add(c,"doce",5);
        docs.add(d,"docf",6);
        ArrayList<Pair<String,String>> list = docs.listDocuments();
        ArrayList<Pair<String,String>> expected = new ArrayList<>();
        expected.add(new Pair<>("Fujie","doca"));
        expected.add(new Pair<>("Hector","docd"));
        expected.add(new Pair<>("Hector","doce"));
        expected.add(new Pair<>("Jana","docb"));
        expected.add(new Pair<>("Jana","docc"));
        expected.add(new Pair<>("Jano","docf"));
        assertEquals(expected,list);
    }

    @Test
    public void listdocsauthor() {
        setup();
        Author a = new Author("Fujie");
        Author b = new Author("Jana");
        Author c = new Author("Hector");
        Author d = new Author("Jano");
        docs.add(a,"doca",1);
        docs.add(b,"docb",2);
        docs.add(b,"docc",3);
        docs.add(c,"docd",4);
        docs.add(c,"doce",5);
        docs.add(d,"docf",6);
        ArrayList<String> list1 = docs.listDocuments("Fujie");
        ArrayList<String> list2 = docs.listDocuments("Hector");
        ArrayList<String> list3 = docs.listDocuments("Pedro");
        ArrayList<String> expected1 = new ArrayList<>();
        ArrayList<String> expected2 = new ArrayList<>();
        ArrayList<String> expected3 = new ArrayList<>();
        expected1.add("doca");
        expected2.add("docd");
        expected2.add("doce");
        assertEquals(expected1,list1);
        assertEquals(expected2,list2);
        assertEquals(expected3,list3);
    }

    @Test
    public void testhavetitles() {
        setup();
        Author a = new Author("Fujie");
        Author b = new Author("Jana");
        Author c = new Author("Hector");
        Author d = new Author("Jano");
        docs.add(a,"doca",1);
        docs.add(b,"docb",2);
        docs.add(b,"docc",3);
        docs.add(c,"docd",4);
        docs.add(c,"doce",5);
        docs.add(d,"docf",6);
        boolean b0 = docs.haveTitles("Fujie");
        boolean b1 = docs.haveTitles("Pedro");
        boolean b2 = docs.haveTitles("Hector");
        assertTrue(b0);
        assertTrue(b2);
        assertFalse(b1);
    }

    /*@Test
    public void testgetid() {
        setup();
        Author a = new Author("Fujie");
        Author b = new Author("Jana");
        Author c = new Author("Hector");
        Author d = new Author("Jano");
        docs.add(a,"doca",1);
        docs.add(b,"docb",2);
        docs.add(b,"docc",3);
        docs.add(c,"docd",4);
        docs.add(c,"doce",5);
        docs.add(d,"docf",6);
        int id1 = docs.getId("Fujie","doca");
        int id2 = docs.getId("Jana","docb");
        int id3 = docs.getId("Fujie","docc");
        int id4 = docs.getId("Marc","doce");
        assertEquals(1,id1);
        assertEquals(2,id2);
        assertEquals(0,id3);
        assertEquals(0,id4);
    }*/


}
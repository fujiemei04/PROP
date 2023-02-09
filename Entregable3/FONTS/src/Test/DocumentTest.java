package Test;

import junit.framework.TestCase;
import org.junit.Test;
import Domain.*;

public class DocumentTest extends TestCase {

    @Test
    public void TestConstructora() {
        Author a = new Author("Pedro");
        Document doc = new Document(a,"Doc1",1);
        String s = "Doc1";
        String s1 = doc.getTitle();
        assertEquals(s,s1);
        Author b = new Author("Pedro");
        Author c = doc.getAuthor();
        assertEquals(b,c);
        int i = 1;
        int ii = doc.getId();
        assertEquals(i,ii);
    }

}
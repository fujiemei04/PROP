package Test;

import Domain.Author;
import org.junit.Assert;
import org.junit.Test;

public class AuthorTest {
    @Test
    public void testCreadora() {
        Author author = new Author("Pere");
        Assert.assertEquals("Pere", author.getName());
    }

    @Test
    public void testToString() {
        Author author = new Author("Jan");
        Assert.assertEquals("Jan", author.toString());
    }

}
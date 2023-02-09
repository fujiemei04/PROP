package Test;

import Domain.Author;
import Domain.Document;
import Domain.VectorialSpace;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import std_extend.Pair;

import java.util.ArrayList;
import java.util.List;

public class VectorialSpaceTest  extends TestCase {

    @Test
    public void testGestSimilars() {
        Document d1 = new Document(new Author("test"), "d1", 1);
        d1.setPlainText(new ArrayList<>(List.of(new String[]{"a b c d"})));
        Document d2 = new Document(new Author("test"), "d2", 2);
        d2.setPlainText(new ArrayList<>(List.of(new String[]{"a b c d"})));
        Document d3 = new Document(new Author("test"), "d3", 1);
        d3.setPlainText(new ArrayList<>(List.of(new String[]{""})));
        Document d4 = new Document(new Author("test"), "d4", 1);
        d4.setPlainText(new ArrayList<>(List.of(new String[]{""})));
        Document d5 = new Document(new Author("test"), "d5", 1);
        d5.setPlainText(new ArrayList<>(List.of(new String[]{"a b "})));

        VectorialSpace vectorialSpace = new VectorialSpace();
        vectorialSpace.clean();
        vectorialSpace.add(d1);
        vectorialSpace.add(d2);
        vectorialSpace.add(d3);
        vectorialSpace.add(d4);
        vectorialSpace.add(d5);
        ArrayList<Pair<String,String>> result = vectorialSpace.getSimilar(d1,3);
        Assert.assertEquals(result.size(), 3);
        ArrayList<Pair<String ,String>> expected = new ArrayList<>();
        expected.add(new Pair<>(d3.getTitle(), d3.getAuthor().getName()));
        expected.add(new Pair<>(d4.getTitle(), d4.getAuthor().getName()));
        expected.add(new Pair<>(d2.getTitle(), d2.getAuthor().getName()));

        Assert.assertEquals(expected, result);



    }

}

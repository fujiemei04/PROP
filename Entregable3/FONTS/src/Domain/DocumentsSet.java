package Domain;

import std_extend.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class DocumentsSet {
    private final TreeMap<String, TreeMap<String, Document>> docs;

    private final VectorialSpace vectorialSpace;

    public DocumentsSet() {
        docs = new TreeMap<>();
        vectorialSpace = new VectorialSpace();
    }

    public boolean exist(String author, String title) {
        return docs.containsKey(author) && docs.get(author).containsKey(title);
    }

    public ArrayList<Pair<String, String>> listDocuments() {
        ArrayList<Pair<String, String>> result = new ArrayList<>(docs.size());
        for (Map.Entry<String, TreeMap<String, Document>> a : docs.entrySet()) {
            for (Map.Entry<String, Document> d : a.getValue().entrySet()) {
                result.add(new Pair<>(a.getKey(), d.getKey()));
            }
        }
        return result;
    }

    public ArrayList<String> listDocuments(String author) {
        ArrayList<String> result = new ArrayList<>(docs.size());
        TreeMap<String, Document> a = docs.get(author);
        if (a == null) return result;
        for (Map.Entry<String, Document> d : a.entrySet()) {
            result.add(d.getValue().getTitle());
        }
        return result;
    }

    public Document get(String author, String title) {
        return docs.get(author).get(title);
    }

    public void add(Author author, String title, int id) {
        TreeMap<String, Document> act;
        if (!docs.containsKey(author.getName())) {
            docs.put(author.getName(), new TreeMap<>());
        }
        act = docs.get(author.getName());
        act.put(title, new Document(author, title, id));
    }

    public boolean remove(String author, String title) {
        if (!exist(author, title)) return false;
        Document d = docs.get(author).remove(title);
        if (docs.get(author).size() == 0) docs.remove(author);
        return (Objects.equals(d.getAuthor().getName(), author) && Objects.equals(d.getTitle(), title));
    }

    public boolean haveTitles(String name) {
        return docs.containsKey(name);
    }

    public int getId(String author, String title) {
        Document d = get(author, title);
        if (d != null) {
            return d.getId();
        }
        return 0;
    }

    public void setPlainText(String author, String title, ArrayList<String> newText) {
        Document d = get(author, title);
        if (d != null) {
            d.setPlainText(newText);
        }
    }

    public ArrayList<String> getPlainText(String author, String title) {
        Document d = get(author, title);
        if (d != null) {
            return d.getPlainText();
        }
        return null;
    }

    public void free(String author, String title) {
        Document d = get(author, title);
        if (d != null) {
            d.free();
        }
    }

    public ArrayList<String> serialize() {
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, TreeMap<String, Document>> a : docs.entrySet()) {
            for (Map.Entry<String, Document> d : a.getValue().entrySet()) {
                result.add(d.getValue().getAuthor().getName() + ',' + d.getValue().getTitle() + ',' + d.getValue().getId());
            }
        }
        return result;
    }

    public ArrayList<Pair<String, String>> FindSimilar(String auth, String title, int k) {
        vectorialSpace.clean();
        Document reference = get(auth, title);
        vectorialSpace.setReference(reference);

        for (Map.Entry<String, TreeMap<String, Document>> a : docs.entrySet()) {
            for (Map.Entry<String, Document> d : a.getValue().entrySet()) {
                if (!d.getValue().equals(reference)) {
                    vectorialSpace.add(d.getValue());
                }
            }
        }
        return vectorialSpace.getSimilar(reference, k);
    }

}

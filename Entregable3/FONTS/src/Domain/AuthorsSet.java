package Domain;

import java.util.*;

public class AuthorsSet {
    private final SortedSet<Author> auths;

    public AuthorsSet() {
        Comparator<Author> textComp = Comparator.comparing(Author::getName);
        auths = new TreeSet<>(textComp);
    }

    public AuthorsSet(ArrayList<String> data) {
        Comparator<Author> textComp = Comparator.comparing(Author::getName);
        auths = new TreeSet<>(textComp);
        for (String s : data) {
            auths.add(new Author(s));
        }
    }

    public boolean exist(String name) {
        return auths.contains(new Author(name));
    }

    public ArrayList<String> list() {
        ArrayList<String> l = new ArrayList<>();
        for (Author a : auths) l.add(a.getName());
        return l;
    }

    public ArrayList<String> list(String pre) {
        ArrayList<String> l = new ArrayList<>();
        SortedSet<Author> prefixAuth = auths.subSet(new Author(pre), new Author(pre + Character.MAX_VALUE));
        for (Author a : prefixAuth) l.add(a.getName());
        return l;
    }

    public boolean add(String name) {
        if (name == null) return false;
        return auths.add(new Author(name));
    }

    public boolean remove(String name) {
        if (name == null) return false;
        return auths.remove(new Author(name));
    }

    public ArrayList<String> serialize() {
        ArrayList<String> result = new ArrayList<>();
        for (Author a : auths) result.add(a.toString());
        return result;
    }

    public Author get(String name) {
        SortedSet<Author> prefixAuth = auths.subSet(new Author(name), new Author(name + Character.MAX_VALUE));
        for (Author a : prefixAuth) if (Objects.equals(a.getName(), name)) return a;
        return null;
    }
}

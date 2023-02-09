package Domain;

import std_extend.Pair;

import java.util.ArrayList;

public class AuthorTitleList extends DocumentsSet {

    private final ArrayList<Pair<String, String>> authTitleList;

    public AuthorTitleList() {
        authTitleList = new ArrayList<>();
    }

    public void add(Document d) {
        authTitleList.add(new Pair<>(d.getTitle(), d.getAuthor().getName()));
    }

    public void addNames(String title, String author) {
        authTitleList.add(new Pair<>(title, author));
    }

    public ArrayList<Pair<String, String>> getAuthorTitle() {
        ArrayList<Pair<String, String>> result = new ArrayList<>();
        for (Pair<String, String> a : authTitleList) {
            result.add(new Pair<>(a.getKey(), a.getValue()));
        }
        return result;
    }
}
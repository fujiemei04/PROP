package Domain;

import java.util.ArrayList;

public class Document {
    private final String title;
    private final Author author;
    private final int id;
    private ArrayList<String> plainText;

    public Document(Author author, String title, int id) {
        this.author = author;
        this.title = title;
        this.id = id;
        plainText = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public void setPlainText(ArrayList<String> newText) {
        if (newText != null) {
            plainText = (ArrayList<String>) newText.clone();
        } else {
            plainText.clear();
        }
    }

    public ArrayList<String> getPlainText() {
        return plainText;
    }

    public void free() {
        if (plainText != null) plainText.clear();
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != Document.class) return false;
        return this.getAuthor().equals(((Document) o).getAuthor()) && this.getTitle().equals(((Document) o).getTitle());
    }
}

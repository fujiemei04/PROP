package Domain;

public class Author {
    private final String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != Author.class) return false;
        return this.name.equals(((Author) o).name);
    }

}

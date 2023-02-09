package Controllers;

import std_extend.Pair;

import java.util.ArrayList;

public class ControllerPresentation {
    private final ControllerDomain cd;

    //region domini
    public ControllerPresentation() {
        cd = new ControllerDomain();
    }

    public void exit() {
        cd.exit();
    }

    //region authors

    //region consulters
    public ArrayList<String> listAuthors() {
        return cd.listAuthors();
    }

    public ArrayList<String> listAuthors(String pre) {
        return cd.listAuthors(pre);
    }

    public boolean authorExist(String name) {
        return cd.authorExist(name);
    }

    public boolean haveNotTitles(String name) {
        return !cd.haveTitles(name);
    }

    public ArrayList<String> listDocuments(String name) {
        return cd.listDocuments(name);
    }
    //endregion

    //region modification
    public boolean addAuthor(String name) {
        return cd.addAuthor(name);
    }

    public boolean removeAuthor(String name) {
        return cd.removeAuthor(name);
    }

    //endregion

    //endregion

    //region documents

    //region consulters
    public ArrayList<Pair<String, String>> listDocuments() {
        return cd.listDocuments();
    }

    public boolean documentExist(String author, String title) {
        return cd.documentExist(author, title);
    }

    public ArrayList<Pair<String, String>> FindSimilar(String auth, String title, int k) {
        return cd.FindSimilar(auth, title, k);
    }

    //endregion

    //region modification
    public void addDocument(String author, String title) {
        cd.addDocument(author, title);
    }

    public ArrayList<String> loadDocument(String author, String title) {
        return cd.loadDocument(author, title);
    }

    public void writeDocument(String author, String title, ArrayList<String> body) {
        cd.writeDocument(author, title, body);
    }

    public void freeDocument(String author, String title) {
        cd.freeDocument(author, title);
    }

    public boolean removeDocument(String author, String title) {
        return cd.removeDocument(author, title);
    }
    //endregion

    //endregion

    //region boolean expressions

    public ArrayList<Pair<String, String>> findDocumentExpBool(int id) {
        return cd.findDocumentExpBool(id);
    }

    public int addBooleanExpression(String s) {
        return cd.addBooleanExpression(s);
    }

    public void removeBooleanExpression(int n) {
        cd.removeBooleanExpression(n);
    }

    public ArrayList<Pair<Integer, String>> listBooleanExpression() {
        return cd.listBooleanExpression();
    }

    public void exportDocument(int mode, String author, String title, String path) {
        cd.exportDocument(mode, author, title, path);
    }

    public ArrayList<String> importDocument(int mode, String path) {
        return cd.importDocument(mode, path);
    }

    //endregion

    //endregion


}

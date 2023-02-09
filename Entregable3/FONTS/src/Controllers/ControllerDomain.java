package Controllers;

import Domain.AuthorsSet;
import Domain.BooleanExpressionsSet;
import Domain.DocumentsSet;
import std_extend.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class ControllerDomain {
    ControllerPersistence cp;
    private static AuthorsSet authors_set = new AuthorsSet();
    private static DocumentsSet documents_set = new DocumentsSet();
    private static BooleanExpressionsSet boolean_expression = new BooleanExpressionsSet();

    public ControllerDomain() {
        cp = new ControllerPersistence();
        authors_set = new AuthorsSet(cp.loadAuthorsData());

        documents_set = new DocumentsSet();
        ArrayList<String> documentsList = cp.loadDocumentsData();
        for (String s : documentsList) {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(s.split(",")));
            documents_set.add(authors_set.get(temp.get(0)), temp.get(1), Integer.parseInt(temp.get(2)));
        }

        boolean_expression = new BooleanExpressionsSet(cp.loadBooleanExpressionsData());
    }

    public void exit() {
        save_authors();
        save_documents();
        save_booleanExpressions();
        cp.exit();
    }

    private void save_authors() {
        cp.saveAuthorsData(authors_set.serialize());
    }

    private void save_documents() {
        cp.saveDocumentsData(documents_set.serialize());
    }

    private void save_booleanExpressions() {
        cp.saveBooleanExpressionsData(boolean_expression.serialize());
    }

    //region authors

    //region consulters
    public ArrayList<String> listAuthors() {
        return authors_set.list();
    }

    public ArrayList<String> listAuthors(String pre) {
        return authors_set.list(pre);
    }

    public boolean authorExist(String name) {
        return authors_set.exist(name);
    }

    public ArrayList<String> listDocuments(String name) {
        if (authors_set.exist(name)) return documents_set.listDocuments(name);
        return null;
    }
    //endregion

    //region modification
    public boolean addAuthor(String name) {
        return authors_set.add(name);
    }

    public boolean removeAuthor(String name) {
        return authors_set.remove(name);
    }

    //endregion

    //endregion

    //region documents

    //region consulters

    public boolean haveTitles(String name) {
        return documents_set.haveTitles(name);
    }

    public boolean documentExist(String author, String title) {
        return documents_set.exist(author, title);
    }

    public ArrayList<Pair<String, String>> listDocuments() {
        return documents_set.listDocuments();
    }

    public ArrayList<Pair<String, String>> FindSimilar(String auth, String title, int k) {
        ArrayList<Pair<String, String>> result = new ArrayList<>();
        if (k <= 0) return result;
        ArrayList<Pair<String, String>> docs = listDocuments();
        for (Pair<String, String> d : docs) {
            loadDocument(d.getKey(), d.getValue());
        }

        result = documents_set.FindSimilar(auth, title, k);

        for (Pair<String, String> d : docs) {
            freeDocument(d.getKey(), d.getValue());
        }
        return result;
    }

    //endregion

    //region modification
    public void addDocument(String author, String title) {
        int id = cp.getNextId();
        documents_set.add(authors_set.get(author), title, id);
        cp.newDocument(id, author, title);
    }

    public ArrayList<String> loadDocument(String author, String title) {
        documents_set.setPlainText(
                author,
                title,
                cp.loadDocument(
                        documents_set.getId(author, title)
                ));
        return documents_set.getPlainText(author, title);
    }

    public void writeDocument(String author, String title, ArrayList<String> body) {
        documents_set.setPlainText(
                author,
                title,
                body);
        cp.writeDocument(documents_set.getId(author, title), body);
    }

    public void exportDocument(int mode, String author, String title, String path) {
        cp.exportDocument(mode, author, title, documents_set.getId(author, title), path);
    }

    public ArrayList<String> importDocument(int mode, String path) {
        return cp.importDocument(mode, path);
    }

    public void freeDocument(String author, String title) {
        documents_set.free(author, title);
    }

    public boolean removeDocument(String author, String title) {
        cp.removeDocument(documents_set.getId(author, title));
        return documents_set.remove(author, title);
    }
    //endregion

    //endregion

    //region boolean expressions

    public int addBooleanExpression(String s) {
        return boolean_expression.add(s);
    }

    public void removeBooleanExpression(int n) {
        boolean_expression.remove(n);
    }

    public ArrayList<Pair<Integer, String>> listBooleanExpression() {
        return boolean_expression.list();
    }

    public ArrayList<Pair<String, String>> findDocumentExpBool(int id) {
        ArrayList<Pair<String, String>> result = new ArrayList<>();
        ArrayList<Pair<String, String>> docs = listDocuments();
        for (Pair<String, String> d : docs) {
            loadDocument(d.getKey(), d.getValue());

            if (BooleanExpressionsSet.evaluate(id, documents_set.get(d.getKey(), d.getValue()))) {
                result.add(new Pair<>(d.getKey(), d.getValue()));
            }

            freeDocument(d.getKey(), d.getValue());
        }
        return result;
    }

    //endregion

    //endregion
}
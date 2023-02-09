package Controllers;

import Persistence.SaveData;
import Persistence.SaveDocument;
import Persistence.SavePlainText;
import Persistence.SaveSerialized;
import Persistence.SaveXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class ControllerPersistence {
    private final SaveData sd = new SaveData();
    private final SaveDocument documentSht = new SaveSerialized();
    private final SaveDocument documentPlainText = new SavePlainText();
    private final SaveDocument documentXML = new SaveXML();
    private final TreeMap<Integer, String> docs;

    public ControllerPersistence() {
        docs = new TreeMap<>();
        loadDocuments();
    }

    public void exit() {
        saveDocuments();
    }

    public int getNextId() {
        if (docs == null || docs.isEmpty()) return 0;
        return docs.lastKey() + 1;
    }

    public void newDocument(int id, String author, String title) {
        String path = "docs" + '/' + author + '_' + title + ".sht";
        docs.put(id, path);
        documentSht.WriteDocument(path, null);
    }

    public ArrayList<String> loadDocument(int id) {
        return documentSht.ReadDocument(docs.get(id));
    }

    public void removeDocument(int id) {
        documentSht.deleteFile(docs.get(id));
        docs.remove(id);
    }

    public void writeDocument(int id, ArrayList<String> text) {
        documentSht.WriteDocument(docs.get(id), text);
    }

    private void saveDocuments() {
        if (docs != null && !docs.isEmpty()) {
            ArrayList<String> data = new ArrayList<>();
            for (Map.Entry<Integer, String> d : docs.entrySet()) {
                data.add(d.getKey().toString() + ',' + d.getValue());
            }
            saveData("data", "pDocument.bin", data);
        }
    }

    private void loadDocuments() {
        ArrayList<String> data = loadData("data" + '/' + "pDocument.bin");
        for (String s : data) {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(s.split(",")));
            docs.put(Integer.parseInt(temp.get(0)), temp.get(1));
        }
    }

    public void exportDocument(int mode, String author, String title, int id, String path) {
        if (mode == 0) exportPlainText(author, title, id, path);
        if (mode == 1) exportXML(author, title, id, path);
    }

    private void exportPlainText(String author, String title, int id, String path) {
        ArrayList<String> data = new ArrayList<>();
        data.add(author);
        data.add(title);
        data.addAll(data.size(), loadDocument(id));
        documentPlainText.WriteDocument(path, data);
    }

    private void exportXML(String author, String title, int id, String path) {
        ArrayList<String> data = new ArrayList<>();
        data.add(author);
        data.add(title);
        data.addAll(data.size(), loadDocument(id));
        documentXML.WriteDocument(path, data);
    }

    public ArrayList<String> importDocument(int mode, String path) {
        if (mode == 0) return importPlainText(path);
        if (mode == 1) return importXML(path);
        return null;
    }

    private ArrayList<String> importPlainText(String path) {
        return documentPlainText.ReadDocument(path);
    }

    private ArrayList<String> importXML(String path) {
        return documentXML.ReadDocument(path);
    }

    public ArrayList<String> loadAuthorsData() {
        return loadData("data" + '/' + "authors.bin");
    }

    public ArrayList<String> loadDocumentsData() {
        return loadData("data" + '/' + "documents.bin");
    }

    public ArrayList<String> loadBooleanExpressionsData() {
        return loadData("data" + '/' + "bExp.bin");
    }

    private ArrayList<String> loadData(String path) {
        return sd.loadData(path);
    }

    public void saveAuthorsData(ArrayList<String> data) {
        saveData("data", "authors.bin", data);
    }

    public void saveDocumentsData(ArrayList<String> data) {
        saveData("data", "documents.bin", data);
    }

    public void saveBooleanExpressionsData(ArrayList<String> data) {
        saveData("data", "bExp.bin", data);
    }

    public void saveData(String dir, String name, ArrayList<String> data) {
        sd.saveData(dir, name, data);
    }

}

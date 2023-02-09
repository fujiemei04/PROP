package Presentation;

import Controllers.ControllerPresentation;
import std_extend.Pair;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

public class DocumentList extends JPanel {
    private final ControllerPresentation cp;
    private final DocumentView documentView;
    private ArrayList<Pair<String, String>> documentList;

    public DocumentList(ControllerPresentation cp, DocumentView documentView) {
        this.cp = cp;
        this.documentView = documentView;
        setLayout(new GridLayout(0, 1, 5, 5));
        reloadDocuments("");
        repaint();
    }

    private void pushDocuments() {
        removeAll();
        revalidate();
        repaint();
        for (Pair<String, String> doc : documentList) {
            DocumentBox actBox = new DocumentBox(cp, doc.getKey(), doc.getValue(), this);
            add(actBox);
        }
    }

    public void deleteDocument(String author, String title) {
        if (cp.removeDocument(author, title)) {
            reloadDocuments("");
            documentView.reloadScrollView();
            JOptionPane.showMessageDialog(this,
                    "Document " + title + " of " + author + " was deleted.",
                    "Document Delete",
                    JOptionPane.INFORMATION_MESSAGE);
        } else JOptionPane.showMessageDialog(this,
                "Error: Unknown Error.",
                "Document Delete",
                JOptionPane.ERROR_MESSAGE);
    }

    public void reloadDocuments(String author) {
        if (author == null || author.isBlank() || author.isEmpty()) documentList = cp.listDocuments();
        else {
            documentList.clear();
            ArrayList<String> tmp = cp.listAuthors(author);
            if (tmp != null && tmp.size() > 0 && tmp.get(0).equals(author)) {
                tmp = cp.listDocuments(author);
                documentList.clear();
                for (String s : tmp) documentList.add(new Pair<>(author, s));
            }
        }
        documentList.sort(Pair::compareTo2);
        pushDocuments();
    }

    public void setDocuments(ArrayList<Pair<String, String>> docs) {
        documentList = docs;
        pushDocuments();
    }

    public void invertOrder() {
        Collections.reverse(documentList);
        pushDocuments();
    }
}
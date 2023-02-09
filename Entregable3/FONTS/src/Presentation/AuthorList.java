package Presentation;

import Controllers.ControllerPresentation;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

public class AuthorList extends JPanel {
    private final ControllerPresentation cp;
    private final AuthorView authorView;
    private ArrayList<String> authorList;

    public AuthorList(ControllerPresentation cp, AuthorView authorView) {
        this.cp = cp;
        this.authorView = authorView;
        setLayout(new GridLayout(0, 3, 5, 5));
        reloadAuthors("");
        repaint();
    }

    private void pushAuthors() {
        removeAll();
        revalidate();
        repaint();
        for (String a : authorList) {
            AuthorBox actBox = new AuthorBox(a, this);
            add(actBox);
        }
    }

    public void deleteAuthor(String author) {
        if (cp.authorExist(author)) {
            if (cp.haveNotTitles(author)) {
                if (cp.removeAuthor(author)) {
                    reloadAuthors("");
                    authorView.reloadScrollView();
                    JOptionPane.showMessageDialog(this,
                            "Author " + author + " was deleted.",
                            "Author Delete",
                            JOptionPane.INFORMATION_MESSAGE);

                } else JOptionPane.showMessageDialog(this,
                        "Error: Unknown Error.",
                        "Author Delete",
                        JOptionPane.ERROR_MESSAGE);
            } else JOptionPane.showMessageDialog(this,
                    "Error: Author '" + author + "' have documents assigned.",
                    "Author Delete",
                    JOptionPane.ERROR_MESSAGE);
        } else JOptionPane.showMessageDialog(this,
                "Error: Author '" + author + "' it doesn't exist.",
                "Author Delete",
                JOptionPane.ERROR_MESSAGE);
    }

    public void reloadAuthors(String prefix) {
        authorList = cp.listAuthors(prefix);
        pushAuthors();
    }

    public void invertOrder() {
        Collections.reverse(authorList);
        pushAuthors();
    }
}

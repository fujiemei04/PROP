package Presentation;

import Controllers.ControllerPresentation;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocumentEditor extends JFrame {
    private final String author;
    private final String title;
    private final ControllerPresentation cp;
    private boolean modified;
    private final JTextArea textArea;
    private String startingText;

    public DocumentEditor(ControllerPresentation cp, String author, String title) {
        this.cp = cp;
        this.author = author;
        this.title = title;
        modified = false;

        setTitle(title + " - " + author);
        setMinimumSize(new Dimension(300, 200));
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                if(modified) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "There are Unsaved changes, dow you want to save them?",
                            "Unsaved changes",
                            JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.YES_OPTION) {
                        saveDocument();
                        cp.freeDocument(author, title);
                        setVisible(false);
                        dispose();
                    } else if (option == JOptionPane.NO_OPTION) {
                        cp.freeDocument(author, title);
                        setVisible(false);
                        dispose();
                    }
                } else {
                    setVisible(false);
                    dispose();
                }
            }
        });

        ArrayList<String> documentContent = cp.loadDocument(author, title);
        startingText = "";
        for(String s : documentContent) startingText = startingText.concat(s+"\n");
        textArea = new JTextArea();
        textArea.setText("");
        textArea.setText(startingText);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        JMenuBar menuBar = new JMenuBar();
        JMenuItem saveItem = new JMenuItem("Save (Ctrl+S)");
        menuBar.add(saveItem);
        setJMenuBar(menuBar);

        saveItem.addActionListener(e -> saveDocument());

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) saveDocument();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void saveDocument() {
        modified = false;
        setTitle(title + " - " + author);
        ArrayList<String> content = new ArrayList<>(List.of(textArea.getText().split("\n")));
        cp.writeDocument(author, title, content);
    }

    private void setState () {
        if (!Objects.equals(startingText, textArea.getText())) {
            setTitle(title + " - " + author + " (Modified)");
            modified = true;
        } else {
            setTitle(title + " - " + author);
            modified = false;
        }
    }
}

package Presentation;

import Controllers.ControllerPresentation;
import std_extend.Pair;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Objects;

public class DocumentView extends JPanel implements ComponentListener {
    private final ControllerPresentation cp;
    private final JScrollPane documentScrollPane;
    private final JPanel topPanel;
    private final JComboBox<String> jComboBoxAuthors;
    private final DocumentList documentList;

    public DocumentView(ControllerPresentation cp) {
        this.cp = cp;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.addComponentListener(this);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 3;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel, c);

        Icon searchIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/search_icon.png")));
        JLabel labelSearchIcon = new JLabel(searchIcon);
        topPanel.add(BorderLayout.WEST, labelSearchIcon);

        jComboBoxAuthors = new JComboBox<>();
        reloadAuthors();
        jComboBoxAuthors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jComboBoxAuthors.getItemCount() > 0) {
                    if (jComboBoxAuthors.getSelectedIndex() == 0) documentList.reloadDocuments("");
                    else
                        documentList.reloadDocuments(Objects.requireNonNull(jComboBoxAuthors.getSelectedItem()).toString());
                }
                reloadScrollView();
            }
        });
        topPanel.add(BorderLayout.CENTER, jComboBoxAuthors);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(BorderLayout.EAST, searchPanel);

        Icon sortIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/sortName_icon.png")));
        JButton sortButton = new JButton(sortIcon);
        sortButton.addActionListener(e -> invertDocumentOrder());
        searchPanel.add(sortButton);

        Icon addIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/addDocument_icon.png")));
        JButton addButton = new JButton(addIcon);
        addButton.addActionListener(e -> new NewDocument(this));
        searchPanel.add(addButton);

        Icon importIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/import_icon.png")));
        JButton importButton = new JButton(importIcon);
        importButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt and XML", "xml", "txt");
            jFileChooser.setFileFilter(filter);
            int returnVal = jFileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (jFileChooser.accept(jFileChooser.getSelectedFile())) {
                    int n = -1;
                    if (jFileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".txt")) n = 0;
                    else if (jFileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".xml")) n = 1;

                    if (n >= 0) {
                        ArrayList<String> data = cp.importDocument(n, jFileChooser.getSelectedFile().getPath());
                        if (data != null && data.size() >= 2 && !data.get(0).isEmpty() && !data.get(1).isEmpty()) {
                            String author = data.remove(0), title = data.remove(0);
                            if (createNewDocument(author, title)) {
                                cp.loadDocument(author, title);
                                cp.writeDocument(author, title, data);
                                cp.freeDocument(author, title);
                            }
                        } else errorSelectedFileNoValid();
                    } else errorSelectedFileNoValid();
                } else errorSelectedFileNoValid();
            }
        });
        searchPanel.add(importButton);

        Icon resetIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/reset_icon.png")));
        JButton resetButton = new JButton(resetIcon);
        resetButton.addActionListener(e -> resetButtonFunction());
        searchPanel.add(resetButton);

        Icon booleanExpIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/bExpFilter_icon.png")));
        JButton booleanExpButton = new JButton(booleanExpIcon);
        booleanExpButton.addActionListener(e -> new BooleanExpression(cp, this));
        searchPanel.add(booleanExpButton);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        documentList = new DocumentList(cp, this);
        documentScrollPane = new JScrollPane(documentList);
        add(documentScrollPane, c);

        c.anchor = GridBagConstraints.SOUTHWEST;

        reloadScrollView();
    }

    private void resetButtonFunction() {
        documentList.reloadDocuments("");
        reloadAuthors();
        reloadScrollView();
    }

    private void errorSelectedFileNoValid() {
        JOptionPane.showMessageDialog(this,
                "Error: Selected file is not valid.",
                "Import document",
                JOptionPane.ERROR_MESSAGE);
    }

    public boolean createNewDocument(String author, String title) {
        if (cp.authorExist(author)) {
            if (!cp.documentExist(author, title)) {
                cp.addDocument(author, title);
                documentList.reloadDocuments("");
                reloadScrollView();
                return true;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: This document already exist.",
                        "New document",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (JOptionPane.showConfirmDialog(this,
                    "Author " + author + " does not exist, do you want to create it?", "New document",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                cp.addAuthor(author);
                cp.addDocument(author, title);
                documentList.reloadDocuments("");
                reloadAuthors();
                reloadScrollView();
                return true;
            }
        }
        return false;
    }

    public void reloadAuthors() {
        if (jComboBoxAuthors.getItemCount() > 0) jComboBoxAuthors.removeAllItems();
        jComboBoxAuthors.addItem("All");

        ArrayList<String> authors = cp.listAuthors();
        for (String a : authors) jComboBoxAuthors.addItem(a);
        jComboBoxAuthors.setSelectedIndex(0);
    }

    public void setSearch(String s) {
        jComboBoxAuthors.setSelectedItem(s);
    }

    public void reloadScrollView() {
        revalidate();
        repaint();
        resizeScrollPanel();
        documentScrollPane.revalidate();
        documentScrollPane.repaint();
    }

    public void invertDocumentOrder() {
        documentList.invertOrder();
    }

    private void resizeScrollPanel() {
        int a = documentList.getLayout().preferredLayoutSize(documentScrollPane).height + 3,
                b = this.getHeight() - topPanel.getHeight();
        documentScrollPane.setPreferredSize(new Dimension(0, Math.min(a, b)));
    }

    public void setDocuments(ArrayList<Pair<String, String>> docs) {
        documentList.setDocuments(docs);
        reloadScrollView();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        reloadScrollView();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}

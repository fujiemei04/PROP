package Presentation;

import Controllers.ControllerPresentation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Objects;

public class AuthorView extends JPanel implements ComponentListener {

    private final ControllerPresentation cp;
    private final JScrollPane authorScrollPane;
    private final JTextField textField;
    private final JButton addButton;
    private final AuthorList authorList;

    public AuthorView(ControllerPresentation cp) {
        this.cp = cp;

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.addComponentListener(this);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;


        Icon searchIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/search_icon.png")));
        JLabel labelSearchIcon = new JLabel(searchIcon);
        add(labelSearchIcon, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 9;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 97;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 5;
        textField = new JTextField(20);
        add(textField, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 98;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        Icon sortIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/sortName_icon.png")));
        JButton sortButton = new JButton(sortIcon);
        sortButton.addActionListener(e -> invertAuthorsOrder());
        add(sortButton, c);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 99;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 0;
        c.ipady = 0;
        Icon addIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/addDocument_icon.png")));
        addButton = new JButton(addIcon);
        addButton.addActionListener(e -> addAuthor());
        add(addButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 10;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 100;
        c.gridheight = 1;
        authorList = new AuthorList(cp, this);
        authorScrollPane = new JScrollPane(authorList);
        add(authorScrollPane, c);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                authorList.reloadAuthors(textField.getText());
                reloadScrollView();
            }

            public void removeUpdate(DocumentEvent e) {
                authorList.reloadAuthors(textField.getText());
                reloadScrollView();
            }

            public void insertUpdate(DocumentEvent e) {
                authorList.reloadAuthors(textField.getText());
                reloadScrollView();
            }
        });

        reloadScrollView();
    }

    public void invertAuthorsOrder() {
        authorList.invertOrder();
    }

    private void addAuthor() {
        String author = JOptionPane.showInputDialog(this, "Type author name", "Add Author", JOptionPane.QUESTION_MESSAGE);
        if (author != null) {
            if (author.equals(""))
                JOptionPane.showMessageDialog(this,
                        "Error: Author cannot have null name.",
                        "Add Author",
                        JOptionPane.ERROR_MESSAGE);
            else if (cp.authorExist(author))
                JOptionPane.showMessageDialog(this,
                        "Error: Author already exist.",
                        "Add Author",
                        JOptionPane.ERROR_MESSAGE);
            else {
                cp.addAuthor(author);
                authorList.reloadAuthors("");
                reloadScrollView();
                JOptionPane.showMessageDialog(this,
                        "Author '" + author + "' was created.",
                        "Add author",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void reloadAuthors() {
        authorList.reloadAuthors(textField.getText());
    }

    public void reloadScrollView() {
        revalidate();
        repaint();
        resizeScrollPanel();
        authorScrollPane.revalidate();
        authorScrollPane.repaint();
    }

    private void resizeScrollPanel() {
        int a = authorList.getLayout().preferredLayoutSize(authorScrollPane).height + 3,
                b = this.getHeight() - addButton.getHeight();
        authorScrollPane.setPreferredSize(new Dimension(0, Math.min(a, b)));
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

package Presentation;

import Controllers.ControllerPresentation;
import std_extend.Pair;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Objects;


public class DocumentBox extends JPanel {
    private final ControllerPresentation cp;
    private final DocumentList parent;
    public String author;
    public String title;

    public DocumentBox(ControllerPresentation cp, String author, String title, DocumentList parent) {
        this.cp = cp;
        this.author = author;
        this.title = title;
        this.parent = parent;

        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                int border = 25;
                g.drawRoundRect(x, y, width - 1, height - 1, border, border);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 5, 5, 5);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0; //Position
        c.gridy = 0;
        c.weightx = 1; //Additional space
        c.weighty = 1;
        c.fill = GridBagConstraints.WEST;
        c.gridwidth = 9; //Number rows/cols spans
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST; //Position of component if it has additions space
        c.ipadx = 0; //controls padding between component and borders
        c.ipady = 10;
        JLabel labelTitle = new JLabel(title);
        add(labelTitle, c);

        c.gridx = 1; //Position
        c.gridy = 0;
        c.weightx = 1; //Additional space
        c.weighty = 1;
        c.fill = GridBagConstraints.WEST;
        c.gridwidth = 9; //Number rows/cols spans
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER; //Position of component if it has additions space
        c.ipadx = 0; //controls padding between component and borders
        c.ipady = 10;
        JLabel labelAuthor = new JLabel(author);
        add(labelAuthor, c);

        c.gridx = 2; //Position
        c.gridy = 0;
        c.weightx = 0.1; //Additional space
        c.weighty = 0.1;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1; //Number rows/cols spans
        c.gridheight = 1;
        c.anchor = GridBagConstraints.EAST; //Position of component if it has additions space
        c.ipadx = 0; //controls padding between component and borders
        c.ipady = 10;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonPanel, c);

        JButton exportButton = setExportButton();
        buttonPanel.add(exportButton);

        JButton editButton = setEditOption();
        buttonPanel.add(editButton);

        JButton compareButton = setCompareOption();
        buttonPanel.add(compareButton);

        JButton eraseButton = setEraseOption();
        buttonPanel.add(eraseButton);

        setMaximumSize(new Dimension(0, 200));
    }

    private JButton setExportButton() {
        Icon exportIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/export_icon.png")));
        JButton exportButton = new JButton(exportIcon);
        exportButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML and txt", "xml", "txt");
            jFileChooser.setFileFilter(filter);
            jFileChooser.setDialogTitle("Save");
            jFileChooser.setApproveButtonText("Save");
            int returnVal = jFileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (jFileChooser.accept(jFileChooser.getSelectedFile())) {
                    int n = -1;
                    if (jFileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".txt")) n = 0;
                    else if (jFileChooser.getSelectedFile().getPath().toLowerCase().endsWith(".xml")) n = 1;

                    if (n >= 0)
                        cp.exportDocument(n, author, title, jFileChooser.getSelectedFile().getPath());

                    else System.out.println("no valid");
                } else System.out.println("no valid");
            }
        });
        return exportButton;
    }

    private JButton setEditOption() {
        Icon editIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/edit_icon.png")));
        JButton editButton = new JButton(editIcon);
        editButton.addActionListener(e -> {
            //cp.freeDocument(author, title);
            new DocumentEditor(cp, author, title);
        });
        return editButton;
    }

    private JButton setCompareOption() {
        Icon compareIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/compare_icon.png")));
        JButton compareButton = new JButton(compareIcon);
        compareButton.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog(this,
                    "Type how many documents do you want to get",
                    "Search by similarity Author",
                    JOptionPane.QUESTION_MESSAGE);
            if (amount != null) {
                int k;
                try {
                    k = Integer.parseInt(amount);
                    ArrayList<Pair<String, String>> comparedDocs = cp.FindSimilar(author, title, k);
                    parent.setDocuments(comparedDocs);
                } catch (NumberFormatException exception) {
                    //exception.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Error: The input is not a valid number.",
                            "Search by similarity",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return compareButton;
    }

    private JButton setEraseOption() {
        Icon eraseIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/bin_icon.png")));
        JButton eraseButton = new JButton(eraseIcon);
        eraseButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure that you want to delete document " + title + " of " + author + "?",
                    "Delete Document",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                parent.deleteDocument(author, title);

            }
        });
        return eraseButton;
    }
}

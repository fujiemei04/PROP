package Presentation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Objects;

public class AuthorBox extends JPanel {
    public String author_name;

    public AuthorBox(String author_name, AuthorList parent) {
        this.author_name = author_name;

        Border b = new Border() {
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
        };
        setBorder(b);

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
        JLabel labelName = new JLabel(author_name);
        add(labelName, c);

        c.gridx = 1; //Position
        c.gridy = 0;
        c.weightx = 0.1; //Additional space
        c.weighty = 0.1;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 1; //Number rows/cols spans
        c.gridheight = 1;
        c.anchor = GridBagConstraints.EAST; //Position of component if it has additions space
        c.ipadx = 0; //controls padding between component and borders
        c.ipady = 10;
        JPanel buttonBox = new JPanel();
        buttonBox.setLayout(new FlowLayout(FlowLayout.RIGHT));
        add(buttonBox, c);

        Icon searchIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/authorFilter_icon.png")));
        JButton searchButton = new JButton(searchIcon);
        searchButton.addActionListener(e -> MainWindow.filterByAuthor(author_name));
        buttonBox.add(searchButton);

        Icon removeIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/bin_icon.png")));
        JButton removeButton = new JButton(removeIcon);
        removeButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this,
                    "Are you sure that you want to delete the author " + author_name + "?",
                    "Author delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                //deleteAuthor(actBut.getText());
                parent.deleteAuthor(author_name);

            }
        });
        buttonBox.add(removeButton);
    }
}

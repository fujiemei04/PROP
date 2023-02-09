package Presentation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

public class NewDocument extends JFrame {
    public NewDocument(DocumentView documentView) throws HeadlessException {
        setMinimumSize(new Dimension(250, 100));
        setMaximumSize(new Dimension(250, 100));
        setSize(250, 100);
        setTitle("New document");
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        JPanel pane = new JPanel();
        add(pane);

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel author_name = new JLabel("Author:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(author_name, c);

        JTextField author_input = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(author_input, c);

        JLabel title_name = new JLabel("Title:");
        c.weightx = 0.3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(title_name, c);

        JTextField title_input = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.6;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(title_input, c);


        JButton button = new JButton("Create document");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 2;
        pane.add(button, c);

        button.addActionListener(e -> {
            documentView.createNewDocument(author_input.getText(), title_input.getText());
            title_input.setText("");
        });

    }


}

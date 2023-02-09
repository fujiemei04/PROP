package Presentation;

import Controllers.ControllerPresentation;
import std_extend.Pair;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Objects;

public class BooleanExpression extends JFrame {
    private final ControllerPresentation cp;
    private final JComboBox<Pair<Integer, String>> jComboBoxBExp;

    public BooleanExpression(ControllerPresentation cp, DocumentView parent) {
        this.cp = cp;
        setMinimumSize(new Dimension(500, 80));
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Boolean Expression");
        setAlwaysOnTop(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        Icon addIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/addDocument_icon.png")));
        JButton addButton = new JButton(addIcon);
        addButton.addActionListener(e -> addNewBooleanExpression());
        panel.add(BorderLayout.WEST, addButton);

        jComboBoxBExp = new JComboBox<>();
        reloadBExp();
        panel.add(BorderLayout.CENTER, jComboBoxBExp);

        JPanel rightButtons = new JPanel();
        rightButtons.setLayout(new FlowLayout());
        panel.add(BorderLayout.EAST, rightButtons);

        Icon useIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/arrow_icon.png")));
        JButton useButton = new JButton(useIcon);
        useButton.addActionListener(e -> {
            if (jComboBoxBExp.getItemCount() > 0)
                parent.setDocuments(cp.findDocumentExpBool(
                        ((Pair<Integer, String>) Objects.requireNonNull(jComboBoxBExp.getSelectedItem())).getKey()
                ));
            else
                JOptionPane.showMessageDialog(this,
                        "There no exist any boolean expression to use. Please create one first.",
                        "Error Boolean Expression",
                        JOptionPane.ERROR_MESSAGE);
        });
        rightButtons.add(useButton);

        Icon removeIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/bin_icon.png")));
        JButton removeButton = new JButton(removeIcon);
        removeButton.addActionListener(e -> {
            if (jComboBoxBExp.getItemCount() > 0)
                eraseBooleanExpression(((Pair<Integer, String>) Objects.requireNonNull(jComboBoxBExp.getSelectedItem())).getKey());
            else
                JOptionPane.showMessageDialog(this,
                        "There no exist any boolean expression to erase. Please create one first.",
                        "Error Boolean Expression",
                        JOptionPane.ERROR_MESSAGE);
        });
        rightButtons.add(removeButton);
    }

    private void eraseBooleanExpression(int id) {
        cp.removeBooleanExpression(id);
        reloadBExp();
        JOptionPane.showMessageDialog(this,
                "Boolean expression was erased.",
                "Add Boolean Expression",
                JOptionPane.INFORMATION_MESSAGE);
        reloadBExp();
    }

    private void addNewBooleanExpression() {
        String bExp = JOptionPane.showInputDialog(this,
                "Type the boolean expression",
                "Add Boolean Expression",
                JOptionPane.QUESTION_MESSAGE);
        if (bExp != null) {
            if (bExp.equals(""))
                JOptionPane.showMessageDialog(this,
                        "Error: Boolean expression can not be null.",
                        "Add Boolean Expression",
                        JOptionPane.ERROR_MESSAGE);
            else {
                if (cp.addBooleanExpression(bExp) >= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Boolean expression was created.",
                            "Add Boolean Expression",
                            JOptionPane.INFORMATION_MESSAGE);
                    reloadBExp();
                } else
                    JOptionPane.showMessageDialog(this,
                            "Error: Syntax is not correct.",
                            "Add Boolean Expression",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void reloadBExp() {
        if (jComboBoxBExp.getItemCount() > 0) jComboBoxBExp.removeAllItems();
        ArrayList<Pair<Integer, String>> BExpList = cp.listBooleanExpression();
        for (Pair<Integer, String> p : BExpList) {
            jComboBoxBExp.addItem(p);
        }
        jComboBoxBExp.setSelectedItem(0);
    }

}

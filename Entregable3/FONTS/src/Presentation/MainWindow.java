package Presentation;

import Controllers.ControllerPresentation;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class MainWindow {
    private static AuthorView authorPanel;
    private static DocumentView documentPanel;
    private static JTabbedPane tabbedPane;

    public MainWindow() {
        ControllerPresentation cp = new ControllerPresentation();

        JFrame frame = new JFrame("Seshat");

        ImageIcon seshatIcon = new ImageIcon(Objects.requireNonNull(MainWindow.class.getClassLoader().getResource("icons/seshat_icon.png")));
        frame.setIconImage(seshatIcon.getImage());
        frame.setMinimumSize(new Dimension(500, 200));


        tabbedPane = new JTabbedPane();
        authorPanel = new AuthorView(cp);
        documentPanel = new DocumentView(cp);

        tabbedPane.add(authorPanel, "Authors");
        tabbedPane.add(documentPanel, "Documents");
        frame.add(tabbedPane);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure that you want to close Seshat?",
                        "Close Seshat",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    cp.exit();
                    System.exit(0);
                }
            }
        });

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 0) {
                authorPanel.reloadAuthors();
                authorPanel.reloadScrollView();
            } else if (tabbedPane.getSelectedIndex() == 1) {
                documentPanel.reloadAuthors();
            }
        });

    }

    public static void filterByAuthor(String author) {
        tabbedPane.setSelectedIndex(1);
        documentPanel.setSearch(author);
    }

}

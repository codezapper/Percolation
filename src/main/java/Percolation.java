import javax.swing.*;
import java.awt.*;

public class Percolation {
    private Percolation() {
        initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("CustomLayoutDemo");
        Container pane = frame.getContentPane();

        pane.setLayout(new BorderLayout());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnOpen = new JButton("Open random site");
        JButton btnQuit = new JButton("Quit");

        btnPanel.add(btnOpen);
        btnPanel.add(btnQuit);
        JPanel topPanel = new JPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        Surface surface = new Surface(50, 50, 15, 15);
        topPanel.add(surface);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        pane.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Percolation ex = new Percolation();
        });
    }
}

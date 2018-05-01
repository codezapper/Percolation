import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Percolation {
    private Surface surface;

    private Percolation() {
        initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("CustomLayoutDemo");
        Container pane = frame.getContentPane();

        pane.setLayout(new BorderLayout());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnOpenUntilConnected = new JButton("Open until connected");
        JButton btnShowConnected = new JButton("Show connection");
        JButton btnOpen = new JButton("Open random site");
        JButton btnQuit = new JButton("Quit");

        btnPanel.add(btnOpenUntilConnected);
        btnPanel.add(btnShowConnected);
        btnPanel.add(btnOpen);
        btnPanel.add(btnQuit);
        JPanel topPanel = new JPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());

        btnOpenUntilConnected.addActionListener(e -> surface.openUntilConnected());
        btnShowConnected.addActionListener(e -> surface.showConnected());
        btnOpen.addActionListener(e -> surface.open(new Random().nextInt(50), new Random().nextInt(50)));
        btnQuit.addActionListener(e -> System.exit(0));

        surface = new Surface(5, 5, 30, 30);
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

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Percolation {
    private Surface surface;

    private Percolation() {
        initUI();
    }

    private void openUntilConnected(Surface surface) {
        while (surface.findRoot(surface.virtual_top) != surface.findRoot(surface.virtual_bottom)) {
            surface.open(new Random().nextInt(50), new Random().nextInt(50));
        }
    }

    private void initUI() {
        JFrame frame = new JFrame("CustomLayoutDemo");
        Container pane = frame.getContentPane();

        pane.setLayout(new BorderLayout());
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnOpenUntilConnected = new JButton("Open until connected");
        JButton btnOpen = new JButton("Open random site");
        JButton btnQuit = new JButton("Quit");

        btnPanel.add(btnOpenUntilConnected);
        btnPanel.add(btnOpen);
        btnPanel.add(btnQuit);
        JPanel topPanel = new JPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());

        btnOpenUntilConnected.addActionListener(e -> openUntilConnected(surface));
        btnOpen.addActionListener(e -> surface.open(new Random().nextInt(50), new Random().nextInt(50)));
        btnQuit.addActionListener(e -> System.exit(0));

        surface = new Surface(50, 50, 15, 15);
        topPanel.add(surface);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        pane.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        surface.open(5, 5);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Percolation ex = new Percolation();
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Percolation {
    private Surface surface;
    private JLabel lblStats = new JLabel("");

    private Percolation() {
        initUI();
    }

    private void updateStats() {
        Integer total = surface.getOpen() + surface.getClosed();
        Float ratio;
        if (surface.getOpen() <= 0) {
            ratio = 0f;
        } else {
            ratio = (100 * (float) surface.getOpen()) / (float) total;
        }
        lblStats.setText("<html>" + surface.getOpen().toString() + "/" + total.toString() + "<br />Ratio: " + ratio.toString() + "%</html>");
    }

    private void initUI() {
        JFrame frame = new JFrame("CustomLayoutDemo");
        Container pane = frame.getContentPane();
        surface = new Surface(50, 50, 15, 15);

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

        btnOpenUntilConnected.addActionListener(e -> {
            surface.openUntilConnected();
            updateStats();
        });
        btnShowConnected.addActionListener(e -> surface.showConnected());
        btnOpen.addActionListener(e -> surface.open(new Random().nextInt(surface.getColumns()), new Random().nextInt(surface.getRows())));
        btnQuit.addActionListener(e -> System.exit(0));

        lblStats.setPreferredSize(new Dimension(100, 100));
        topPanel.add(surface);
        topPanel.add(lblStats);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        pane.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        updateStats();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Percolation ex = new Percolation();
        });
    }
}

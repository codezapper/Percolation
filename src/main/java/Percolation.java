import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Percolation extends JFrame {
    private Percolation() {
        initUI();
    }

    private void initUI() {
        add(new Surface());
        setTitle("Percolation demo");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Percolation ex = new Percolation();
            ex.setVisible(true);
        });
    }
}

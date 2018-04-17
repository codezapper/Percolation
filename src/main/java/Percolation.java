import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Percolation extends JFrame {
    private Percolation() {
        initUI();
    }

    private void initUI() {
        Surface surface = new Surface();
        add(surface);
        setTitle("Percolation demo");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        surface.open(5, 6);
        surface.open(5, 7);
        int id = surface.findRoot(surface.getIdFromCoords(5, 7));
        id = surface.findRoot(surface.getIdFromCoords(5, 6));

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Percolation ex = new Percolation();
            ex.setVisible(true);
        });
    }
}

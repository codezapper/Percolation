import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Surface extends JPanel {
    private Site[] sites;
    private Graphics2D g2d = null;

    private int width;
    private int height;

    private int columns;
    private int rows;

    public Integer virtualTop;
    public Integer virtualBottom;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * (columns + 1), height * (rows + 1));
    }

    private void initSites() {
        virtualTop = rows * columns;
        virtualBottom = (rows * columns) + 1;
        sites = new Site[(rows * columns) + 2];
        int i = 0;
        for (Integer row = 0; row < rows; row++) {
            for (Integer column = 0; column < columns; column++) {
                sites[i] = new Site(Site.NO_ROOT, (column + 1) * width, (row + 1) * height, width, height, virtualTop, virtualBottom);
                i++;
            }
        }
        sites[i] = new Site(virtualTop, -1, -1, -1, -1, virtualTop, virtualBottom);
        sites[i + 1] = new Site(virtualBottom, -2, -2, -1, -1, virtualTop, virtualBottom);
    }

    public Surface(Integer rows, Integer columns, Integer width, Integer height) {
        this.rows = rows;
        this.columns = columns;
        this.width = width;
        this.height = height;
        initSites();
    }

    public ArrayList<Integer> getAdjacents(int x, int y) {
        int[][] coords = {{x - 1, y}, {x + 1, y}, {x, y - 1}, {x, y + 1}};
        ArrayList<Integer> retValue = new ArrayList<>();

        for (int[] coord : coords) {
            try {
                int _x = coord[0];
                int _y = coord[1];
                int id = getIdFromCoords(_x, _y);

                if (_y == 0) {
                    retValue.add(virtualTop);
                } else if (_y == (rows - 1)) {
                    retValue.add(virtualBottom);
                }

                if (id > -1) {
                    retValue.add(id);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Skipping because out of bounds");
            }
        }

        return retValue;
    }

    public void openUntilConnected() {
        while (findRoot(virtualTop) != findRoot(virtualBottom)) {
            open(new Random().nextInt(50), new Random().nextInt(50));
        }
    }

    public void showConnected() {
        Integer virtualTopRoot = findRoot(virtualTop);
        Integer virtualBottomRoot = findRoot(virtualBottom);
        if (virtualTopRoot != virtualBottomRoot) {
            return;
        }
        for (Integer i = 0; i < (rows * columns) - 1; i++) {
            if (findRoot(i) == virtualBottomRoot) {
                sites[i].setHighlighted(true);
            }
        }
        revalidate();
        repaint();
    }

    public void open(int x, int y) {
        sites[getIdFromCoords(x, y)].setId(getIdFromCoords(x, y));
        for (int id : getAdjacents(x, y)) {
            if ((id == virtualTop) || (id == virtualBottom)) {
                sites[id].setId(getIdFromCoords(x, y));
            } else {
                if (sites[id].isOpen()) {
                    sites[findRoot(id)].setId(getIdFromCoords(x, y));
                }
            }
        }
        revalidate();
        repaint();
    }

    public int getIdFromCoords(int x, int y) {
        if (y < 0) {
            return virtualTop;
        }

        int tempId = x + (columns * y);
        if (tempId > ((columns * rows) -1)) {
            tempId = virtualBottom;
        }
        return tempId;
    }

    public int[] getCoordsFromId(int id) {
        int[] coords = new int[2];
        if (id < 0) {
            coords[0] = -1;
            coords[1] = -1;
        } else {
            coords[0] = id % columns;
            coords[1] = id / columns;
        }
        return coords;
    }

    public Integer findRoot(int id) {
        while ((sites[id].getId() != id) && (sites[id].getId() != Site.NO_ROOT)) {
            id = sites[id].getId();
        }
        return id;
    }

    private void doDrawing(Graphics g) {
        if (g2d == null) {
            g2d = (Graphics2D) g;
        }

        for (Site site : sites) {
            site.draw((Graphics2D) g);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

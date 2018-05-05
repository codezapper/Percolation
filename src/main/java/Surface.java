import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Surface extends JPanel {
    private Site[] sites;
    private Graphics2D g2d = null;

    private int width;
    private int height;

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    private int columns;
    private int rows;

    private Integer nOpen;
    private Integer nClosed;

    public Integer getOpen() {
        return nOpen;
    }

    public Integer getClosed() {
        return nClosed;
    }

    public Integer virtualTop;
    public Integer virtualBottom;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * (columns + 1), height * (rows + 1));
    }

    private void initSites() {
        nOpen = 0;
        nClosed = rows * columns;
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
                if ((_x < 0) || (_x >= columns)) {
                    continue;
                }

                if (_y >= rows) {
                    retValue.add(virtualBottom);
                    continue;
                }

                if (_y < 0) {
                    retValue.add(virtualTop);
                    continue;
                }

                int id = getIdFromCoords(_x, _y);

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
        initSites();
        while (findRoot(virtualTop) != findRoot(virtualBottom)) {
            Integer x = new Random().nextInt(columns);
            Integer y = new Random().nextInt(rows);
            if (!sites[getIdFromCoords(x, y)].isOpen()) {
                nOpen++;
                nClosed--;
                open(x, y);
            }
        }
    }

    public void showConnected() {
        Integer virtualTopRoot = findRoot(virtualTop);
        Integer virtualBottomRoot = findRoot(virtualBottom);
        if (virtualTopRoot != virtualBottomRoot) {
            return;
        }
        for (Integer i = 0; i < (rows * columns); i++) {
            Integer currentRoot = findRoot(i);
            if (currentRoot == virtualBottomRoot) {
                sites[i].setHighlighted(true);
            }
        }
        revalidate();
        repaint();
    }

    public void open(Integer x, Integer y) {
        Integer currentId = getIdFromCoords(x, y);
        sites[currentId].setId(currentId);
        for (int id : getAdjacents(x, y)) {
            if (sites[id].isOpen()) {
                Integer currentRoot = findRoot(id);
                sites[currentRoot].setId(currentId);
            }
        }
        revalidate();
        repaint();
    }

    public int getIdFromCoords(int x, int y) {
        if (y < 0) {
            return virtualTop;
        }

        if ((x < 0) || (x >= columns) || (y >= rows)) {
            return -1;
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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Surface extends JPanel {
    private Site[] sites;
    private Graphics2D g2d = null;

    private int width;
    private int height;

    private int columns;
    private int rows;

    public Integer virtual_top;
    public Integer virtual_bottom;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * (columns + 1), height * (rows + 1));
    }

    private void initSites() {
        virtual_top = rows * columns;
        virtual_bottom = (rows * columns) + 1;
        sites = new Site[(rows * columns) + 2];
        int i = 0;
        for (Integer row = 0; row < rows; row++) {
            for (Integer column = 0; column < columns; column++) {
                sites[i] = new Site(Site.NO_ROOT, (column + 1) * width, (row + 1) * height, width, height, virtual_top, virtual_bottom);
                i++;
            }
        }
        sites[i] = new Site(virtual_top, -1, -1, -1, -1, virtual_top, virtual_bottom);
        sites[i + 1] = new Site(virtual_bottom, -2, -2, -1, -1, virtual_top, virtual_bottom);
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
                    retValue.add(virtual_top);
                } else if (_y == 9) {
                    retValue.add(virtual_bottom);
                }

                retValue.add(id);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Skipping because out of bounds");
            }
        }

        return retValue;
    }

    public void open(int x, int y) {
        sites[getIdFromCoords(x, y)].setId(getIdFromCoords(x, y));
        for (int id : getAdjacents(x, y)) {
            if ((id == virtual_top) || (id == virtual_bottom)) {
                sites[id].setId(getIdFromCoords(x, y));
            } else {
                if (sites[id].isOpen()) {
                    sites[findRoot(id)].setId(getIdFromCoords(x, y));
                }
            }
        }
    }

    public int getIdFromCoords(int x, int y) {
        if (y < 0) {
            return virtual_top;
        }

        int tempId = x + (10 * y);
        if (tempId > 99) {
            tempId = virtual_bottom;
        }
        return tempId;
    }

    public int[] getCoordsFromId(int id) {
        int[] coords = new int[2];
        if (id < 0) {
            coords[0] = -1;
            coords[1] = -1;
        } else {
            coords[0] = id % 10;
            coords[1] = id / 10;
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

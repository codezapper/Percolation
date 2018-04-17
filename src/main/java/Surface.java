import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Surface extends JPanel {
    private Site[] sites = new Site[102];
    private Graphics2D g2d = null;

    private int width = 30;
    private int height = 30;

    public Surface() {
        int i = 0;
        for (Integer y = 0; y < 10; y++) {
            for (Integer x = 0; x < 10; x++) {
                sites[i] = new Site(Site.NO_ROOT, (x + 1) * width, (y + 1) * height, width, height);
                i++;
            }
        }
        sites[i] = new Site(Site.VIRTUAL_TOP, -1, -1, -1, -1);
        sites[i + 1] = new Site(Site.VIRTUAL_BOTTOM, -2, -2, -1, -1);
    }

    public ArrayList<Integer> getAdjacents(int x, int y) {
        int[][] coords = {{x - 1, y}, {x + 1, y}, {x, y - 1}, {x, y + 1}};
        ArrayList<Integer> retValue = new ArrayList<Integer>();
        Boolean addedVirtualTop = false;

        for (int[] coord : coords) {
            try {
                int _x = coord[0];
                int _y = coord[1];
                int id = getIdFromCoords(_x, _y);

                if (_y == 0) {
                    retValue.add(Site.VIRTUAL_TOP);
                } else if (_y == 9) {
                    retValue.add(Site.VIRTUAL_BOTTOM);
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
            if (sites[id].isOpen()) {
                sites[findRoot(id)].setId(getIdFromCoords(x, y));
            }
        }
    }

    public int getIdFromCoords(int x, int y) {
        int tempId = x + (10 * y);
        if (tempId > 99) {
            tempId = Site.VIRTUAL_BOTTOM;
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

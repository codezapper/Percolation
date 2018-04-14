import java.awt.*;

public class Site {
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;

    private Integer id = null;
    private Integer root = null;

    public static final Integer VIRTUAL_TOP = 100;
    public static final Integer VIRTUAL_BOTTOM = 101;
    public static final Integer NO_ROOT = -1;

    public Site(Integer id, Integer x, Integer y, Integer width, Integer height) {
        this.id = id;
        this.root = NO_ROOT;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void draw(Graphics2D g2d) {
        if ((this.x < 0) || (this.y < 0)) {
            return;
        }
        g2d.setPaint(new Color(0, 0, 0));
        g2d.setStroke(new BasicStroke(2));
        Integer x_top_corner = x - (width / 2);
        Integer y_top_corner = y - (height / 2);
        g2d.drawRect(x_top_corner, y_top_corner, width, height);

        if (isOpen()) {
            g2d.setPaint(new Color(150, 150, 200));
        } else {
            g2d.setPaint(new Color(200, 200, 200));
        }
        g2d.fillRect(x_top_corner + 1, y_top_corner + 1, width - 2, height - 2);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setRoot(Integer id) {
        root = id;
    }

    public Integer getRoot() {
        return root;
    }

    public Boolean isOpen() {
        if ((id == VIRTUAL_TOP) || (id == VIRTUAL_BOTTOM)) {
            return true;
        }
        return !(id == NO_ROOT);
    }
}

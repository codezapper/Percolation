import java.awt.*;

public class Site {
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;

    private Integer id;
    private Integer virtual_top;
    private Integer virtual_bottom;

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    private boolean highlighted = false;

    public static final Integer NO_ROOT = -1;

    public Site(Integer id, Integer x, Integer y, Integer width, Integer height, Integer virtual_top, Integer virtual_bottom) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.virtual_top = virtual_top;
        this.virtual_bottom = virtual_bottom;
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
            if (isHighlighted()) {
                g2d.setPaint(new Color(255, 100, 100));
            } else {
                g2d.setPaint(new Color(150, 150, 200));
            }
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

    public Boolean isOpen() {
        if ((id == virtual_top) || (id == virtual_bottom)) {
            return true;
        }
        return !(id == NO_ROOT);
    }
}

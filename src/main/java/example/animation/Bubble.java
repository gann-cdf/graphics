package example.animation;

import org.gannacademy.cdf.graphics.geom.Ellipse;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;

public class Bubble {
    private Ellipse e;
    private double dx, dy;
    public static final double DEFAULT_RADIUS = 10;

    public Bubble(DrawingPanel p) {
        if (Math.random() > 0.5) {
            e = new Ellipse((Math.random() > 0.5 ? -DEFAULT_RADIUS : p.getWidth()), Math.random() * p.getHeight(), DEFAULT_RADIUS, DEFAULT_RADIUS, p);
        } else {
            e = new Ellipse(Math.random() * p.getWidth(), (Math.random() > 0.5 ? -DEFAULT_RADIUS : p.getHeight()), DEFAULT_RADIUS, DEFAULT_RADIUS, p);
        }
        e.setFillColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 1));
        e.setStrokeColor(e.getFillColor().darker());
        dx = Math.random() * (DEFAULT_RADIUS / 2) - (DEFAULT_RADIUS / 4);
        dy = Math.random() * (DEFAULT_RADIUS / 2) - (DEFAULT_RADIUS / 4);
    }

    public void step() {
        e.translate(dx, dy);
        if (e.getX() > e.getDrawingPanel().getWidth() || e.getX() < -e.getWidth()) {
            dx = -1 * dx;
        }
        if (e.getY() > e.getDrawingPanel().getHeight() || e.getY() < -e.getHeight()) {
            dy = -1 * dy;
        }
    }

    public double getRadius() {
        return e.getWidth() / 2;
    }

    public boolean intersects(Bubble other) {
        return Math.hypot(e.getX() - other.e.getX(), e.getY() - other.e.getY()) < getRadius() + other.getRadius();
    }

    public void absorb(Bubble other) {
        double a = Math.PI * (Math.pow(getRadius(), 2) + Math.pow(other.getRadius(), 2)),
                r = Math.sqrt(a / Math.PI),
                dr = (r - getRadius()) / 2,
                ratio = getRadius() / other.getRadius();
        dx = (ratio * dx + other.dx) / ratio;
        dy = (ratio * dy + other.dy) / ratio;
        e.setFillColor(new Color(
                (int) Math.sqrt((Math.pow(e.getFillColor().getRed(), 2) + Math.pow(other.e.getFillColor().getRed(), 2)) / 2),
                (int) Math.sqrt((Math.pow(e.getFillColor().getGreen(), 2) + Math.pow(other.e.getFillColor().getGreen(), 2)) / 2),
                (int) Math.sqrt((Math.pow(e.getFillColor().getBlue(), 2) + Math.pow(other.e.getFillColor().getBlue(), 2)) / 2)
        ));
        e.setStrokeColor(e.getFillColor().darker());
        e.setFrame(e.getX() - dr, e.getY() - dr, r * 2, r * 2);
    }

    public void close() {
        e.removeFromDrawingPanel();
    }
}

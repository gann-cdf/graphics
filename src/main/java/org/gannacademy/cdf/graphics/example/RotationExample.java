package org.gannacademy.cdf.graphics.example;

import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.geom.Path;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class RotationExample extends AppWindow implements MouseListener {
    private static final double
            BASE = 50,
            HEIGHT = 100,
            THETA = Math.PI / 180.0,
            CROSSHAIR_ARM = 20;
    private Path path, crosshair;
    private double x, y;
    private boolean mouseDown;
    private int button;

    @Override
    protected void setup() {
        // show instructions to user
        new Text("Left, middle, or right-click to rotate the triangle", 10, 40, getDrawingPanel());
        x = getDrawingPanel().getWidth() / 2.0;

        // calculate top point of triangle
        y = (getDrawingPanel().getHeight() - HEIGHT) / 2.0;

        defineAnchorPointCrosshair();
        defineTriangle();

        // align crosshair to center of gravity
        Point2D center = getCentroid();
        crosshair.setLocation(center.getX() - CROSSHAIR_ARM, center.getY() - CROSSHAIR_ARM);

        // register to be notified of mouse events (and assume no one is clicking right now)
        mouseDown = false;
        getDrawingPanel().addMouseListener(this);
        getDrawingPanel().requestFocus();
    }

    private void defineTriangle() {
        path = new Path(getDrawingPanel());
        path.moveTo(x, y); // move to top point
        path.lineTo(x + BASE / 2.0, y + HEIGHT); // line to bottom-right point
        path.lineTo(x - BASE / 2.0, y + HEIGHT); // line to bottom-left point
        path.lineTo(x, y);
        path.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
    }

    private void defineAnchorPointCrosshair() {
        crosshair = new Path(getDrawingPanel());
        crosshair.moveTo(CROSSHAIR_ARM, 0);
        crosshair.lineTo(CROSSHAIR_ARM, CROSSHAIR_ARM * 2);
        crosshair.moveTo(0, CROSSHAIR_ARM);
        crosshair.lineTo(CROSSHAIR_ARM * 2, CROSSHAIR_ARM);
        crosshair.setStrokeColor(Color.RED);
    }

    @Override
    protected void loop() {
        if (mouseDown) {

            double theta = THETA; // default, rotate clockwise around mouse
            double x = this.x, y = this.y; // default, use whatever (roundRect, ellipse) is currently set for axis of rotation

            if (button == MouseEvent.BUTTON1) theta *= -1; // left button, rotate counter-clockwise around mouse

            else if (button == MouseEvent.BUTTON2) { // middle button, rotate around center of gravity
                // use center of gravity calculation as rotation axis
                Point2D center = getCentroid();
                x = center.getX();
                y = center.getY();
            }

            // rotate, using any adjustments to theta and axis of rotiation
            path.rotate(theta, x, y);
            crosshair.setLocation(x - CROSSHAIR_ARM, y - CROSSHAIR_ARM);
        }
        sleep(25);
    }

    public Point2D getCentroid() {
        double[] coords = new double[6]; // to hold coordinates
        double x = 0, y = 0; // tally roundRect and ellipse-coordinates
        int points = 0; // haven't visited any points yet!
        for (PathIterator i = path.getPathIterator(null); !i.isDone(); i.next()) {
            switch (i.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO:
                case PathIterator.SEG_LINETO:
                    x += coords[0];
                    y += coords[1];
                    break;
                case PathIterator.SEG_QUADTO:
                    x += coords[2];
                    y += coords[3];
                    break;
                case PathIterator.SEG_CUBICTO:
                    x += coords[4];
                    y += coords[5];
                    break;
                case PathIterator.SEG_CLOSE:
                default:
                    // do nothing
            }
            points++;
        }

        // package (roundRect, ellipse) coordinates into r single object
        return new Point2D.Double(x / points, y / points);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        button = e.getButton();
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        new RotationExample();
    }
}

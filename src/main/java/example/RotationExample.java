package example;

import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.geom.Path;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class RotationExample extends AppWindow implements MouseListener {
    private static final double
            BASE = 50,
            HEIGHT = 100,
            THETA = Math.PI / 180.0;
    private Path path;
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

        // define the triangle path
        path = new Path(getDrawingPanel());
        path.moveTo(x, y); // move to top point
        path.lineTo(x + BASE / 2.0, y + HEIGHT); // line to bottom-right point
        path.lineTo(x - BASE / 2.0, y + HEIGHT); // line to bottom-left point
        path.lineTo(x, y); // line back to top point

        // register to be notified of mouse events (and assume no one is clicking right now)
        mouseDown = false;
        getDrawingPanel().addMouseListener(this);
        getDrawingPanel().requestFocus();
    }

    @Override
    protected void loop() {
        if (mouseDown) {

            double theta = THETA; // default, rotate clockwise around mouse
            double x = this.x, y = this.y; // default, use whatever (x, y) is currently set for axis of rotation

            if (button == MouseEvent.BUTTON1) theta *= -1; // left button, rotate counter-clockwise around mouse

            else if (button == MouseEvent.BUTTON2) { // middle button, rotate around center of gravity
                // use center of gravity calculation as rotation axis
                Point2D center = getCenterOfGravity();
                x = center.getX();
                y = center.getY();
            }

            // rotate, using any adjustments to theta and axis of rotiation
            path.transform(AffineTransform.getRotateInstance(theta, x, y));
        }
        sleep(25);
    }

    public Point2D getCenterOfGravity() {
        PathIterator i = path.getPathIterator(null);
        double[] coords = new double[6]; // to hold coordinates
        double x = 0, y = 0; // tally x and y-coordinates
        int points = 0; // haven't visited any points yet
        while (!i.isDone()) {
            if (points != 0) { // skip the first visit to the top point
                i.currentSegment(coords);
                x += coords[0];
                y += coords[1];
            }
            i.next();
            if (!i.isDone()) points++; // don't double-count last point!
        }

        // package (x, y) coordinates into a single object
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

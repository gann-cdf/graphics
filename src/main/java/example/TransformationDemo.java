package example;

import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.geom.*;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;
import java.awt.geom.Point2D;

public class TransformationDemo extends AppWindow {
    Path p, q, r, s;
    Rectangle a;
    RoundRectangle b;
    Ellipse c;
    Arc d;

    boolean inflation = true;
    int steps = 0;

    @Override
    protected void setup() {
        Color
                transparentRed = new Color(1f, 0f, 0f, 0.5f),
                transparentBlue = new Color(0f, 0f, 1f, 0.5f);

        a = new Rectangle(100, 50, 150, 100, getDrawingPanel());
        a.setFillColor(transparentRed);
        b = new RoundRectangle(350, 50, 150, 100, 25, 25, getDrawingPanel());
        b.setFillColor(transparentRed);
        c = new Ellipse(100, 250, 150, 100, getDrawingPanel());
        c.setFillColor(transparentRed);
        d = new Arc(350, 250, 150, 100, 117, 312, getDrawingPanel());
        d.setFillColor(transparentRed);

        p = a.getAsPath();
        p.setFillColor(transparentBlue);
        q = b.getAsPath();
        q.setFillColor(transparentBlue);
        r = c.getAsPath();
        r.setFillColor(transparentBlue);
        s = d.getAsPath();
        s.setFillColor(transparentBlue);

        Text t = new Text("Translate", 0, 0, getDrawingPanel());
        t.setLocation(a.getX() + (a.getWidth() - t.getWidth()) / 2, a.getY() + (a.getHeight() - t.getHeight()) / 2 + t.getMaxAscent());
        t = new Text("Shear", 0, 0, getDrawingPanel());
        t.setLocation(b.getX() + (b.getWidth() - t.getWidth()) / 2, b.getY() + (b.getHeight() - t.getHeight()) / 2 + t.getMaxAscent());
        t = new Text("Scale", 0, 0, getDrawingPanel());
        t.setLocation(c.getX() + (c.getWidth() - t.getWidth()) / 2, c.getY() + (c.getHeight() - t.getHeight()) / 2 + t.getMaxAscent());
        t = new Text("Rotate", 0, 0, getDrawingPanel());
        t.setLocation(d.getX() + (d.getWidth() - t.getWidth()) / 2, d.getY() + (d.getHeight() - t.getHeight()) / 2 + t.getMaxAscent());
    }

    @Override
    protected void loop() {
        steps++;
        if (steps > 100) {
            inflation = !inflation;
            steps = 0;
        }
        p.translate(.6 * (inflation ? 1 : 0 - 1), .4 * (inflation ? 1 : 0 - 1));
        Point2D p = r.getLocation();
        r.scale(1 + (inflation ? 1 : 0 - 1) * .01, 1 + (inflation ? 1 : 0 - 1) * .01);
        r.setLocation(p.getX(), p.getY());
        p = q.getLocation();
        q.shear(.01 * (inflation ? 1 : 0 - 1), .01 * (inflation ? 1 : 0 - 1));
        q.setLocation(p.getX(), p.getY());
        s.rotate(Math.PI / 180 * (inflation ? 1 : 0 - 1), 425, 300);
        sleep(100);
    }

    public static void main(String[] args) {
        new TransformationDemo();
    }
}

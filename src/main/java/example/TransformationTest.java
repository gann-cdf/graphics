package example;

import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.geom.*;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TransformationTest extends AppWindow {
    Path p, q, r, s;
    Rectangle a;
    RoundRectangle b;
    Ellipse c;
    Arc d;

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


    }

    @Override
    protected void loop() {
        p.transform(AffineTransform.getRotateInstance(Math.PI / 36, 175, 100));
        q.transform(AffineTransform.getRotateInstance(Math.PI / 32, 425, 100));
        r.transform(AffineTransform.getRotateInstance(Math.PI / 28, 175, 300));
        s.transform(AffineTransform.getRotateInstance(Math.PI / 24, 425, 300));
        sleep(100);
    }

    public static void main(String[] args) {
        new TransformationTest();
    }
}

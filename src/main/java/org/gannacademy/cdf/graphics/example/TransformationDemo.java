package org.gannacademy.cdf.graphics.example;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.geom.*;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;
import java.awt.geom.Point2D;

public class TransformationDemo extends AppWindow {
    Path rect, roundRect, ellipse, arc;
    Rectangle r;
    RoundRectangle rr;
    Ellipse e;
    Arc a;

    boolean inflation = true;
    int steps = 0;

    private void centerOnShape(Text label, Drawable shape) {
        label.setLocation(shape.getX() + (shape.getWidth() - label.getWidth()) / 2, shape.getY() + (shape.getHeight() - label.getHeight()) / 2 + label.getMaxAscent());
    }

    @Override
    protected void setup() {
        Color
                transparentRed = new Color(1f, 0f, 0f, 0.5f),
                transparentBlue = new Color(0f, 0f, 1f, 0.5f);

        r = new Rectangle(100, 50, 150, 100, getDrawingPanel());
        r.setFillColor(transparentRed);
        rr = new RoundRectangle(350, 50, 150, 100, 25, 25, getDrawingPanel());
        rr.setFillColor(transparentRed);
        e = new Ellipse(100, 250, 150, 100, getDrawingPanel());
        e.setFillColor(transparentRed);
        a = new Arc(350, 250, 150, 100, 117, 312, getDrawingPanel());
        a.setFillColor(transparentRed);

        rect = r.getAsPath();
        rect.setFillColor(transparentBlue);
        roundRect = rr.getAsPath();
        roundRect.setFillColor(transparentBlue);
        ellipse = e.getAsPath();
        ellipse.setFillColor(transparentBlue);
        arc = a.getAsPath();
        arc.setFillColor(transparentBlue);

        Text label = new Text("Translate", 0, 0, getDrawingPanel());
        centerOnShape(label, r);
        label = new Text("Shear", 0, 0, getDrawingPanel());
        centerOnShape(label, rr);
        label = new Text("Scale", 0, 0, getDrawingPanel());
        centerOnShape(label, e);
        label = new Text("Rotate", 0, 0, getDrawingPanel());
        centerOnShape(label, a);
    }

    @Override
    protected void loop() {
        steps++;
        if (steps > 100) {
            inflation = !inflation;
            steps = 0;
        }
        rect.translate(.6 * (inflation ? 1 : 0 - 1), .4 * (inflation ? 1 : 0 - 1));
        Point2D p = ellipse.getLocation();
        ellipse.scale(1 + (inflation ? 1 : 0 - 1) * .01, 1 + (inflation ? 1 : 0 - 1) * .01);
        ellipse.setLocation(p.getX(), p.getY());
        p = roundRect.getLocation();
        roundRect.shear(.01 * (inflation ? 1 : 0 - 1), .01 * (inflation ? 1 : 0 - 1));
        roundRect.setLocation(p.getX(), p.getY());
        arc.rotate(Math.PI / 180 * (inflation ? 1 : 0 - 1), 425, 300);
        sleep(100);
    }

    public static void main(String[] args) {
        new TransformationDemo();
    }
}

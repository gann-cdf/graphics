package example.shapes;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.ui.AppWindow;

public abstract class ShapeTest extends AppWindow {

    protected Drawable shape;
    protected double dx, dy, width, height;

    @Override
    protected void setup() {
        width = getDrawingPanel().getWidth() / 3.0;
        height = getDrawingPanel().getHeight() / 3.0;
        double heading = Math.random() * Math.PI * 2.0;
        dx = Math.cos(heading);
        dy = Math.sin(heading);
    }

    @Override
    protected void loop() {
        shape.translate(dx, dy);
        if (shape.getX() < 0 || shape.getX() + shape.getWidth() > getDrawingPanel().getWidth()) {
            dx = -dx;
        }
        if (shape.getY() < 0 || shape.getY() + shape.getHeight() > getDrawingPanel().getHeight()) {
            dy = -dy;
        }
        sleep(10);
    }
}

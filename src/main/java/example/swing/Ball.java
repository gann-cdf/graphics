package example.swing;

import org.gannacademy.cdf.graphics.geom.Ellipse;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;

public class Ball {
    private Ellipse ball;
    private double dx, dy;

    public Ball(DrawingPanel panel) {
        double heading = Math.random() * Math.PI * 2;
        dx = Math.cos(heading);
        dy = Math.sin(heading);

        ball = new Ellipse(
                Math.random() * panel.getWidth(),
                Math.random() * panel.getHeight(),
                20, 20,
                panel
        );
        ball.setFillColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
        ball.setStrokeColor(ball.getFillColor());
    }

    public void move() {
        ball.translate(dx, dy);
        if (ball.getX() < 0 || ball.getX() + ball.getWidth() > ball.getDrawingPanel().getWidth()) {
            dx = -dx;
        }
        if (ball.getY() < 0 || ball.getY() + ball.getWidth() > ball.getDrawingPanel().getHeight()) {
            dy = -dy;
        }
    }
}

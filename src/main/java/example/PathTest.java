package example;

import org.gannacademy.cdf.graphics.geom.Path;
import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;

public class PathTest extends AppWindow {
  private Path p;
  private Rectangle frame;
  private double dx, dy, sx, sy;
  private boolean isIncreasing;

  public static void main(String[] args) {
    new PathTest();
  }

  @Override
  protected void setup() {
    p = new Path(getDrawingPanel());
    p.moveTo(100, 100);
    p.curveTo(200, 100, 200, 200, 100, 200);
    p.lineTo(100, 100);
    p.setStrokeColor(Color.red);

    frame = new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight(), getDrawingPanel());
    frame.setStrokeColor(Color.green);

    dx = Math.random() * 10 - 5;
    dy = Math.random() * 10 - 5;
  }

  @Override
  protected void loop() {
    frame.translate(dx, dy);
    p.translate(dx, dy);

    if (frame.getX() > getDrawingPanel().getWidth()) {
      frame.setX(0 - frame.getWidth());
      p.setX(0 - p.getWidth());
    } else if (frame.getX() < 0 - frame.getWidth()) {
      frame.setX(getDrawingPanel().getWidth());
      p.setX(getDrawingPanel().getWidth());
    }

    if (frame.getY() > getDrawingPanel().getHeight()) {
      frame.setY(0 - frame.getHeight());
      p.setY(0 - p.getHeight());
    } else if (frame.getY() < 0 - frame.getHeight()) {
      frame.setY(getDrawingPanel().getHeight());
      p.setY(getDrawingPanel().getHeight());
    }
    sleep(100);
  }
}

package example.graphics.demo;

import gann.graphics.Drawable;
import gann.graphics.Image;
import gann.graphics.Text;
import gann.graphics.geom.*;
import gann.graphics.geom.Rectangle;
import gann.graphics.ui.AppWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DemoApp extends AppWindow {

  public static final double COL_SPACE = 30, COL_WIDTH = 160, ROW_SPACE = 10, ROW_HEIGHT = 120, SPEED = 3;
  public static final long DELAY = 5000;

  public static void main(String[] args) {
    new DemoApp();
  }

  private List<Drawable> components;
  private List<Double> headings;
  private long start;

  private int columns() {
    return (int) ((getDrawingPanel().getWidth() - COL_SPACE) / (COL_SPACE + COL_WIDTH));
  }

  private double xCoord(int i) {
    int col = i % columns();
    return COL_SPACE + col * (COL_WIDTH + COL_SPACE);
  }

  private double yCoord(int i) {
    int row = i / columns();
    return ROW_SPACE + row * (ROW_HEIGHT + ROW_SPACE);
  }

  private Color randomColor() {
    return new Color((int) (Math.random() * 256.0), (int) (Math.random() * 256.0), (int) (Math.random() * 256.0));
  }

  private Color adjustColor(Color color, int adjustment) {
    return new Color(
            Math.max(0, Math.min(255, color.getRed() + adjustment)),
            Math.max(0, Math.min(255, color.getGreen() + adjustment)),
            Math.max(0, Math.min(255, color.getBlue() + adjustment))
    );
  }

  @Override
  protected void setup() {
    Class[] types = {
            Rectangle.class,
            Ellipse.class,
            RoundRectangle.class,
            Arc.class,
            Line.class,
            CubicCurve.class,
            QuadCurve.class,
            Path.class,
            Text.class,
            Image.class
    };
    components = new ArrayList<>();
    headings = new ArrayList<>();

    double rows = (int) (Math.sqrt(types.length));
    int cols = (int) (Math.ceil((double) types.length / rows));
    setSize((int) (COL_SPACE + cols * (COL_WIDTH + COL_SPACE)), (int) (ROW_SPACE + rows * (ROW_HEIGHT + ROW_SPACE)));

    for (int i = 0; i < types.length; i++) {
      headings.add(Math.random() * 360.0);
      if (types[i] == Ellipse.class) {
        components.add(new Ellipse(xCoord(i), yCoord(i), COL_WIDTH, ROW_HEIGHT, getDrawingPanel()));
      } else if (types[i] == RoundRectangle.class) {
        components.add(new RoundRectangle(xCoord(i), yCoord(i), COL_WIDTH, ROW_HEIGHT, COL_SPACE, ROW_SPACE, getDrawingPanel()));
      } else if (types[i] == Arc.class) {
        components.add(new Arc(xCoord(i), yCoord(i), COL_WIDTH, ROW_HEIGHT, Math.random() * 360, Math.random() * 360, getDrawingPanel()));
      } else if (types[i] == Line.class) {
        components.add(new Line(xCoord(i), yCoord(i), xCoord(i) + COL_WIDTH, yCoord(i) + ROW_HEIGHT, getDrawingPanel()));
      } else if (types[i] == CubicCurve.class) {
        components.add(new CubicCurve(xCoord(i), yCoord(i), xCoord(i), yCoord(i) + ROW_HEIGHT, xCoord(i) + COL_WIDTH, yCoord(i), xCoord(i) + COL_WIDTH, yCoord(i) + ROW_HEIGHT, getDrawingPanel()));
      } else if (types[i] == QuadCurve.class) {
        components.add(new QuadCurve(xCoord(i), yCoord(i), xCoord(i) + COL_WIDTH, yCoord(i), xCoord(i) + COL_WIDTH, yCoord(i) + ROW_HEIGHT, getDrawingPanel()));
      } else if (types[i] == Path.class) {
        Path path = new Path(getDrawingPanel());
        path.moveTo(xCoord(i), yCoord(i));
        path.lineTo(xCoord(i) + COL_WIDTH, yCoord(i) + ROW_HEIGHT);
        path.curveTo(xCoord(i) + COL_WIDTH, yCoord(i), xCoord(i), yCoord(i), xCoord(i), yCoord(i) + ROW_HEIGHT);
        path.quadTo(xCoord(i) + COL_WIDTH, yCoord(i) + ROW_HEIGHT, xCoord(i) + COL_WIDTH, yCoord(i));
        components.add(path);
      } else if (types[i] == Text.class) {
        Text text = new Text(" Gann Graphics ", xCoord(i), yCoord(i), getDrawingPanel());
        text.translate(0, text.getHeight());
        components.add(text);
      } else if (types[i] == Image.class) {
        Image image = new Image("/java-logo.png", xCoord(i), yCoord(i), getDrawingPanel());
        image.setWidth(COL_WIDTH);
        image.setHeight(ROW_HEIGHT);
        components.add(image);
      } else {
        components.add(new Rectangle(xCoord(i), yCoord(i), COL_WIDTH, ROW_HEIGHT, getDrawingPanel()));
      }
      components.get(i).setStrokeColor(randomColor());
      components.get(i).setFillColor(adjustColor(components.get(i).getStrokeColor(), 50));
      components.get(i).setStroke(new BasicStroke((float) (Math.random() * Math.min(COL_SPACE, ROW_SPACE))));
    }
    start = System.currentTimeMillis();
  }

  public void loop() {
    if (System.currentTimeMillis() > start + DELAY) {
      for (int i = 0; i < components.size(); i++) {
        components.get(i).translate(Math.cos(headings.get(i)) * SPEED, Math.sin(headings.get(i)) * SPEED);
        if (components.get(i).getX() < 0 - COL_WIDTH) {
          components.get(i).setOrigin(getDrawingPanel().getWidth(), components.get(i).getY());
        } else if (components.get(i).getX() > getDrawingPanel().getWidth()) {
          components.get(i).setOrigin(0 - COL_WIDTH, components.get(i).getY());
        }
        if (components.get(i).getY() < 0 - ROW_HEIGHT) {
          components.get(i).setOrigin(components.get(i).getX(), getDrawingPanel().getHeight());
        } else if (components.get(i).getY() > getDrawingPanel().getHeight()) {
          components.get(i).setOrigin(components.get(i).getX(), 0 - ROW_HEIGHT);
        }
      }
    }
    sleep(100);
  }
}

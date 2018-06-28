package gann.graphics.geom;

import gann.graphics.Drawable2D;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.Rectangle2D;

public class Rectangle extends Drawable2D {
  public Rectangle(double x, double y, double width, double height, DrawingPanel drawingPanel) {
    try {
      setShape(new Rectangle2D.Double(x, y, width, height));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }
}

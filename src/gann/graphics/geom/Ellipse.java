package gann.graphics.geom;

import gann.graphics.Drawable2D;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.Ellipse2D;

public class Ellipse extends Drawable2D {
  public Ellipse(double x, double y, double width, double height, DrawingPanel drawingPanel) {
    try {
      setShape(new Ellipse2D.Double(x, y, width, height));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }
}

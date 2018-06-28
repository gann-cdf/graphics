package gann.graphics.geom;

import gann.graphics.Drawable2D;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.RoundRectangle2D;

public class RoundRectangle extends Drawable2D {
  public RoundRectangle(double x, double y, double width, double height, double arcWidth, double arcHeight, DrawingPanel drawingPanel) {
    try {
      setShape(new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }
}

package gann.graphics.geom;

import gann.graphics.Drawable2D;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.*;

public class Arc extends Drawable2D {
  public Arc(double x, double y, double width, double height, double start, double extent, DrawingPanel drawingPanel) {
    try {
      setShape(new Arc2D.Double(x, y, width, height, start, extent, Arc2D.PIE));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }
}

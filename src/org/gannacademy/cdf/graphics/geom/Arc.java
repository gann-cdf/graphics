package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Draw an arc as a section of an ellipse
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Arc extends Drawable2D {
  /**
   * <p>Construct a new arc</p>
   *
   * <p><img src="doc-files/Arc.png" alt="Diagram of Arc parameters"></p>
   *
+    *
   * @param x            coordinate of origin
   * @param y            coordinate of origin
   * @param width        in pixels
   * @param height       in piels
   * @param start        angle in degrees (measured counter-clockwise from 0&deg; (a.k.a. "east")
   * @param extent       angle in degrees of the arc (measured counter-clockwise from {@code start})
   * @param drawingPanel on which to draw
   */
  public Arc(double x, double y, double width, double height, double start, double extent, DrawingPanel drawingPanel) {
    try {
      setShape(new Arc2D.Double(x, y, width, height, start, extent, Arc2D.PIE));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof Arc2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set Arc's underlying shape to a non-Arc2D instance");
    }
  }
}

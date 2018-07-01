package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Draw a round rectangle (i.e. a rectangle with round corners)
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class RoundRectangle extends Drawable2D {
  /**
   * <p>Construct a new round rectangle</p>
   *
   * <p><img src="doc-files/RoundRectangle.png" alt="Diagram of RoundRectangle parameters"></p>
   *
   * <p>It is worth noting that window coordinate system has its origin in the top, left corner of the window, and that
   * the X-axis increases from left to right, while the Y-axis increases from <i>top to bottom</i>. All coordinates that
   * are displated are in the first quadrant.</p>
   *
   * @param x            coordinate of origin
   * @param y            coordinate of origin
   * @param width        in pixels
   * @param height       in piels
   * @param arcWidth     in pixels, width of the ellipse that defines the round corners
   * @param arcHeight    in pixels, height of the ellipse that defines the round corners
   * @param drawingPanel on which to draw
   */
  public RoundRectangle(double x, double y, double width, double height, double arcWidth, double arcHeight, DrawingPanel drawingPanel) {
    try {
      setShape(new RoundRectangle2D.Double(x, y, width, height, arcWidth, arcHeight));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof RoundRectangle2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set RoundRectangle's underlying shape to a non-RoundRectangle2D instance");
    }
  }
}

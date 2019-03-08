package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * <p>Draw a rectangle</p>
 *
 * <p><img src="doc-files/Rectangle.png" alt="Rectangle diagram"></p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Rectangle extends Drawable2D {
  /**
   * <p>Construct a new rectangle</p>
   *
   * <p><img src="doc-files/Rectangle.png" alt="Diagram of Rectangle parameters"></p>
   *
   * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
   * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
   *
   * <p><img src="../doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
   *
   * @param x            coordinate of origin
   * @param y            coordinate of origin
   * @param width        in pixels
   * @param height       in pixels
   * @param drawingPanel on which to draw
   */
  public Rectangle(double x, double y, double width, double height, DrawingPanel drawingPanel) {
    try {
      setShape(new Rectangle2D.Double(x, y, width, height));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
        System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof Rectangle2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set Rectangles's underlying shape to a non-Rectangle2D instance");
    }
  }
}

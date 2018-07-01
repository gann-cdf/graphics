package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Draw an ellipse
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Ellipse extends Drawable2D {
  /**
   * <p>Construct a new ellipse</p>
   *
   * <p><img src="doc-files/Ellipse.png" alt="Diagram of Ellipse parameters"></p>
   *
   * <p>It is worth noting that window coordinate system has its origin in the top, left corner of the window, and that
   * the X-axis increases from left to right, while the Y-axis increases from <i>top to bottom</i>. All coordinates that
   * are displated are in the first quadrant.</p>
   *
   * @param x            coordinate of origin
   * @param y            coordinate of origin
   * @param width        in pixels
   * @param height       in pixels
   * @param drawingPanel on which to draw
   */
  public Ellipse(double x, double y, double width, double height, DrawingPanel drawingPanel) {
    try {
      setShape(new Ellipse2D.Double(x, y, width, height));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof Ellipse2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set Ellipse's underlying shape to a non-Ellipse2D instance");
    }
  }
}

package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * <p>Draw an ellipse</p>
 *
 * <p><img src="doc-files/Ellipse.png" alt="Ellipse diagram"></p>
 *
 * <p>Ellipses are drawn inscribed within their rectangular bounding box.</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Ellipse extends Drawable2D {
  /**
   * <p>Construct a new ellipse</p>
   *
   * <p><img src="doc-files/Ellipse.png" alt="Diagram of Ellipse parameters"></p>
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
  public Ellipse(double x, double y, double width, double height, DrawingPanel drawingPanel) {
    try {
      setShape(new Ellipse2D.Double(x, y, width, height));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
        System.err.println(e.getMessage());
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

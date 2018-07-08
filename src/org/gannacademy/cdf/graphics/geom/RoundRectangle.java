package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * <p>Draw a round rectangle (i.e. a rectangle with round corners)</p>
 *
 * <p><img src="doc-files/RoundRectangle.png" alt="Round rectangle diagram"></p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class RoundRectangle extends Drawable2D {
  /**
   * <p>Construct a new round rectangle</p>
   *
   * <p><img src="doc-files/RoundRectangle.png" alt="Diagram of RoundRectangle parameters"></p>
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

  protected RoundRectangle2D getShapeAsRoundRectangle() {
    return (RoundRectangle2D) getShape();
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof RoundRectangle2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set RoundRectangle's underlying shape to a non-RoundRectangle2D instance");
    }
  }

  /**
   * Width of ellipse defining corners
   *
   * @return Arc width
   * @see RoundRectangle#getArcWidth()
   */
  public double getArcWidth() {
    return getShapeAsRoundRectangle().getArcWidth();
  }

  /**
   * Height of ellipse defining corners
   *
   * @return Arc height
   * @see RoundRectangle#getArcHeight()
   */
  public double getArcHeight() {
    return getShapeAsRoundRectangle().getArcHeight();
  }
}

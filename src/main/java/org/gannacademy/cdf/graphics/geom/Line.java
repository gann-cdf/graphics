package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * <p>Draw a line</p>
 *
 * <p><img src="doc-files/Line.png" alt="Line diagram"></p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Line extends Drawable {
  /**
   * <p>Construct a new line</p>
   *
   * <p><img src="doc-files/Line.png" alt="Diagram of Line parameters"></p>
   *
   * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
   * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
   *
   * <p><img src="../doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
   *
   * @param x1           coordinate of starting point
   * @param y1           coordinate of starting point
   * @param x2           coordinate of ending point
   * @param y2           coordinate of ending point
   * @param drawingPanel on which to draw
   */
  public Line(double x1, double y1, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new Line2D.Double(x1, y1, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
        System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * <p>Construct a new line</p>
   *
   * <p><img src="doc-files/Line.png" alt="Diagram of Line parameters"></p>
   *
   * @param p1           Starting point
   * @param p2           Ending point
   * @param drawingPanel on which to draw
   */
  public Line(Point2D p1, Point2D p2, DrawingPanel drawingPanel) {
    try {
      setShape(new Line2D.Double(p1, p2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return Underlying {@link Line2D} geometry
   */
  protected Line2D getShapeAsLine() {
    return (Line2D) getShape();
  }

  /**
   * <p>Replace the underlying {@link Line2D} geometry</p>
   *
   * <p>Changing the geometry leaves other characteristics (stroke color, style) unchanged.</p>
   *
   * @param shape of new geometry (must be and instance of {@link Line2D})
   * @throws DrawableException if {@code shape} is not an instance of {@link Line2D}
   */
  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof Line2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set Line's underlying shape to a non-Line2D instance");
    }
  }

  @Override
  public void setWidth(double width) {
    // FIXME this feels hacktacular
    double scale = width / getWidth();
    setLine(
            getX() + (getX1() - getX()) * scale, getY1(),
            getX() + (getX2() - getX()) * scale, getY2()
    );
  }

  @Override
  public void setHeight(double height) {
    // FIXME this feels hacktacular
    double scale = height / getHeight();
    setLine(
            getX1(), getY() + (getY1() - getY()) * scale,
            getX2(), getY() + (getY2() - getY()) * scale
    );
  }

  /**
   * Starting point
   *
   * @return Starting point
   * @see Line2D#getP1()
   */
  public Point2D getP1() {
    return getShapeAsLine().getP1();
  }

  /**
   * Ending point
   *
   * @return Ending point
   * @see Line2D#getP2()
   */
  public Point2D getP2() {
    return getShapeAsLine().getP2();
  }

  /**
   * Replace the underlying geometry
   *
   * @param x1 coordinate of the starting point
   * @param y1 coordinate of the starting point
   * @param x2 coordinate of the ending point
   * @param y2 coordinate of the ending point
   */
  public void setLine(double x1, double y1, double x2, double y2) {
    getShapeAsLine().setLine(x1, y1, x2, y2);
  }

  /**
   * X-coordinate of starting point
   *
   * @return X-coordinate of starting point
   * @see Line2D#getX1()
   */
  public double getX1() {
    return getShapeAsLine().getX1();
  }

  /**
   * Y-coordinate of starting point
   *
   * @return Y-coordinate of starting point
   * @see Line2D#getY1()
   */
  public double getY1() {
    return getShapeAsLine().getY1();
  }

  /**
   * X-coordinate of ending point
   *
   * @return X-coordinate of ending point
   * @see Line2D#getX2()
   */
  public double getX2() {
    return getShapeAsLine().getX2();
  }

  /**
   * Y-coordinate of ending point
   *
   * @return Y-coordinate of ending point
   * @see Line2D#getY2()
   */
  public double getY2() {
    return getShapeAsLine().getY2();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsLine().setLine(getX1() + dx, getY1() + dy, getX2() + dx, getY2() + dy);
  }

  @Override
  public void setLocation(double x, double y) {
    getShapeAsLine().setLine(x, y, x + getX1() - getX2(), y + getY1() - getY2());
  }
}

package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 * <p>Draw a quadratic Bézier curve</p>
 *
 * <p><img src="doc-files/Bezier-QuadCurve-fig4.png" alt="Quadratic Bézier curve"></p>
 *
 * <p>Refer to {@link CubicCurve} for a full explanation of the Bézier interpolation process (which explains what the
 * control point does).</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues" target="_blank">Seth Battis</a>
 */
public class QuadCurve extends Drawable {
  /**
   * <p>Construct a new quadratic Bézier curve</p>
   *
   * <p><img src="doc-files/QuadCurve.png" alt="Diagram of QuadCurve parameters"></p>
   *
   * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
   * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
   *
   * <p><img src="../doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
   *
   * @param x1           X-coordinate of starting point
   * @param y1           Y-coordinate of starting point
   * @param controlX     X-coordinate of control point
   * @param controlY     Y-coordinate of control point
   * @param x2           X-coordinate of ending point
   * @param y2           Y-coordinate of ending point
   * @param drawingPanel on which to draw
   */
  public QuadCurve(double x1, double y1, double controlX, double controlY, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new QuadCurve2D.Double(x1, y1, controlX, controlY, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
        System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * @return Underlying {@link QuadCurve2D} geometry
   */
  protected QuadCurve2D getShapeAsQuadCurve() {
    return (QuadCurve2D) getShape();
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof QuadCurve2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set QuadCurve's underlying shape to a non-QuadCurve2D instance");
    }
  }

  @Override
  public void setWidth(double width) {
    // FIXME this feels hacktacular -- surely there's a better way to do this
    double scale = width / getWidth();
    setCurve(
            getX() + (getX1() - getX()) * scale, getY1(),
            getX() + (getCtrlX() - getX()) * scale, getCtrlY(),
            getX() + (getX2() - getX()) * scale, getY2()
    );
  }

  @Override
  public void setHeight(double height) {
    // FIXME this feels hacktacular -- surely there's a better way to do this
    double scale = height / getHeight();
    setCurve(
            getX1(), getY() + (getY1() - getY()) * scale,
            getCtrlX(), getY() + (getCtrlY() - getY()) * scale,
            getX2(), getY() + (getY2() - getY()) * scale
    );
  }

  /**
   * Starting point
   *
   * @return Coordinates of starting point
   */
  public Point2D getP1() {
    return getShapeAsQuadCurve().getP1();
  }

  /**
   * Ending point
   *
   * @return Coordinates of ending point
   */
  public Point2D getP2() {
    return getShapeAsQuadCurve().getP2();
  }

  /**
   * <p>Replace the underlying {@link QuadCurve2D} geometry</p>
   *
   * <p><img src="doc-files/QuadCurve.png" alt="Diagram of QuadCurve parameters"></p>
   *
   * <p>Replacing the underlying geometry leaves other characteristics (fill, stroke) unchanged</p>
   *
   * @param x1    X-coordinate of starting point
   * @param y1    Y-coordinate of starting point
   * @param ctrlX X-Coordinate of control point
   * @param ctrlY Y-coordinate of control point
   * @param x2    X-coordinate of ending point
   * @param y2    Y-coordinate of ending point
   */
  public void setCurve(double x1, double y1, double ctrlX, double ctrlY, double x2, double y2) {
    getShapeAsQuadCurve().setCurve(x1, y1, ctrlX, ctrlY, x2, y2);
  }

  /**
   * X-coordinate of starting point
   *
   * @return X-coordinate of starting point
   */
  public double getX1() {
    return getShapeAsQuadCurve().getX1();
  }

  /**
   * Y-coordinate of starting point
   *
   * @return Y-coordinate of starting point
   */
  public double getY1() {
    return getShapeAsQuadCurve().getY1();
  }

  /**
   * X-coordinate of control point
   *
   * @return X-coordinate of control point
   */
  public double getCtrlX() {
    return getShapeAsQuadCurve().getCtrlX();
  }

  /**
   * Y-coordinate of control point
   *
   * @return Y-coordinate of control point
   */
  public double getCtrlY() {
    return getShapeAsQuadCurve().getCtrlY();
  }

  /**
   * Control point
   *
   * @return Coordinates of control point
   */
  public Point2D getCtrlPt() {
    return getShapeAsQuadCurve().getCtrlPt();
  }


  /**
   * X-coordinate of ending point
   *
   * @return X-coordinate of ending point
   */
  public double getX2() {
    return getShapeAsQuadCurve().getX2();
  }

  /**
   * Y-coordinate of ending point
   *
   * @return Y-coordinate of ending point
   */
  public double getY2() {
    return getShapeAsQuadCurve().getY2();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsQuadCurve().setCurve(getX1() + dx, getY1() + dy, getCtrlX() + dx, getCtrlY() + dy, getX2() + dx, getY2() + dy);
  }

  @Override
  public void setLocation(double x, double y) {
    translate(x - getX(), y - getY());
  }
}

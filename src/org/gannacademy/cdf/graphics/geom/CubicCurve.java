package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

/**
 * Draw a cubic curve
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class CubicCurve extends Drawable {
  /**
   * <p>Construct a new cubic curve with no points</p>
   *
   * <p>You will need to use {@link #setCurve(double, double, double, double, double, double, double, double)} to make
   * the curve exist (and visible)</p>
   *
   * @param drawingPanel on which to draw
   */
  public CubicCurve(DrawingPanel drawingPanel) {
    try {
      setShape(new CubicCurve2D.Double());
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>A cubic curve is one shaped like a cubic function (<i>x</i><sup>3</sup>)</p>
   *
   * <p><img src="doc-files/CubicCurve.png" alt="Diagram of CubicCurve parameetrs"></p>
   *
   * @param x1           X-coordinate of Point 1
   * @param y1           Y-coordinate of Point 1
   * @param ctrlX1       X-coordinate of Control Point 1
   * @param ctrlY1       Y-coordinate of Control Point 1
   * @param ctrlX2       X-coordinate of Control Point 2
   * @param ctrlY2       Y-coordinate of Control Point 2
   * @param x2           X-coordinate of Point 2
   * @param y2           Y-coordinate of Point 2
   * @param drawingPanel on which to draw
   */
  public CubicCurve(double x1, double y1, double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new CubicCurve2D.Double(x1, y1, ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return {@code shape} field cast as a {@link CubicCurve2D}
   */
  protected CubicCurve2D getShapeAsCubicCurve() {
    return (CubicCurve2D) getShape();
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof CubicCurve2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set CubicCuve's underlying shape to a non-CubicCurve2D instance");
    }
  }

  /**
   * @return Point 1
   * @see CubicCurve2D#getP1()
   */
  public Point2D getP1() {
    return getShapeAsCubicCurve().getP1();
  }

  /**
   * @return Point 2
   * @see CubicCurve2D#getP2()
   */
  public Point2D getP2() {
    return getShapeAsCubicCurve().getP2();
  }

  /**
   * <p>Set the points describing the curve</p>
   *
   * <p>This will leave other characteristics (e.g. fill color or stroke) unchanged</p>
   *
   * <p><img src="doc-files/CubicCurve.png" alt="Diagram of setCurve() Parameters"></p>
   *
   * @param x1     X-coordinate of Point 1
   * @param y1     Y-coordinate of Point 1
   * @param ctrlx1 X-coordinate of Control Point 1
   * @param ctrly1 Y-coordinate of Control POint 1
   * @param ctrlx2 X-coordinate of Control Point 2
   * @param ctrly2 Y-coordinate of Control Point 2
   * @param x2     X-coordinate of Point 2
   * @param y2     Y-coordinate of Point 2
   */
  public void setCurve(double x1, double y1, double ctrlx1, double ctrly1, double ctrlx2, double ctrly2, double x2, double y2) {
    getShapeAsCubicCurve().setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
  }

  public double getX() {
    return getShapeAsCubicCurve().getBounds().getX();
  }

  public double getY() {
    return getShapeAsCubicCurve().getBounds().getY();
  }

  /**
   * @return X-coordinate of Point 1
   * @see CubicCurve2D#getX1()
   */
  public double getX1() {
    return getShapeAsCubicCurve().getX1();
  }

  /**
   * @return Y-coordinate of Point 1
   * @see CubicCurve2D#getY1()
   */
  public double getY1() {
    return getShapeAsCubicCurve().getY1();
  }

  /**
   * @return X-coordinate of Control Point 1
   * @see CubicCurve2D#getCtrlX1()
   */
  public double getCtrlX1() {
    return getShapeAsCubicCurve().getCtrlX1();
  }

  /**
   * @return Y-coordinate of Control Point 1
   * @see CubicCurve2D#getCtrlY1()
   */
  public double getCtrlY1() {
    return getShapeAsCubicCurve().getCtrlY1();
  }

  /**
   * @return Control Point 1
   * @see CubicCurve2D#getCtrlP1()
   */
  public Point2D getCtrlP1() {
    return getShapeAsCubicCurve().getCtrlP1();
  }

  /**
   * @return X-coordinate of Control Point 2
   * @see CubicCurve2D#getCtrlX2()
   */
  public double getCtrlX2() {
    return getShapeAsCubicCurve().getCtrlX2();
  }

  /**
   * @return Y-coordinate of Control Point 2
   * @see CubicCurve2D#getCtrlY2()
   */
  public double getCtrlY2() {
    return getShapeAsCubicCurve().getCtrlY2();
  }

  /**
   * @return Point 2
   * @see CubicCurve2D#getP2()
   */
  public Point2D getCtrlP2() {
    return getShapeAsCubicCurve().getCtrlP2();
  }

  /**
   * @return X-coordinate of Point 2
   * @see CubicCurve2D#getX2()
   */
  public double getX2() {
    return getShapeAsCubicCurve().getX2();
  }

  /**
   * @return Y-coordinate of Point 2
   * @see CubicCurve2D#getY2()
   */
  public double getY2() {
    return getShapeAsCubicCurve().getY2();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsCubicCurve().setCurve(getX1() + dx, getY1() + dy, getCtrlX1() + dx, getCtrlY1() + dy, getCtrlX2() + dx, getCtrlY2() + dy, getX2() + dx, getY2() + dy);
  }

  @Override
  public void setOrigin(double x, double y) {
    translate(x - getX(), y - getY());
  }
}

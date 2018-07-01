package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class QuadCurve extends Drawable {
  public QuadCurve(DrawingPanel drawingPanel) {
    try {
      setShape(new QuadCurve2D.Double());
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p><img src="doc-files/QuadCurve.png" alt="Diagram of QuadCurve parameters"></p>
   *
   * @param x1
   * @param y1
   * @param controlX
   * @param controlY
   * @param x2
   * @param y2
   * @param drawingPanel
   */
  public QuadCurve(double x1, double y1, double controlX, double controlY, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new QuadCurve2D.Double(x1, y1, controlX, controlY, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

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

  public Point2D getP1() {
    return getShapeAsQuadCurve().getP1();
  }

  public Point2D getP2() {
    return getShapeAsQuadCurve().getP2();
  }

  public void setCurve(double x1, double y1, double ctrlx, double ctrly, double x2, double y2) {
    getShapeAsQuadCurve().setCurve(x1, y1, ctrlx, ctrly, x2, y2);
  }

  public double getX() {
    return getShapeAsQuadCurve().getBounds().getX();
  }

  public double getY() {
    return getShapeAsQuadCurve().getBounds().getY();
  }

  public double getX1() {
    return getShapeAsQuadCurve().getX1();
  }

  public double getY1() {
    return getShapeAsQuadCurve().getY1();
  }

  public double getCtrlX() {
    return getShapeAsQuadCurve().getCtrlX();
  }

  public double getCtrlY() {
    return getShapeAsQuadCurve().getCtrlY();
  }

  public Point2D getCtrlPt() {
    return getShapeAsQuadCurve().getCtrlPt();
  }

  public double getX2() {
    return getShapeAsQuadCurve().getX2();
  }

  public double getY2() {
    return getShapeAsQuadCurve().getY2();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsQuadCurve().setCurve(getX1() + dx, getY1() + dy, getCtrlX() + dx, getCtrlY() + dy, getX2() + dx, getY2() + dy);
  }

  @Override
  public void setOrigin(double x, double y) {
    translate(x - getX(), y - getY());
  }
}

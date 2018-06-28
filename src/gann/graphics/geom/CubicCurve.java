package gann.graphics.geom;

import gann.graphics.Drawable;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CubicCurve extends Drawable {
  public CubicCurve(DrawingPanel drawingPanel) {
    try {
      setShape(new CubicCurve2D.Double());
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public CubicCurve(double x1, double y1, double x1control, double y1control, double x2control, double y2control, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new CubicCurve2D.Double(x1, y1, x1control, y1control, x2control, y2control, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  protected CubicCurve2D getShapeAsCubicCurve() {
    return (CubicCurve2D) getShape();
  }

  public Point2D getP1() {
    return getShapeAsCubicCurve().getP1();
  }

  public Point2D getP2() {
    return getShapeAsCubicCurve().getP2();
  }

  public void setCurve(double x1, double y1, double ctrlx1, double ctrly1, double ctrlx2, double ctrly2, double x2, double y2) {
    getShapeAsCubicCurve().setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
  }

  public double getX() {
    return getShapeAsCubicCurve().getBounds().getX();
  }

  public double getY() {
    return getShapeAsCubicCurve().getBounds().getY();
  }

  public double getX1() {
    return getShapeAsCubicCurve().getX1();
  }

  public double getY1() {
    return getShapeAsCubicCurve().getY1();
  }

  public double getCtrlX1() {
    return getShapeAsCubicCurve().getCtrlX1();
  }

  public double getCtrlY1() {
    return getShapeAsCubicCurve().getCtrlY1();
  }

  public Point2D getCtrlP1() {
    return getShapeAsCubicCurve().getCtrlP1();
  }

  public double getCtrlX2() {
    return getShapeAsCubicCurve().getCtrlX2();
  }

  public double getCtrlY2() {
    return getShapeAsCubicCurve().getCtrlY2();
  }

  public Point2D getCtrlP2() {
    return getShapeAsCubicCurve().getCtrlP2();
  }

  public double getX2() {
    return getShapeAsCubicCurve().getX2();
  }

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

package gann.graphics.geom;

import gann.graphics.Drawable;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
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

  public double getCtrlX(){
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

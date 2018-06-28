package gann.graphics.geom;

import gann.graphics.Drawable;
import gann.graphics.DrawableException;
import gann.graphics.ui.DrawingPanel;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Line extends Drawable {
  public Line(double x1, double y1, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new Line2D.Double(x1, y1, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public Line(Point2D p1, Point2D p2, DrawingPanel drawingPanel) {
    try {
      setShape(new Line2D.Double(p1, p2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  protected Line2D getShapeAsLine() {
    return (Line2D) getShape();
  }

  public Point2D getP1() {
    return getShapeAsLine().getP1();
  }

  public Point2D getP2() {
    return getShapeAsLine().getP2();
  }

  public void setLine(double x1, double y1, double x2, double y2) {
    getShapeAsLine().setLine(x1, y1, x2, y2);
  }

  public double getX() {
    return getShapeAsLine().getBounds().getX();
  }

  public double getX1() {
    return getShapeAsLine().getX1();
  }

  public double getY() {
    return getShapeAsLine().getBounds().getY();
  }

  public double getY1() {
    return getShapeAsLine().getY1();
  }

  public double getX2() {
    return getShapeAsLine().getX2();
  }

  public double getY2() {
    return getShapeAsLine().getY2();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsLine().setLine(getX1() + dx, getY1() + dy, getX2() + dx, getY2() + dy);
  }

  @Override
  public void setOrigin(double x, double y) {
    getShapeAsLine().setLine(x, y, x + getX1() - getX2(), y + getY1() - getY2());
  }
}

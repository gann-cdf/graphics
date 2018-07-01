package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Path extends Drawable {
  public Path(DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double());
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public Path(int rule, DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double(rule));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public Path(int rule, int initialCapacity, DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double(rule, initialCapacity));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public Path(Shape shape, DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double(shape));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public Path(Shape shape, AffineTransform transformation, DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double(shape, transformation));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  protected Path2D getShapeAsPath() {
    return (Path2D) getShape();
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof Path2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set Path's underlying shape to a non-Path2D instance");
    }
  }

  public void curveTo(double x1, double y1, double x2, double y2, double x3, double y3) {
    getShapeAsPath().curveTo(x1, y1, x2, y2, x3, y3);
  }

  public void lineTo(double x, double y) {
    getShapeAsPath().lineTo(x, y);
  }

  @Override
  public double getX() {
    return getShapeAsPath().getBounds().getX();
  }

  @Override
  public double getY() {
    return getShapeAsPath().getBounds().getY();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsPath().transform(AffineTransform.getTranslateInstance(dx, dy));
  }

  @Override
  public void setOrigin(double x, double y) {
    translate(x - getX(), y - getY());
  }

  public void moveTo(double x, double y) {
    getShapeAsPath().moveTo(x, y);
  }

  public void quadTo(double controlX, double controlY, double x, double y) {
    getShapeAsPath().quadTo(controlX, controlY, x, y);
  }

  public void transform(AffineTransform transformation) {
    getShapeAsPath().transform(transformation);
  }
}

package org.gannacademy.cdf.graphics;

import java.awt.*;
import java.awt.geom.RectangularShape;

public class Drawable2D extends Drawable {
  private RectangularShape shape;

  @Override
  protected void setShape(Shape shape) throws DrawableException {
    if (shape instanceof RectangularShape) {
      /*
       * FIXME This is hacktacular. this.shape hides super.shape, but both need to be set: super.shape is required to
       * allow setDrawingPanel() to catch misordered assignments, but this.shape is required to allow pass-through methods in
       * Drawable2D below. Oy.
       */
      super.setShape(shape);
      this.shape = (RectangularShape) shape;
    } else {
      throw new DrawableException("Cannot convert a Shape to a RectangularShape");
    }
  }

  @Override
  public double getX() {
    return shape.getX();
  }

  @Override
  public double getY() {
    return shape.getY();
  }

  public void translate(double dx, double dy) {
    ((RectangularShape) getShape()).setFrame(getX() + dx, getY() + dy, getWidth(), getHeight());
  }

  public void setOrigin(double x, double y) {
    ((RectangularShape) getShape()).setFrame(x, y, getWidth(), getHeight());
  }

  public double getWidth() {
    return shape.getWidth();
  }

  public double getHeight() {
    return shape.getHeight();
  }

  public boolean isEmpty() {
    return shape.isEmpty();
  }

  public void setFrame(double x, double y, double width, double height) {
    shape.setFrame(x, y, width, height);
  }
}

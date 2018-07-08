package org.gannacademy.cdf.graphics;

import java.awt.*;
import java.awt.geom.RectangularShape;

/**
 * <p>Superclass of two-dimensional ("rectangular") drawable components</p>
 *
 * <p>What makes a component "rectangular"? It's defined by an origin, width and height &mdash; like a
 * {@link org.gannacademy.cdf.graphics.geom.Rectangle} or an {@link org.gannacademy.cdf.graphics.geom.Ellipse}.</p>
 *
 * @author <a href="http://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Drawable2D extends Drawable {
  private RectangularShape shape;

  /**
   * <p>Replace the underlying {@link Shape} geometry of the component</p>
   *
   * <p>Replacing the {@link Shape} geometry leaves other characteristics (fill, stroke) untouched.</p>
   *
   * @param shape of geometry
   * @throws DrawableException if {@code shape} is incompatible with the class
   */
  @Override
  public void setShape(Shape shape) throws DrawableException {
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
  public void setWidth(double width) {
    ((RectangularShape) getShape()).setFrame(getX(), getY(), width, getHeight());
  }

  @Override
  public void setHeight(double height) {
    ((RectangularShape) getShape()).setFrame(getX(), getY(), getWidth(), height);
  }

  @Override
  public void translate(double dx, double dy) {
    ((RectangularShape) getShape()).setFrame(getX() + dx, getY() + dy, getWidth(), getHeight());
  }

  @Override
  public void setLocation(double x, double y) {
    translate(x - getX(), y - getY());
  }

  /**
   * Test if shape geometry is defined
   *
   * @return {@code true} if the shape does not contain geometry, {@code false} otherwise
   */
  public boolean isEmpty() {
    return shape.isEmpty();
  }

  /**
   * <p>Adjust the frame of the shape's enclosing bounding box</p>
   *
   * <p>Adjusting the frame of the shape resizes the geometry to fit within the new bounds</p>
   *
   * @param x      coordinate of the origin of the new frame
   * @param y      coordinate of the origin of the new frame
   * @param width  of the new frame
   * @param height of the new frame
   */
  public void setFrame(double x, double y, double width, double height) {
    shape.setFrame(x, y, width, height);
  }
}

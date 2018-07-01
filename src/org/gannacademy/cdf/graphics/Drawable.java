package org.gannacademy.cdf.graphics;

import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * <p>The superclass of all drawable components</p>
 *
 * <p>This class defines most of the core operations that define what it means to be drawable (that the component is
 * associated with a drawing panel, has a stroke and/or fill color, perhaps a particular stroke style).</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public abstract class Drawable {
  /**
   * A transparent color constent, to hide either stroke or fill
   */
  public static final Color TRANSPARENT = new Color(0, true);

  /**
   * A zero-width stroke
   */
  public static final Stroke NO_STROKE = new BasicStroke(0);

  private DrawingPanel drawingPanel;
  private Shape shape;
  private Stroke stroke = new BasicStroke();
  private boolean filled = false;
  private Color strokeColor = Color.BLACK, fillColor = TRANSPARENT;

  /**
   * @return Drawing panel on which this component is drawn (may be {@code null})
   */
  public DrawingPanel getDrawingPanel() {
    return drawingPanel;
  }

  /**
   * Change the drawing panel on which this component is drawn
   *
   * @param drawingPanel on which to draw
   */
  public void setDrawingPanel(DrawingPanel drawingPanel) {
    if (this.drawingPanel != null) {
      this.drawingPanel.remove(this);
    }
    this.drawingPanel = drawingPanel;
    this.drawingPanel.add(this);
  }

  /**
   * <p>Remove this component from its associated drawing panel</p>
   *
   * <p>The component will no longer be drawn, but other references to the component will still be valid, and the
   * component may be added to a drawing panel later to be redrawn</p>
   */
  public void removeFromDrawingPanel() {
    getDrawingPanel().remove(this);
    this.drawingPanel = null;
  }

  /**
   * Get the {@link Shape} that underpins this component
   * @return
   */
  public Shape getShape() {
    return shape;
  }

  protected void setShape(Shape shape) throws DrawableException {
    this.shape = shape;
  }

  public abstract double getX();

  public abstract double getY();

  public abstract void translate(double dx, double dy);

  public abstract void setOrigin(double x, double y);

  public Stroke getStroke() {
    return stroke;
  }

  public void setStroke(Stroke stroke) {
    this.stroke = stroke;
  }

  public boolean isFilled() {
    return filled;
  }

  public void setFilled(boolean filled) {
    this.filled = filled;
  }

  public Color getStrokeColor() {
    return strokeColor;
  }

  public void setStrokeColor(Color color) {
    this.strokeColor = color;
  }

  public Color getFillColor() {
    return fillColor;
  }

  public void setFillColor(Color color) {
    this.fillColor = color;
    setFilled(!fillColor.equals(TRANSPARENT));
  }

  public void draw(Graphics2D graphics) {
    if (isFilled()) {
      graphics.setPaint(getFillColor());
      graphics.fill(getShape());
    }
    if (getStroke() != NO_STROKE) {
      graphics.setPaint(getStrokeColor());
      graphics.setStroke(getStroke());
      graphics.draw(getShape());
    }
  }

  public Rectangle getBounds() {
    return shape.getBounds();
  }

  public Rectangle2D getBounds2D() {
    return shape.getBounds2D();
  }

  public boolean contains(double x, double y) {
    return shape.contains(x, y);
  }

  public boolean contains(Point2D point) {
    return shape.contains(point);
  }

  public boolean intersects(double x, double y, double width, double height) {
    return shape.intersects(x, y, width, height);
  }

  public boolean intersects(Rectangle2D rectangle) {
    return shape.intersects(rectangle);
  }

  public boolean contains(double x, double y, double width, double height) {
    return shape.contains(x, y, width, height);
  }

  public boolean contains(Rectangle2D rectangle) {
    return shape.contains(rectangle);
  }

  public PathIterator getPathIterator(AffineTransform transformation) {
    return shape.getPathIterator(transformation);
  }

  public PathIterator getPathIterator(AffineTransform transformation, double flatness) {
    return shape.getPathIterator(transformation, flatness);
  }

  public void close() {
    if (drawingPanel != null) {
      drawingPanel.remove(this);
      drawingPanel = null;
    }
  }

  @Override
  public void finalize() {
    if (drawingPanel != null) {
      close();
    }
  }
}

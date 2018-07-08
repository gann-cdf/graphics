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
   * A transparent color constant, to hide either stroke or fill
   */
  public static final Color TRANSPARENT = new Color(0, true);

  /**
   * A zero-width stroke constant, to hide the stroke (no matter the stroke color)
   */
  public static final Stroke NO_STROKE = new BasicStroke(0);

  private DrawingPanel drawingPanel;
  private Shape shape;
  private Stroke stroke = new BasicStroke();
  private boolean filled = false;
  private Color strokeColor = Color.BLACK, fillColor = TRANSPARENT;

  /**
   * Drawing panel on which component is drawn
   *
   * @return Drawing panel on which this component is drawn (may be {@code null})
   */
  public DrawingPanel getDrawingPanel() {
    return drawingPanel;
  }

  /**
   * <p>Change the drawing panel on which this component is drawn</p>
   *
   * <p>This method may also be used as a "hack" adjust the order in which drawing components are stacked. Drawable
   * components are drawn on the screen in the order in which they are declared, oldest to newest, with the newest in front
   * of the older components. (Re)setting the drawing panel of a component will pull it forward, in front of newer
   * components, as though it had just been declared.</p>
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
   * Underlying {@link Shape} geometry
   *
   * @return Underlying {@link Shape} geometry
   */
  public Shape getShape() {
    return shape;
  }

  /**
   * Replace the underlying {@link Shape} geometry of the drawable component
   *
   * @param shape of geometry
   * @throws DrawableException will be thrown if {@code shape} is not compatible with the component (e.g trying to
   *                           redefine an {@link org.gannacademy.cdf.graphics.geom.Arc} as a
   *                           {@link org.gannacademy.cdf.graphics.geom.Line})
   */
  public void setShape(Shape shape) throws DrawableException {
    this.shape = shape;
  }

  /**
   * Origin of bounding box
   *
   * @return Coordinates of bounding box origin
   */
  public Point2D getLocation() {
    return getShape().getBounds().getLocation();
  }

  /**
   * X-coordinate of bounding box origin
   *
   * @return X-coordinate of the origin of the enclosing bounding box of the underlying {@link Shape}
   * @see Shape#getBounds()
   * @see Rectangle#getX()
   */
  public double getX() {
    return getShape().getBounds().getX();
  }

  /**
   * Adjust the current X-coordinate of bounding box origin
   *
   * @param x coordinate to use
   */
  public void setX(double x) {
    setLocation(x, getY());
  }

  /**
   * Y-coordinate of bounding box origin
   *
   * @return Y-coordinate of the origin of the enclosing bounding box of the underlying {@link Shape}
   * @see Shape#getBounds()
   * @see Rectangle#getY()
   */
  public double getY() {
    return getShape().getBounds().getY();
  }

  /**
   * Adjust Y-coordinate of origin of bounding box
   *
   * @param y coordinate
   */
  public void setY(double y) {
    setLocation(getX(), y);
  }

  /**
   * Width of bounding box
   *
   * @return Width of the enclosing bounding box of the underlying {@link Shape}
   * @see Shape#getBounds()
   * @see Rectangle#getWidth()
   */
  public double getWidth() {
    return getShape().getBounds().getWidth();
  }

  /**
   * <p>Adjust width of bounding box</p>
   *
   * @param width to use
   */
  public abstract void setWidth(double width);

  /**
   * Height of bounding box
   *
   * @return Height of the enclosing bounding box of the underlying {@link Shape}
   * @see Shape#getBounds()
   * @see Rectangle#getHeight()
   */
  public double getHeight() {
    return getShape().getBounds().getHeight();
  }

  /**
   * Adjust height of bounding box
   *
   * @param height to use
   */
  public abstract void setHeight(double height);

  /**
   * <p>Translate the shape location</p>
   *
   * @param dx Change in X-coordinates
   * @param dy Change in Y-coordinates
   */
  public abstract void translate(double dx, double dy);

  /**
   * <p>Translate the shape to a location</p>
   *
   * @param x coordinate of shape origin at new location
   * @param y coordinate of shape origin at new location
   */
  public abstract void setLocation(double x, double y);

  /**
   * Current stroke style
   *
   * @return Current {@link Stroke}
   */
  public Stroke getStroke() {
    return stroke;
  }

  /**
   * <p>Change the {@link Stroke}</p>
   *
   * <p>Refer to {@link BasicStroke} documentation for information on how to define a new stroke/</p>
   *
   * @param stroke description
   */
  public void setStroke(Stroke stroke) {
    this.stroke = stroke;
  }

  /**
   * Current stroke color
   *
   * @return Current stroke color
   */
  public Color getStrokeColor() {
    return strokeColor;
  }

  /**
   * <p>Adjust stroke color</p>
   *
   * <p><img src="doc-files/stroke-fill.png" alt="Stroke and fill diagram"></p>
   *
   * @param color of stroke
   */
  public void setStrokeColor(Color color) {
    this.strokeColor = color;
  }

  /**
   * Current fill color
   *
   * @return Current fill color
   */
  public Color getFillColor() {
    return fillColor;
  }

  /**
   * <p>Adjust fill color</p>
   *
   * <p><img src="doc-files/stroke-fill.png" alt="Stroke and fill diagram"></p>
   *
   * @param color of fill
   */
  public void setFillColor(Color color) {
    this.fillColor = color;
  }

  /**
   * <p>Drawing instructions for this component</p>
   *
   * <p>Required by {@link DrawingPanel#draw(Graphics2D)} to render the drawable component.</p>
   *
   * @param graphics context for drawing instructions
   */
  public void draw(Graphics2D graphics) {
    if (getFillColor() != TRANSPARENT) {
      graphics.setPaint(getFillColor());
      graphics.fill(getShape());
    }
    if (getStroke() != NO_STROKE && getStrokeColor() != TRANSPARENT) {
      graphics.setPaint(getStrokeColor());
      graphics.setStroke(getStroke());
      graphics.draw(getShape());
    }
  }

  /**
   * Enclosing bounding box of the underlying {@link Shape}
   *
   * @return Enclosing bounding box of the underlying {@link Shape}
   * @see Shape#getBounds()
   */
  public Rectangle2D getBounds() {
    return shape.getBounds2D();
  }

  /**
   * Tests if the specified coordinates are inside the boundary of the underlying {@link Shape}
   *
   * @param x coordinate of point
   * @param y coordinate of point
   * @return {@code true} if the coordinates are inside the boundary of the shape, {@code false} otherwise
   * @see Shape#contains(double, double)
   */
  public boolean contains(double x, double y) {
    return shape.contains(x, y);
  }

  /**
   * Tests if the specified point is inside the boundary of the underlying {@link Shape}
   *
   * @param point to test
   * @return {@code true} if the point is inside the boundary of the shape, {@code false} otherwise
   * @see Shape#contains(Point2D)
   */
  public boolean contains(Point2D point) {
    return shape.contains(point);
  }

  /**
   * Tests if the interior of the specified rectangle intersects the interior of the underlying {@link Shape}
   *
   * @param x      coordinate of top, left corner of the rectangle
   * @param y      coordinate of top, left corner of the rectangle
   * @param width  of the rectangle
   * @param height of the rectangle
   * @return {@code true} if the interior of the rectangle and the shape intersect, {@code false} otherwise
   * @see Shape#intersects(double, double, double, double)
   */
  public boolean intersects(double x, double y, double width, double height) {
    return shape.intersects(x, y, width, height);
  }

  /**
   * Tests if the interior of the specified rectangle intersects the interior of the underlying {@link Shape}
   *
   * @param rectangle to test
   * @return {@code true} if the interior of the rectangle and the shape intersect, {@code false} otherwise
   * @see Shape#intersects(Rectangle2D)
   */
  public boolean intersects(Rectangle2D rectangle) {
    return shape.intersects(rectangle);
  }


  /**
   * Tests if the interior of the underlying {@link Shape} entirely contains the interior of the specified rectangle
   *
   * @param x      coordinate of top, left corner of the rectangle
   * @param y      coordinate of the top, left corner of the rectangle
   * @param width  of the rectangle
   * @param height of the rectangle
   * @return {@code true} if the shape contains the rectangle, {@code false} otherwise
   * @see Shape#contains(double, double, double, double)
   */
  public boolean contains(double x, double y, double width, double height) {
    return shape.contains(x, y, width, height);
  }

  /**
   * Test if the interior of the underlying {@link Shape} entirely contains the interior of the specified rectangle
   *
   * @param rectangle to test
   * @return {@code true} if the shape contains the rectangle, {@code false} otherwise
   * @see Shape#contains(Rectangle2D)
   */
  public boolean contains(Rectangle2D rectangle) {
    return shape.contains(rectangle);
  }

  /**
   * <p>Provides access to the underlying geometry of the {@link Shape} outline</p>
   *
   * <p>If a {@code transformation} is provided, point coordinates are suitable transformed before being returned.</p>
   *
   * @param transformation to apply to shape before iterating
   * @return iterator over shape outline coordinates
   * @see Shape#getPathIterator(AffineTransform)
   */
  public PathIterator getPathIterator(AffineTransform transformation) {
    return shape.getPathIterator(transformation);
  }

  /**
   * Perform necessary cleanup before garbage collection
   */
  public void close() {
    if (drawingPanel != null) {
      drawingPanel.remove(this);
      drawingPanel = null;
    }
  }

  /**
   * Called automatically by garbage collector when no remaining references to the object are detected
   *
   * @see Object#finalize()
   */
  @Override
  public void finalize() {
    if (drawingPanel != null) {
      close();
    }
  }
}

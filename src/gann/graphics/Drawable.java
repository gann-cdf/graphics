package gann.graphics;

import gann.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.*;

public abstract class Drawable {
  public static final Color TRANSPARENT = new Color(0, true);
  public static final Stroke NO_STROKE = new BasicStroke(0);

  private DrawingPanel drawingPanel;
  private Shape shape;
  private Stroke stroke = new BasicStroke();
  private boolean filled = false;
  private Color strokeColor = Color.BLACK, fillColor = TRANSPARENT;

  public DrawingPanel getDrawingPanel() {
    return drawingPanel;
  }

  public void setDrawingPanel(DrawingPanel drawingPanel) throws DrawableException {
    if (this.drawingPanel != null) {
      this.drawingPanel.remove(this);
    }
    if (shape == null) {
      throw new DrawableException("Attempted to add a drawable object to drawingPanel before its shape was defined");
    }
    this.drawingPanel = drawingPanel;
    this.drawingPanel.add(this);
  }

  public void removeFromDrawingPanel() {
    getDrawingPanel().remove(this);
    this.drawingPanel = null;
  }

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
}

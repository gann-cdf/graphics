package org.gannacademy.cdf.graphics;

import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text extends Drawable {
  private double x, y;
  private String text;
  private Font font = new Font("Arial", Font.PLAIN, 20);

  public Text(String text, double x, double y, DrawingPanel drawingPanel) {
    try {
      this.text = text;
      this.x = x;
      this.y = y;
      setShape(new Rectangle2D.Double(0, 0, 0, 0));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  protected void rescaleShape() {
    ((Rectangle2D) getShape()).setFrame(getX(), getY() - getHeight() + getMaxDescent(), getWidth(), getHeight());
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
    rescaleShape();
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
    rescaleShape();
  }

  public String getText(){
    return text;
  }

  public void setText(String text) {
    this.text = text;
    rescaleShape();
  }

  public Font getFont() {
    return font;
  }

  public void setFont(Font font) {
    this.font = font;
    rescaleShape();
  }

  public double getWidth() {
    return getBounds().getWidth();
  }

  public double getHeight() {
    return getBounds().getHeight();
  }

  public Rectangle getBounds() {
    return getBounds(getDrawingPanel());
  }

  private Rectangle getBounds(DrawingPanel drawingPanel) {
    return drawingPanel.getGraphics().getFontMetrics(getFont())
            .getStringBounds(getText(), getDrawingPanel().getGraphics())
            .getBounds();
  }

  public double getMaxAscent() {
    return getDrawingPanel().getGraphics().getFontMetrics(getFont()).getMaxAscent();
  }

  public double getMaxDescent() {
    return getDrawingPanel().getGraphics().getFontMetrics(getFont()).getMaxDescent();
  }

  @Override
  public void translate(double dx, double dy) {
    setX(x + dx);
    setY(y + dy);
  }

  @Override
  public void setOrigin(double x, double y) {
    setX(x);
    setY(y);
  }

  @Override
  public void draw(Graphics2D graphics) {
    if (isFilled()) {
      graphics.setPaint(getFillColor());
      graphics.fill(getShape());
    }
    graphics.setPaint(getStrokeColor());
    graphics.setFont(getFont());
    graphics.drawString(getText(), (float) getX(), (float) getY());
  }
}

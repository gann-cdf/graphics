package org.gannacademy.cdf.graphics;

import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * <p>Draw text</p>
 *
 * <p><img src="doc-files/Text.png" alt="Diagram of Text parameters"></p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues" target="_blank">Seth Battis</a>
 */
public class Text extends Drawable {
  private double x, y;
  private String text;
  private Font font = new Font("Arial", Font.PLAIN, 20);

  /**
   * <p>Construct a new text object</p>
   *
   * <p><img src="doc-files/Text.png" alt="Diagram of Text parameters"></p>
   *
   * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
   * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
   *
   * <p><img src="doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
   *
   * @param text         to draw
   * @param x            coordinate of baseline origin
   * @param y            coordinate of baseline origin
   * @param drawingPanel on which to draw
   */
  public Text(String text, double x, double y, DrawingPanel drawingPanel) {
    setText(text);
    setX(x);
    setY(y);
    setDrawingPanel(drawingPanel);
  }

  @Override
  public Shape getShape() {
    return new Rectangle2D.Double(x, y - getMaxAscent(), getWidth(), getHeight());
  }

  @Override
  public double getX() {
    return x;
  }

  @Override
  public void setX(double x) {
    this.x = x;
  }

  @Override
  public double getY() {
    return y;
  }

  @Override
  public void setY(double y) {
    this.y = y;
  }

  @Override
  public double getWidth() {
    return getDrawingPanel().getFontMetrics(getFont()).getStringBounds(getText(), getDrawingPanel().getGraphics()).getWidth();
  }

  /**
   * Attempts to rescale the font size to match desired width
   *
   * @param width to use
   */
  @Override
  public void setWidth(double width) {
    double scale = width / getWidth();
    setFont(new Font(getFont().getFontName(), getFont().getStyle(), (int) (scale * getFont().getSize())));
  }

  @Override
  public double getHeight() {
    return getDrawingPanel().getFontMetrics(getFont()).getHeight();
  }

  /**
   * Attempts to rescale the font size to match desired height
   *
   * @param height to use
   */
  @Override
  public void setHeight(double height) {
    double scale = height / getHeight();
    setFont(new Font(getFont().getFontName(), getFont().getStyle(), (int) (scale * getFont().getSize())));
  }

  /**
   * Current text
   *
   * @return Current text
   */
  public String getText() {
    return text;
  }

  /**
   * Replace text
   *
   * @param text to use
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Current {@link Font}
   *
   * @return Current {@link Font}
   */
  public Font getFont() {
    return font;
  }

  /**
   * Replace current font
   *
   * @param font to use
   */
  public void setFont(Font font) {
    this.font = font;
  }

  public Rectangle2D getBounds() {
    return getBounds(getDrawingPanel());
  }

  private Rectangle2D getBounds(DrawingPanel drawingPanel) {
    return drawingPanel.getGraphics().getFontMetrics(getFont())
            .getStringBounds(getText(), getDrawingPanel().getGraphics())
            .getBounds2D();
  }

  /**
   * Height of character ascent in current font
   *
   * @return Height of maximum ascent in current font
   */
  public double getMaxAscent() {
    return getDrawingPanel().getGraphics().getFontMetrics(getFont()).getMaxAscent();
  }


  /**
   * Height of character descent in current font
   *
   * @return height of maximum descent in current font
   */
  public double getMaxDescent() {
    return getDrawingPanel().getGraphics().getFontMetrics(getFont()).getMaxDescent();
  }

  @Override
  public void translate(double dx, double dy) {
    setX(x + dx);
    setY(y + dy);
  }

  @Override
  public void setLocation(double x, double y) {
    setX(x);
    setY(y);
  }

  @Override
  public void draw(Graphics2D graphics) {
    if (getFillColor() != TRANSPARENT) {
      graphics.setPaint(getFillColor());
      graphics.fill(getShape());
    }
    graphics.setPaint(getStrokeColor());
    graphics.setFont(getFont());
    graphics.drawString(getText(), (float) getX(), (float) getY());
  }
}

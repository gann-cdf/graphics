package org.gannacademy.cdf.graphics;

import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image extends Drawable {
  private BufferedImage original, active;
  private String path;
  private double x, y, width, height;

  public Image(String path, double x, double y, DrawingPanel drawingPanel) {
    try {
      setX(x);
      setY(y);
      setShape(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
      setImage(path);
      setWidth(getImage().getWidth());
      setHeight(getImage().getHeight());
      setDrawingPanel(drawingPanel);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage() {
    return original;
  }

  public String getPath() {
    return path;
  }

  protected void rescaleImage() {
    if (getWidth() > 0 && getHeight() > 0) {
      ((Rectangle2D) getShape()).setFrame(getX(), getY(), getWidth(), getHeight());
      active = new BufferedImage((int) getWidth(), (int) getHeight(), original.getType());
      Graphics2D graphics = active.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      graphics.drawImage(original, 0, 0, (int) getWidth(), (int) getHeight(), null);
      graphics.dispose();
    }
  }

  public void setImage(String path) throws IOException {
    this.path = path;
    setImage(ImageIO.read(getClass().getResource(path)));
  }

  public void setImage(BufferedImage image) {
    this.original = image;
    if (getWidth() == 0 && getHeight() == 0) {
      setWidth(image.getWidth());
      setHeight(image.getHeight());
      active = original;
    } else {
      rescaleImage();
    }
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
    if (getWidth() != 0 && getHeight() != 0) {
      ((Rectangle2D) getShape()).setFrame(getX(), getY(), getWidth(), getHeight());
    }
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
    if (getWidth() != 0 && getHeight() != 0) {
      ((Rectangle2D) getShape()).setFrame(getX(), getY(), getWidth(), getHeight());
    }
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
    rescaleImage();
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
    rescaleImage();
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
  public void draw(Graphics2D graphics2D) {
    if (isFilled()) {
      graphics2D.setPaint(getFillColor());
      graphics2D.fill(getShape());
    }
    graphics2D.drawImage(active, (int) getX(), (int) getY(), null);
    if (getStroke() != NO_STROKE) {
      graphics2D.setStroke(getStroke());
      graphics2D.setPaint(getStrokeColor());
      graphics2D.draw(getShape());
    }
  }
}

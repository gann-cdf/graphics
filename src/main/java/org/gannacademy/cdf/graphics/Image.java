package org.gannacademy.cdf.graphics;

import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Render an image file
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Image extends Drawable {
  private BufferedImage original, active;
  private String path;
  private double x, y, width, height;

  /**
   * <p>Construct a new image object</p>
   *
   * <p><img src="doc-files/Image.png" alt="Diagram of Image parameters"></p>
   *
   * <p>Initially, width and height of a new image object match the dimensions of the original image.</p>
   *
   * <p>Note that image files (as should all files used by your app internally) should be stored in a resources directory in your project. Maven creates this directory by default, but IDEs allow you to mark a subfolder of your project as the resources root. The path to the image file is relative to the root of the resources directory.</p>
   *
   * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
   * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
   *
   * <p><img src="doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
   *
   * @param path         to the image resource
   * @param x            coordinate of image origin
   * @param y            coordinate of image origin
   * @param drawingPanel on which to draw
   */
  public Image(String path, double x, double y, DrawingPanel drawingPanel) {
    try {
      setImage(path);
      setX(x);
      setY(y);
      setDrawingPanel(drawingPanel);
    } catch (IOException e) {
        System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Underlying image data
   *
   * @return Underlying {@link BufferedImage} data
   */
  public BufferedImage getImage() {
    return original;
  }

  /**
   * <p>Replace the underlying {@link BufferedImage} data</p>
   *
   * <p>Replacing the underlying image data leaves other characteristics (fill, stroke, position) unchanged.</p>
   *
   * @param path to image resource
   * @throws IOException if the image resource cannot be accessed
   */
  public void setImage(String path) throws IOException {
    this.path = path;
    setImage(ImageIO.read(getClass().getResource(path)));
  }

  /**
   * <p>Replace the underlying {@link BufferedImage} data</p>
   *
   * <p>Replacing the underlying image data leaves other characteristics (fill, stroke, position) unchanged.</p>
   *
   * @param image data
   */
  public void setImage(BufferedImage image) {
    original = image;

    // only set width and height to match image if loading initial image
    if (active == null && original != null) {
      setWidth(original.getWidth());
      setHeight(original.getHeight());
      active = original;
    } else {
      rescaleImage();
    }
  }

  /**
   * Path to image resource
   *
   * @return Path to image resource
   */
  public String getPath() {
    return path;
  }

  /**
   * Rescale the underlying image data to match current width and height field values
   */
  protected void rescaleImage() {
    if (getWidth() > 0 && getHeight() > 0) {
      active = new BufferedImage((int) getWidth(), (int) getHeight(), original.getType());
      Graphics2D graphics = active.createGraphics();
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      graphics.drawImage(original, 0, 0, (int) getWidth(), (int) getHeight(), null);
      graphics.dispose();
    }
  }

  @Override
  public Shape getShape() {
    return new Rectangle2D.Double(x, y, width, height);
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
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
    rescaleImage();
  }

  @Override
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
  public void setLocation(double x, double y) {
    setX(x);
    setY(y);
  }

  @Override
  public void draw(Graphics2D graphics2D) {
    if (getFillColor() != TRANSPARENT) {
      graphics2D.setPaint(getFillColor());
      graphics2D.fill(getShape());
    }
    graphics2D.drawImage(active, (int) x, (int) y, null);
    if (getStroke() != NO_STROKE && getStrokeColor() != TRANSPARENT) {
      graphics2D.setStroke(getStroke());
      graphics2D.setPaint(getStrokeColor());
      graphics2D.draw(getShape());
    }
  }
}

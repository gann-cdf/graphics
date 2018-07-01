package org.gannacademy.cdf.graphics.ui;

import org.gannacademy.cdf.graphics.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * <p>A drawing panel receives and displays all drawing instructions</p>
 *
 * <p>In the default configuration (in which {@link AppWindow} is extended to construct a drawing), the drawing panel is
 * contained by an {@code AppWindow} object &mdash; an {@code AppWindow} has-a {@code DrawingPanel}, in the parlance of
 * the Advanced Placement exam, and a {@code DrawingPanel} has-many {@code Drawable} components.</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class DrawingPanel extends JPanel {

  /**
   * {@value #DEFAULT_WIDTH} pixels
   */
  public static final int DEFAULT_WIDTH = 600;

  /**
   * {@value #DEFAULT_HEIGHT} pixels
   */
  public static final int DEFAULT_HEIGHT = 400;

  /**
   * {@link Color#WHITE}
   */
  public static final Color DEFAULT_BACKGROUND = Color.WHITE;

  /**
   * {@value #DEFAULT_IMAGE_FORMAT}
   */
  public static final String DEFAULT_IMAGE_FORMAT = "PNG";

  /*
   * TODO Not 100% confident that the stack is the right structure here -- it would be nice to let someone reorder the
   * components to effectively set their Z-depth (which could technically be done with a stack, but is not really in the
   * spirit of it all)
   */
  private Stack<Drawable> components;

  /**
   * Construct a drawing panel of default dimensions and background color
   */
  public DrawingPanel() {
    this(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), DEFAULT_BACKGROUND);
  }

  /**
   * <p>Construct a drawing panel with custom arguments</p>
   *
   * <p>Of potential interest: while setting a background color that is transparent reveals the light gray color of of
   * the enclosing {@link JFrame} underneath the drawing panel, it will save as a transparent PNG usinig
   * {@link #saveAs(String)}.</p>
   *
   * @param dimension  in pixels
   * @param background color
   */
  public DrawingPanel(Dimension dimension, Color background) {
    super();
    setPreferredSize(dimension);
    setBackground(background);
    components = new Stack<Drawable>();
  }

  /**
   * <p>Add a drawable component to the drawing panel</p>
   *
   * <p>While available for use, this method does not usually need to be called manually -- it is automatically called
   * by all {@link Drawable} component constructors. The {@link Drawable#setDrawingPanel(DrawingPanel)} method is
   * meant to be used to move a {@code Drawable} component from one drawing panel to another transparently.</p>
   *
   * @param component to be added
   */
  public synchronized void add(Drawable component) {
    if (!components.contains(component)) {
      components.push(component);
    }
  }

  /**
   * <p>Remove a drawable component from the drawing panel</p>
   *
   * <p>While available for use, this method dos not usually need to be called manually -- it is automatically called
   * by {@link Drawable#removeFromDrawingPanel()} and, if necessary, by
   * {@link Drawable#setDrawingPanel(DrawingPanel)}.</p>
   *
   * @param component to be removed
   * @return {@code true} if the component was present and removed, {@code false} otherwise
   */
  public synchronized boolean remove(Drawable component) {
    return components.remove(component);
  }

  /**
   * <p>Clear all drawable components from the drawing panel</p>
   *
   * <p>This does not destroy the drawable components &mdash; any references to these components outside of the drawing
   * panel will still be valid, but the components will no longer refer to this drawing panel.</p>
   */
  public void clear() {
    while (!components.isEmpty()) {
      components.peek().removeFromDrawingPanel();
    }
  }

  /**
   * <p>Repaint the contents of the drawing panel (drawable components) as-needed</p>
   *
   * <p>This method is called automatically by the enclosing {@link JFrame} to repaint the drawing panel as needed. It
   * is not meant to be called manually. If the drawing panel needs to be updated, a {@link #repaint()} request will
   * schedule the update.</p>
   *
   * <p>This method calls the {@link #draw(Graphics2D)} method to perform the actual drawing instructions</p>
   *
   * @param graphics context for drawing instructions
   * @see #draw(Graphics2D)
   */
  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    draw((Graphics2D) graphics);
  }

  /**
   * <p>Request drawing instructions from all contained drawing components</p>
   *
   * <p>This method encapsulates all of the drawing instructions necessary to display the current drawable components.
   * It is used by both the {@link #paintComponent(Graphics)} method to update the display and the {@link #saveAs(String)}
   * method to write the current drawable components to a file.</p>
   *
   * <p>This method calls the {@link #preDraw(Graphics2D)} method prior to making drawing instructions to
   * set any rendering hints or other configuration for the drawing.</p>
   *
   * @param graphics context for drawing instructions
   * @see #preDraw(Graphics2D)
   * @see #paintComponent(Graphics)
   * @see #saveAs(String, String)
   */
  protected synchronized void draw(Graphics2D graphics) {
    Graphics2D graphics2D = graphics;
    preDraw(graphics2D);
    for (Drawable component : components) {
      component.draw(graphics2D);
    }
  }

  /**
   * <p>Override this method to set custom graphics configurations</p>
   * <p>This method is called by {@link #draw(Graphics2D)} to configure any desired rendering hints or other
   * preconfiguration before executing the actual drawing instructions.</p>
   *
   * @param graphics context for drawing commands
   */
  public void preDraw(Graphics2D graphics) {
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }

  /**
   * Save the current drawing panel as a file using the default format
   *
   * @param path relative to the current working directory
   * @return {@code true} if the file was successfully written, {@code false} otherwise
   */
  public boolean saveAs(String path) {
    return saveAs(path, DEFAULT_IMAGE_FORMAT);
  }

  /**
   * Save the current drawing panel as a file
   *
   * @param path   relative to the current working directory
   * @param format of the image file (e.g. {@value #DEFAULT_IMAGE_FORMAT}
   * @return {@code true} if the file was successfully written, {@code false} otherwise
   */
  public boolean saveAs(String path, String format) {
    try {
      BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D context = image.createGraphics();
      context.setPaint(getBackground());
      context.fillRect(0, 0, image.getWidth(), image.getHeight());
      draw(context);
      ImageIO.write(image, format, new File(path));
    } catch (IOException e) {
      System.err.println("There was an error trying to create the DrawingPanel image file");
      e.printStackTrace();
      return false;
    }
    return true;
  }
}

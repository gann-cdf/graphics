package gann.graphics.ui;

import gann.graphics.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {

  private List<Drawable> components;

  public DrawingPanel() {
    this(new Dimension(600, 400), Color.WHITE);
  }

  public DrawingPanel(Dimension dimension, Color background) {
    super();
    setPreferredSize(dimension);
    setBackground(background);
    components = new ArrayList<>();
  }

  public boolean add(Drawable component) {
    if (!components.contains(component)) {
      return components.add(component);
    }
    return true;
  }

  public boolean remove(Drawable component) {
    return components.remove(component);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphicsPreDrawing(graphics2D);
    for (Drawable component : components) {
      component.draw(graphics2D);
    }
  }

  public void graphicsPreDrawing(Graphics2D graphics) {
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  }
}

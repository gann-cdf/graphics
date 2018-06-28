package gann.graphics.ui;

import javax.swing.*;
import java.awt.*;

public abstract class AppWindow implements Runnable {
  public static final String DEFAULT_TITLE = "Gann Graphics App";

  private JFrame frame;
  private DrawingPanel drawingPanel;

  public AppWindow() {
    this(DEFAULT_TITLE);
  }

  public AppWindow(String title) {
    frame = new JFrame(title);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    drawingPanel =  new DrawingPanel();
    frame.add(drawingPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    setup();
    frame.setVisible(true);
    run();
  }

  protected void setSize(int width, int height) {
    drawingPanel.setPreferredSize(new Dimension(width, height));
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.repaint();
  }

  protected void sleep(long milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    while (!done()) {
      loop();
      drawingPanel.repaint();
    }
  }

  protected abstract void setup();

  protected void loop() {}

  protected boolean done() {
    return false;
  }

  public DrawingPanel getDrawingPanel() {
    return drawingPanel;
  }
}

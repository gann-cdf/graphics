package org.gannacademy.cdf.graphics.curriculum.projectstems.paint;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class PaintApp extends AppWindow implements MouseListener, MouseMotionListener {
  private Rectangle rectButton;
  private Tool active;
  private Point2D start;
  private Drawable current;

  public static void main(String[] args) {
    new PaintApp();
  }

  @Override
  protected void setup() {
    rectButton = new Rectangle(5, 5, 15, 15, getDrawingPanel());
    active = Tool.none;
    getDrawingPanel().addMouseListener(this);
    getDrawingPanel().addMouseMotionListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (rectButton.contains(e.getPoint())) {
      active = Tool.Rect;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    switch (active) {
      case Rect:
        if (!rectButton.contains(e.getPoint())) {
          start = e.getPoint();
          current = new Rectangle(e.getX(), e.getY(), 1, 1, getDrawingPanel());
        }
      case none:
        // do nothing
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    current = null;
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (active != Tool.none) {
      current.setWidth(Math.abs(e.getX() - start.getX()));
      current.setHeight(Math.abs(e.getY() - start.getY()));
      if (e.getX() < start.getX()) {
        current.setX(e.getX());
      }
      if (e.getY() < start.getY()) {
        current.setY(e.getY());
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }

  private enum Tool {Rect, none}
}

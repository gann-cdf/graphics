package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

/**
 * <p>Draw a cubic Bézier curve</p>
 *
 * <p>Bezier curves are computed using a deceptively simple interpolation technique. A linear Bézier curve is computed by
 * plotting the points between Point 1 and Point 2 &mdash; resulting in a straight line:</p>
 *
 * <p><img src="doc-files/Line.png" alt="Linear Bézier curve"></p>
 *
 * <p>A quadratic Bézier curve uses a control point in addition to Points 1 and 2. The points on the curve are plotted
 * by drawing a line from a point on the line from Point 1 to the control point and connecting to a point on the line
 * connecting the control point to point 2. This is done in a fractional progression: we connect the point, say, 20% of
 * the way between Point 1 and the control point to a point 20% of the way from the control point to Point 2, and find
 * the point on the curve 20% of the way along our interpolated line.</p>
 *
 * <table>
 * <tr>
 * <td><img src="doc-files/Bezier-QuadCurve-fig2.png" alt="Quadratic Bézier curve interpolation"></td>
 * <td><img src="doc-files/Bezier-QuadCurve-fig3.png" alt="Quadratic Bézier curve interpolated"></td>
 * <td><img src="doc-files/Bezier-QuadCurve-fig4.png" alt="Quadratic Bézier curve"></td>
 * </tr>
 * <caption>Quadratic Bézier curve</caption>
 * </table>
 *
 * <p>We can extend this process to higher dimensions by using an increasing number of intermediate control points
 * between Points 1 and 2, and increasing the levels of interpolation between those points. A cubic Bézier curve has
 * two control points.</p>
 *
 * <p><img src="doc-files/Bezier-CubicCurve-fig1.png" alt="Cubic Bézier curve with control points"></p>
 *
 * <p>We have three imaginary lines: from Point 1 to the first control point, from the first control point to the
 * second control point, and from the second control point to Point 2. We interpolate lines connecting these lines in
 * the same manner as a quadratic curve.</p>
 *
 * <p><img src="doc-files/Bezier-CubicCurve-fig2.png" alt="First order interpolation of cubic Bézier curve"></p>
 *
 * <p>We now interpolate our interpolations and choose points on these second order interpolations as the points of our
 * curve. To make this somewhat clearer, we start by reducing the number of interpolations for somewhat better clarity.</p>
 *
 * <table>
 * <tr>
 * <td><img src="doc-files/Bezier-CubicCurve-fig3.png" alt="Second order interpolation of cubic Bézier curve"></td>
 * <td><img src="doc-files/Bezier-CubicCurve-fig4.png" alt="Second order interpolation of cubic Bézier curve (increased detail)"></td>
 * <td><img src="doc-files/Bezier-CubicCurve-fig5.png" alt="Cubic Bézier curve"></td>
 * </tr>
 * <caption>Cubic Bézier curve</caption>
 * </table>
 *
 * <p>Refer to the <a href="https://en.wikipedia.org/wiki/B%C3%A9zier_curve">Wikipedia Bézier curve article</a> for some
 * lovely animated GIFs of this process.</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class CubicCurve extends Drawable {
  /**
   * <p>Construct a new cubic curve.</p>
   *
   * <p>A cubic curve is a curve that is shaped like a cubic function (i.e. <i>f(x)</i>&nbsp;=&nbsp;<i>x</i><sup>3</sup>).
   * The curve is defined by two end points and two control points. The curve is drawn by finding the curve between
   * the two end points that passes closest to the control points, so adjusting their positions will change the shape of
   * the curve.</p>
   *
   * <p><img src="doc-files/CubicCurve.png" alt="Diagram of CubicCurve parameters"></p>
   *
   * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
   * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
   *
   * <p><img src="../doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
   *
   * @param x1           X-coordinate of starting point
   * @param y1           Y-coordinate of staring point
   * @param ctrlX1       X-coordinate of Control Point 1
   * @param ctrlY1       Y-coordinate of Control Point 1
   * @param ctrlX2       X-coordinate of Control Point 2
   * @param ctrlY2       Y-coordinate of Control Point 2
   * @param x2           X-coordinate of end point
   * @param y2           Y-coordinate of end point
   * @param drawingPanel on which to draw
   */
  public CubicCurve(double x1, double y1, double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, double x2, double y2, DrawingPanel drawingPanel) {
    try {
      setShape(new CubicCurve2D.Double(x1, y1, ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
        System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * @return Underlying {@link CubicCurve2D} geometry
   */
  protected CubicCurve2D getShapeAsCubicCurve() {
    return (CubicCurve2D) getShape();
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof CubicCurve2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set CubicCurve's underlying shape to a non-CubicCurve2D instance");
    }
  }

  @Override
  public void setWidth(double width) {
    // FIXME this feels hacktacular
    double scale = width / getWidth();
    setCurve(
            getX() + (getX1() - getX()) * scale, getY1(),
            getX() + (getCtrlX1() - getX()) * scale, getCtrlY1(),
            getX() + (getCtrlX2() - getX()) * scale, getCtrlY2(),
            getX() + (getX2() - getX()) * scale, getY2()
    );
  }

  @Override
  public void setHeight(double height) {
    // FIXME this feels hacktackular
    double scale = height / getHeight();
    setCurve(
            getX1(), getY() + (getY1() - getY()) * scale,
            getCtrlX1(), getY() + (getCtrlY1() - getY()) * scale,
            getCtrlX2(), getY() + (getCtrlY2() - getY()) * scale,
            getX2(), getY() + (getY2() - getY()) * scale
    );
  }

  /**
   * Starting point
   *
   * @return Coordinates of starting point
   * @see CubicCurve2D#getP1()
   */
  public Point2D getP1() {
    return getShapeAsCubicCurve().getP1();
  }

  /**
   * Ending point
   *
   * @return Coordinates of ending point
   * @see CubicCurve2D#getP2()
   */
  public Point2D getP2() {
    return getShapeAsCubicCurve().getP2();
  }

  /**
   * <p>Set the points describing the curve</p>
   *
   * <p>This will leave other characteristics (e.g. fill color or stroke) unchanged</p>
   *
   * <p><img src="doc-files/CubicCurve.png" alt="Diagram of setCurve() Parameters"></p>
   *
   * @param x1     X-coordinate of starting point
   * @param y1     Y-coordinate of starting point
   * @param ctrlX1 X-coordinate of first control point
   * @param ctrlY1 Y-coordinate of first control point
   * @param ctrlX2 X-coordinate of second control point
   * @param ctrlY2 Y-coordinate of second control point
   * @param x2     X-coordinate of ending point
   * @param y2     Y-coordinate of ending point
   */
  public void setCurve(double x1, double y1, double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, double x2, double y2) {
    getShapeAsCubicCurve().setCurve(x1, y1, ctrlX1, ctrlY1, ctrlX2, ctrlY2, x2, y2);
  }

  /**
   * X-coordinate of starting point
   *
   * @return X-coordinate of starting point
   * @see CubicCurve2D#getX1()
   */
  public double getX1() {
    return getShapeAsCubicCurve().getX1();
  }

  /**
   * Y-coordinate of starting point
   *
   * @return Y-coordinate of starting point
   * @see CubicCurve2D#getY1()
   */
  public double getY1() {
    return getShapeAsCubicCurve().getY1();
  }

  /**
   * X-coordinate of first control point
   *
   * @return X-coordinate of first control point
   * @see CubicCurve2D#getCtrlX1()
   */
  public double getCtrlX1() {
    return getShapeAsCubicCurve().getCtrlX1();
  }

  /**
   * Y-coordinate of first control point
   *
   * @return Y-coordinate of first control point
   * @see CubicCurve2D#getCtrlY1()
   */
  public double getCtrlY1() {
    return getShapeAsCubicCurve().getCtrlY1();
  }

  /**
   * First control point
   *
   * @return First control point
   * @see CubicCurve2D#getCtrlP1()
   */
  public Point2D getCtrlP1() {
    return getShapeAsCubicCurve().getCtrlP1();
  }

  /**
   * X-coordinate of second control point
   *
   * @return X-coordinate of first control point
   * @see CubicCurve2D#getCtrlX2()
   */
  public double getCtrlX2() {
    return getShapeAsCubicCurve().getCtrlX2();
  }

  /**
   * Y-coordinate of second control point
   *
   * @return Y-coordinate of second control point
   * @see CubicCurve2D#getCtrlY2()
   */
  public double getCtrlY2() {
    return getShapeAsCubicCurve().getCtrlY2();
  }

  /**
   * Ending point
   *
   * @return Coordinates of ending point
   * @see CubicCurve2D#getP2()
   */
  public Point2D getCtrlP2() {
    return getShapeAsCubicCurve().getCtrlP2();
  }

  /**
   * X-coordinate of ending point
   *
   * @return X-coordinate of ending point
   * @see CubicCurve2D#getX2()
   */
  public double getX2() {
    return getShapeAsCubicCurve().getX2();
  }

  /**
   * Y-coordinate of ending point
   *
   * @return Y-coordinate of ending point
   * @see CubicCurve2D#getY2()
   */
  public double getY2() {
    return getShapeAsCubicCurve().getY2();
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsCubicCurve().setCurve(
            getX1() + dx, getY1() + dy,
            getCtrlX1() + dx, getCtrlY1() + dy,
            getCtrlX2() + dx, getCtrlY2() + dy,
            getX2() + dx, getY2() + dy
    );
  }

  @Override
  public void setLocation(double x, double y) {
    translate(x - getX(), y - getY());
  }
}

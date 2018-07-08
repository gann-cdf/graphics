package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * <p>Draw an arbitrary path</p>
 *
 * <p><img src="doc-files/Path.png" alt="Path diagram"></p>
 *
 * <p>Paths are constructed from an arbitrary number of segments. Each segment is a {@link Line}, {@link QuadCurve}, or
 * {@link CubicCurve} drawn between the end point of the prior segment and new point. For example, in the diagram above,
 * the path is made of three segments:</p>
 *
 * <ol>
 * <li>A line from Point 1 to Point 2</li>
 * <li>A cubic curve from Point 2 to Point 3</li>
 * <li>A quadratic curve from Point 3 to Point 4</li>
 * </ol>
 *
 * <p>The path above could be drawn with the following sequence of instructions:</p>
 *
 * <pre>
 *   Path p = new Path();
 *   p.moveTo(20, 16); // Point 1
 *   p.lineTo(40, 140); // Point 2
 *   p.curveTo(
 *       100, 140, // first Bézier control point
 *       120, 127, // second Bézier control point
 *       120, 78 // Point 3
 *   );
 *   p.quadTo(
 *       120, 16; // quadratic control point
 *       220, 78 // Point 4
 *   );
 * </pre>
 *
 * <p>All paths start with empty geometry and are then built segment by segment using {@link #moveTo(double, double)},
 * {@link #lineTo(double, double)}, {@link #quadTo(double, double, double, double)}, and
 * {@link #curveTo(double, double, double, double, double, double)} methods to extend the existing geometry.</p>
 *
 * <p>Note that the first segment added to the path must be a {@link #moveTo(double, double)} instruction, to locate
 * the first point in the path. Additional {@link #moveTo(double, double)} calls may be made as the path is defined,
 * creating a discontinuous path.</p>
 *
 * <p>Paths are particularly complex (and therefore flexible and powerful!). As the underlying geometry of this object
 * is stored as a {@link Path2D}, it is worth perusing that documentation for information on details like approaches to
 * filling, stroking, or transforming paths. More detailed explanations of how the Bézier curve segments are computed
 * can be found in {@link QuadCurve} and {@link CubicCurve}.</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public class Path extends Drawable {
  /**
   * <p>Construct a path with empty geometry</p>
   *
   * @param drawingPanel on which to draw
   */
  public Path(DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double());
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>Construct a path with empty geometry and a winding rule</p>
   *
   * @param windingRule  The <a href="https://en.wikipedia.org/wiki/Nonzero-rule#/media/File:Even-odd_and_non-zero_winding_fill_rules.png">
   *                     winding rule</a> to determine how to fill the shape
   * @param drawingPanel on which to draw
   */
  public Path(int windingRule, DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double(windingRule));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>Construct a path with empty geometry, a winding rule and expected number of segments</p>
   *
   * <p>The path will expand to contain as many segments as are added to it, but setting the initial capacity to your best
   * guess gains some small amount of efficiency in reducing resizing operations.</p>
   *
   * @param windingRule     The <a href="https://en.wikipedia.org/wiki/Nonzero-rule#/media/File:Even-odd_and_non-zero_winding_fill_rules.png">
   *                        winding rule</a> to determine how to fill the shape
   * @param initialCapacity Anticipated number of segments
   * @param drawingPanel    on which to draw
   */
  public Path(int windingRule, int initialCapacity, DrawingPanel drawingPanel) {
    try {
      setShape(new Path2D.Double(windingRule, initialCapacity));
      setDrawingPanel(drawingPanel);
    } catch (DrawableException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>Construct a path from {@link Shape} geometry and a transformation</p>
   *
   * @param shape          of underlying geometry
   * @param transformation to apply {@code shape} (i.e. scale, translation, rotation)
   * @param drawingPanel   on which to draw
   * @throws DrawableException If {@code shape} cannot be converted to a {@link Path2D} (a highly unlikely eventuality)
   */
  public Path(Shape shape, AffineTransform transformation, DrawingPanel drawingPanel) throws DrawableException {
    setShape(new Path2D.Double(shape, transformation));
    setDrawingPanel(drawingPanel);
  }

  /**
   * @return Underlying {@link Path2D} geometry
   */
  protected Path2D getShapeAsPath() {
    return (Path2D) getShape();
  }

  @Override
  public void setShape(Shape shape) throws DrawableException {
    if (shape instanceof Path2D) {
      super.setShape(shape);
    } else {
      throw new DrawableException("Attempt to set Path's underlying shape to a non-Path2D instance");
    }
  }

  @Override
  public void setWidth(double width) {
    // FIXME transform order is backwards, so translation is also getting scaled
    transform(AffineTransform.getScaleInstance(width / getWidth(), 1));
  }

  @Override
  public void setHeight(double height) {
    // FIXME transform order is backwards, so translation is also getting scaled
    transform(AffineTransform.getScaleInstance(1, height / getHeight()));
  }

  /**
   * <p>Add a cubic curve segment to the path</p>
   *
   * <p><img src="doc-files/CubicCurve.png" alt="Cubic Curve diagram"></p>
   *
   * <p>The cubic Bézier curve starts at the current end point of the path and extends through two control points. For
   * more details on how cubic Bézier curves are computes, refer to {@link CubicCurve}.</p>
   *
   * @param ctrlX1 X-coordinate of first control point
   * @param ctrlY1 Y-coordinate of first control point
   * @param ctrlX2 X-coordinate of second control point
   * @param ctrlY2 Y-coordinate of second control point
   * @param x3     X-coordinate of end point
   * @param y3     Y-coordinate of end point
   */
  public void curveTo(double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, double x3, double y3) {
    getShapeAsPath().curveTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, x3, y3);
  }

  /**
   * <p>Add a line segment to the path</p>
   *
   * <p><img src="doc-files/Line.png" alt="Line diagram"></p>
   *
   * <p>The line starts at the current end point of the path. For more details on drawing lines, refer to {@link Line}.</p>
   *
   * @param x coordinate of end point
   * @param y coordinate of end point
   */
  public void lineTo(double x, double y) {
    getShapeAsPath().lineTo(x, y);
  }

  @Override
  public void translate(double dx, double dy) {
    getShapeAsPath().transform(AffineTransform.getTranslateInstance(dx, dy));
  }

  @Override
  public void setLocation(double x, double y) {
    translate(x - getX(), y - getY());
  }

  /**
   * <p>Select a new starting point for subsequent path segments</p>
   *
   * <p>Path segments are defined relative to the end point of the previous segment. {@code moveTo()}
   * must be the first instruction to the path to set a starting point for following segments. This method can also
   * be used to define a discontinuous path.</p>
   *
   * @param x coordinate
   * @param y coordinate
   */
  public void moveTo(double x, double y) {
    getShapeAsPath().moveTo(x, y);
  }


  /**
   * <p>Add a quadratic Bézier curve segment to the path</p>
   *
   * <p><img src="doc-files/QuadCurve.png" alt="Quad Curve diagram"></p>
   *
   * <p>The quadratic Bézier curve segments starts at the end point of the previous path segment, through a control
   * point to the end point. For more information on computing quadratic Bézier curves, refer to {@link QuadCurve}.</p>
   *
   * @param ctrlX1 X-coordinate of control point
   * @param ctrlY1 Y-coordinate of control point
   * @param x2     X-coordinate of end point
   * @param y2     Y-coordinate of end point
   */
  public void quadTo(double ctrlX1, double ctrlY1, double x2, double y2) {
    getShapeAsPath().quadTo(ctrlX1, ctrlY1, x2, y2);
  }

  /**
   * <p>Transform the path</p>
   *
   * <p>An "affine transformation" is one in which the spatial relationships of the points of the path are not changed
   * relative to each other &mdash; scale, translation, and rotation. Refer to {@link AffineTransform} for more
   * information.</p>
   *
   * @param transformation to be applied to the path
   */
  public void transform(AffineTransform transformation) {
    getShapeAsPath().transform(transformation);
  }
}

package org.gannacademy.cdf.graphics;

import org.gannacademy.cdf.graphics.geom.Path;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.awt.geom.RectangularShape;

/**
 * <p>Superclass of two-dimensional ("rectangular") drawable components</p>
 *
 * <p>What makes a component "rectangular"? It's defined by an origin, width and height &mdash; like a
 * {@link org.gannacademy.cdf.graphics.geom.Rectangle} or an {@link org.gannacademy.cdf.graphics.geom.Ellipse}.</p>
 *
 * @author <a href="http://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
abstract public class Drawable2D extends Drawable {
    private RectangularShape shape;

    @Override
    public Shape getShape() {
        return shape;
    }

    /**
     * <p>Replace the underlying {@link Shape} geometry of the component</p>
     *
     * <p>Replacing the {@link Shape} geometry leaves other characteristics (fill, stroke) untouched.</p>
     *
     * @param shape of geometry
     * @throws DrawableException if {@code shape} is incompatible with the class
     */
    @Override
    public void setShape(Shape shape) throws DrawableException {
        if (shape instanceof RectangularShape) {
            this.shape = (RectangularShape) shape;
        } else {
            throw new DrawableException("Cannot convert a Shape to a RectangularShape");
        }
    }

    /**
     * Underlying {@link RectangularShape} geometry
     *
     * @return Underlying {@link RectangularShape} geometry
     */
    protected RectangularShape getShapeAsRectangularShape() {
        return shape;
    }

    @Override
    public void setWidth(double width) {
        getShapeAsRectangularShape().setFrame(getX(), getY(), width, getHeight());
    }

    @Override
    public void setHeight(double height) {
        getShapeAsRectangularShape().setFrame(getX(), getY(), getWidth(), height);
    }

    @Override
    public void translate(double dx, double dy) {
        getShapeAsRectangularShape().setFrame(getX() + dx, getY() + dy, getWidth(), getHeight());
    }

    @Override
    public void setLocation(double x, double y) {
        translate(x - getX(), y - getY());
    }

    /**
     * Test if shape geometry is defined
     *
     * @return {@code true} if the shape does not contain geometry, {@code false} otherwise
     */
    public boolean isEmpty() {
        return shape.isEmpty();
    }

    /**
     * <p>Adjust the frame of the shape's enclosing bounding box</p>
     *
     * <p>Adjusting the frame of the shape resizes the geometry to fit within the new bounds</p>
     *
     * @param x      coordinate of the origin of the new frame
     * @param y      coordinate of the origin of the new frame
     * @param width  of the new frame
     * @param height of the new frame
     */
    public void setFrame(double x, double y, double width, double height) {
        shape.setFrame(x, y, width, height);
    }

    /**
     * <p>Convert a {@code Drawable2D} shape into its equivalent {@code Path}</p>
     *
     * <p>{@code Drawable2D} shapes are aligned to the rectangular grid of the cartesian coordinate system: a rectangle
     * always has sides aligned with the X- and Y-axes of the coordinate system. This means that transformations other
     * than translation and scale cannot be performed on {@code Drawable2D} shapes, as that would shift them out of
     * alignment with the axes. However, this method converts the shape into a Path, which <i>can</i> be
     * transformed.</p>
     *
     * <p>Note that the path returned by this method is a new object, separate from the original {@code Drawable2D}
     * shape.</p>
     *
     * @return Path equivalent to the 2D shape
     */
    public Path getAsPath() {
        Path p = new Path(getDrawingPanel());
        p.setFillColor(getFillColor());
        p.setStroke(getStroke());
        p.setStrokeColor(getStrokeColor());
        PathIterator i = getPathIterator(null);
        double[] coords = new double[6];
        while (!i.isDone()) {
            switch (i.currentSegment(coords)) {
                case PathIterator.SEG_CLOSE:
                    p.closePath();
                    break;
                case PathIterator.SEG_CUBICTO:
                    p.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                    break;
                case PathIterator.SEG_LINETO:
                    p.lineTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_MOVETO:
                    p.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    p.quadTo(coords[0], coords[1], coords[2], coords[3]);
                    break;
                default:
                    System.err.println("!");
            }
            i.next();
        }
        return p;
    }
}

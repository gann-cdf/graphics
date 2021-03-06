package org.gannacademy.cdf.graphics.geom;

import org.gannacademy.cdf.graphics.Drawable2D;
import org.gannacademy.cdf.graphics.DrawableException;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

/**
 * <p>Draw an arc as a section of an ellipse</p>
 *
 * <p><img src="doc-files/Arc.png" alt="Arc diagram"></p>
 *
 * <p>Arcs are sections of ellipses, inscribed within their rectangular bounding box.</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues" target="_blank">Seth Battis</a>
 */
public class Arc extends Drawable2D {
    /**
     * <p>Construct a new arc</p>
     *
     * <p><img src="doc-files/Arc.png" alt="Diagram of Arc parameters"></p>
     *
     * <p>All window coordinates are measured in pixels, with the X-axis increasing from left to right and the Y-axis
     * increasing from top to bottom. All window coordinates exist in the first quadrant.</p>
     *
     * <p><img src="../doc-files/window-coordinates.png" alt="Diagram of window coordinates"></p>
     *
     * @param x            coordinate of origin
     * @param y            coordinate of origin
     * @param width        in pixels
     * @param height       in pixels
     * @param start        angle in degrees (measured counter-clockwise from 0&deg; (a.k.a. "east")
     * @param extent       angle in degrees of the arc (measured counter-clockwise from {@code start})
     * @param drawingPanel on which to draw
     */
    public Arc(double x, double y, double width, double height, double start, double extent, DrawingPanel drawingPanel) {
        try {
            setShape(new Arc2D.Double(x, y, width, height, start, extent, Arc2D.PIE));
            setDrawingPanel(drawingPanel);
        } catch (DrawableException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Underlying {@link Arc2D} geometry
     *
     * @return Underlying {@link Arc2D} geometry
     */
    protected Arc2D getShapeAsArc() {
        return (Arc2D) getShape();
    }

    @Override
    public void setShape(Shape shape) throws DrawableException {
        if (shape instanceof Arc2D) {
            super.setShape(shape);
        } else {
            throw new DrawableException("Attempt to set Arc's underlying shape to a non-Arc2D instance");
        }
    }

    /**
     * Start angle of arc
     *
     * @return Angle measured counter-clockwise in degrees from 0&deg; (East) of start of ellipse segment
     * @see Arc2D#getAngleStart()
     */
    public double getAngleStart() {
        return getShapeAsArc().getAngleStart();
    }

    /**
     * Extent angle of arc
     *
     * @return Angle measured counter-clockwise in degrees from start angle determining extent of arc
     * @see Arc2D#getAngleExtent()
     */
    public double getAngleExtent() {
        return getShapeAsArc().getAngleExtent();
    }

    /**
     * Set start angle of arc
     * 
     * @param start Angle measured counter-clockwise in degrees from 0&deg; (East) of start of ellipse segment
     * @see Arc2D#setAngleStart(double)
     */
    public void setAngleStart(double start) {
        getShapeAsArc().setAngleStart(start);
    }

    /**
     * Set extent angle of arc
     * 
     * @param extent Angle measured counter-clockwise in degrees from start angle determining extent of arc
     * @see Arc2D#setAngleExtent(double)
     */
    public void setAngleExtent(double extent) {
        getShapeAsArc().setAngleExtent(extent);
    }

    @Override
    public double getHeight() {
        return getShapeAsArc().getHeight();
    }

    @Override
    public double getWidth() {
        return getShapeAsArc().getWidth();
    }

    @Override
    public double getX() {
        return getShapeAsArc().getX();
    }

    @Override
    public double getY() {
        return getShapeAsArc().getY();
    }

    @Override
    public Rectangle2D getBounds() {
        return getShapeAsArc().getBounds();
    }
}

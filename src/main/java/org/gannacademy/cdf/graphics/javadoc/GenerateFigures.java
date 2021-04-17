package org.gannacademy.cdf.graphics.javadoc;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.Image;
import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.geom.*;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.gannacademy.cdf.graphics.Drawable.NO_STROKE;
import static org.gannacademy.cdf.graphics.ui.DrawingPanel.DEFAULT_HEIGHT;
import static org.gannacademy.cdf.graphics.ui.DrawingPanel.DEFAULT_WIDTH;

public class GenerateFigures extends AppWindow {
    private static final String BASE_PATH = "src/main/java/org/gannacademy/cdf/graphics";
    private static final double
        DIAGRAM_WIDTH = 200,
        DIAGRAM_HEIGHT = DIAGRAM_WIDTH / 1.618,
        POINT_DIAMETER = 4,
        ROUND_RECTANGLE_ARC_DIAMETER = 40,
        ARC_START_ANGLE = 70,
        ARC_EXTENT_ANGLE = 220,
        ARROW_SIDE = 10,
        ARC_START_SCALE = 2.0 / 3.0,
        ARC_EXTENT_SCALE = 2.0 / 3.0;

    private static final int
        BEZIER_CURVE_INTERPOLATIONS = 9;

    private static final Color
        DIAGRAM = Color.BLACK,
        BACKGROUND = Color.WHITE,
        POINT_FILL = Color.WHITE,
        HIGHLIGHT_1 = new Color(77, 122, 151),
        HIGHLIGHT_2 = new Color(248, 152, 29),
        HIGHLIGHT_3 = new Color(230, 100, 80),
        HIGHLIGHT_4 = new Color(80, 210, 80);
    private static final Font LABEL = new Font("Arial", Font.PLAIN, 12);
    private static final BasicStroke DASHED = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{3, 3}, 0);

    private Rectangle2D bounds = new Rectangle2D.Double(0, 0, DIAGRAM_WIDTH, DIAGRAM_HEIGHT);
    private HashMap<String, Drawable> components;

    private interface Figure {
        void generate();
    }

    private static void emptyDir(File dir) {
        File[] contents = dir.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory()) {
                    emptyDir(file);
                }
                file.delete();
            }
        }
    }

    private static void resetDir(String dir) {
        if (!new File(dir).mkdirs()) {
            emptyDir(new File(dir));
            System.out.println("Emptied " + dir);
        }
    }

    private void saveFigure(String subpackage, String name) {
        double width = 0, height = 0;
        for (Map.Entry<String, Drawable> entry : components.entrySet()) {
            Drawable component = entry.getValue();
            if (component instanceof Text) {
                Text t = (Text) component;
                if (t.getX() + t.getWidth() > width) {
                    width = t.getX() + t.getWidth();
                }
                if (t.getY() + t.getMaxDescent() > height) {
                    height = t.getY() + t.getMaxDescent();
                }
            } else {
                if (component.getX() + component.getWidth() > width) {
                    width = component.getX() + component.getWidth();
                }
                if (component.getY() + component.getHeight() > height) {
                    height = component.getY() + component.getHeight();
                }
            }
        }
        saveFigure(subpackage, name, width + 1, height + 1);
    }

    private void saveFigure(String subpackage, String name, double width, double height) {
        getDrawingPanel().setBackground(BACKGROUND);
        getDrawingPanel().setSize((int) width, (int) height);
        getDrawingPanel().saveAs(BASE_PATH + (subpackage.length() > 0 ? "/" : "") + subpackage + "/doc-files/" + name + ".png", "PNG");
        System.out.println("Generated " + (int) width + "×" + (int) height + " PNG " + BASE_PATH + (subpackage.length() > 0 ? "/" : "") + subpackage + "/doc-files/" + name + ".png");
    }

    private Path arrow(double x, double y, double angle) {
        angle = Math.toRadians(angle);
        double
            x1 = x + Math.cos(angle + Math.PI * 8.0 / 9.0) * ARROW_SIDE,
            y1 = y + Math.sin(angle + Math.PI * 8.0 / 9.0) * ARROW_SIDE,
            x2 = x + Math.cos(angle + Math.PI * 10.0 / 9.0) * ARROW_SIDE,
            y2 = y + Math.sin(angle + Math.PI * 10.0 / 9.0) * ARROW_SIDE;
        Path arrow = new Path(getDrawingPanel());
        arrow.moveTo(x, y);
        arrow.lineTo(x1, y1);
        arrow.lineTo(x2, y2);
        arrow.lineTo(x, y);
        return arrow;
    }

    private void figureDrawingApp() {
        getDrawingPanel().clear();
        components.clear();

        Rectangle r = new Rectangle(200, 75, 200, 200, getDrawingPanel());
        r.setFillColor(Color.BLUE);
        Ellipse e = new Ellipse(250, 125, 200, 200, getDrawingPanel());
        e.setFillColor(new Color(255, 100, 100, 200));

        components.put("r", r);
        components.put("e", e);
        saveFigure("", "DrawingApp", DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private void figureWindowCoordinates() {
        resetComponents();
        components.remove("rightEdge").removeFromDrawingPanel();
        components.remove("bottomEdge").removeFromDrawingPanel();

        Line
            xAxis = (Line) components.get("topEdge"),
            yAxis = (Line) components.get("leftEdge");

        Text
            originLabel = (Text) components.get("originLabel"),
            xAxisLabel = (Text) components.get("widthLabel"),
            yAxisLabel1 = (Text) components.get("heightLabel1"),
            yAxisLabel2 = (Text) components.get("heightLabel2"),
            yAxisLabel3 = (Text) components.get("heightLabel3"),
            ptLabel = new Text("(" + (int) (bounds.getWidth() * 2 / 3) + ", " + (int) (bounds.getHeight() * 2 / 3) + ")", 0, 0, getDrawingPanel());
        components.put("ptLabel", ptLabel);

        Path
            xAxisArrow = arrow(bounds.getX() + bounds.getWidth(), bounds.getY(), 0),
            yAxisArrow = arrow(bounds.getX(), bounds.getY() + bounds.getHeight(), 90);
        components.put("xAxisArrow", xAxisArrow);
        components.put("yAxisArrow", yAxisArrow);

        Ellipse pt = new Ellipse(
            bounds.getX() + bounds.getWidth() * 2 / 3 - POINT_DIAMETER / 2,
            bounds.getY() + bounds.getHeight() * 2 / 3 - POINT_DIAMETER / 2,
            POINT_DIAMETER,
            POINT_DIAMETER,
            getDrawingPanel()
        );
        components.put("pt", pt);

        originLabel.setText("(0, 0)");

        pt.setFillColor(DIAGRAM);
        pt.setStrokeColor(DIAGRAM);

        ptLabel.setFont(LABEL);
        ptLabel.setStrokeColor(pt.getFillColor());
        ptLabel.setLocation(pt.getX() - ptLabel.getWidth() / 2, pt.getY() - POINT_DIAMETER / 2 - ptLabel.getMaxDescent());

        xAxisLabel.setText("Positive X-axis →");
        xAxisLabel.setLocation(
            bounds.getX() + bounds.getWidth() / 2 - xAxisLabel.getWidth() / 2,
            bounds.getY() + xAxisLabel.getHeight()
        );

        yAxisLabel1.setText(" Positive");
        yAxisLabel2.setText(" Y-axis");
        yAxisLabel3.setText(" ↓");

        yAxisLabel2.setLocation(
            bounds.getX(),
            bounds.getY() + bounds.getHeight() / 2 - yAxisLabel2.getMaxAscent() / 2
        );
        yAxisLabel1.setLocation(yAxisLabel2.getX(), yAxisLabel2.getY() - yAxisLabel2.getHeight());
        yAxisLabel3.setLocation(yAxisLabel2.getX(), yAxisLabel2.getY() + yAxisLabel3.getHeight());

        xAxis.setLine(bounds.getX(), bounds.getY(), bounds.getX() + bounds.getWidth(), bounds.getY());
        xAxisArrow.setStroke(NO_STROKE);
        xAxisArrow.setFillColor(xAxis.getStrokeColor());

        yAxis.setLine(bounds.getX(), bounds.getY(), bounds.getX(), bounds.getY() + bounds.getHeight());
        yAxisArrow.setStroke(NO_STROKE);
        yAxisArrow.setFillColor(yAxis.getStrokeColor());
        saveFigure("", "window-coordinates");
    }

    private void figureStrokeFill() {
        getDrawingPanel().clear();
        components.clear();

        RoundRectangle roundRectangle = new RoundRectangle(
            0, 0, DIAGRAM_WIDTH, DIAGRAM_HEIGHT,
            DIAGRAM_HEIGHT / 3, DIAGRAM_HEIGHT / 3,
            getDrawingPanel()
        );
        components.put("roundRectangle", roundRectangle);

        Text
            strokeLabel = new Text("Stroke", 0, 0, getDrawingPanel()),
            fillLabel = new Text("Fill", 0, 0, getDrawingPanel());
        components.put("strokeLabel", strokeLabel);
        components.put("fillLabel", fillLabel);

        strokeLabel.setFont(new Font(LABEL.getFontName(), Font.BOLD, LABEL.getSize()));
        strokeLabel.setStrokeColor(Color.WHITE);

        fillLabel.setFont(strokeLabel.getFont());

        roundRectangle.setLocation(strokeLabel.getHeight() / 2, strokeLabel.getHeight() / 2);
        roundRectangle.setStroke(new BasicStroke((float) strokeLabel.getHeight(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        roundRectangle.setStrokeColor(HIGHLIGHT_1);
        roundRectangle.setFillColor(HIGHLIGHT_2);

        strokeLabel.setLocation(
            roundRectangle.getX() + roundRectangle.getWidth() / 2 - strokeLabel.getWidth() / 2,
            roundRectangle.getY() - strokeLabel.getHeight() / 2 + strokeLabel.getMaxAscent()
        );
        strokeLabel.setStrokeColor(roundRectangle.getStrokeColor().brighter());

        fillLabel.setLocation(
            roundRectangle.getX() + roundRectangle.getWidth() / 2 - fillLabel.getWidth() / 2,
            roundRectangle.getY() + roundRectangle.getHeight() / 2 - fillLabel.getMaxAscent() / 2
        );
        fillLabel.setStrokeColor(roundRectangle.getFillColor().darker());

        saveFigure(
            "", "stroke-fill",
            roundRectangle.getWidth() + strokeLabel.getHeight(),
            roundRectangle.getHeight() + strokeLabel.getHeight()
        );
    }

    private void figureRectangle() {
        resetComponents();
        components.put("rectangle", new Rectangle(bounds.getX(), bounds.getY(), DIAGRAM_WIDTH, DIAGRAM_HEIGHT, getDrawingPanel()));
        saveFigure("geom", "Rectangle");
    }

    private void figureEllipse() {
        resetComponents();
        components.put("ellipse", new Ellipse(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), getDrawingPanel()));
        saveFigure("geom", "Ellipse");
    }

    private void figureRoundRectangle() {
        resetComponents();

        Ellipse arc = new Ellipse(
            bounds.getX() + bounds.getWidth() - ROUND_RECTANGLE_ARC_DIAMETER,
            bounds.getY() + bounds.getHeight() - ROUND_RECTANGLE_ARC_DIAMETER,
            ROUND_RECTANGLE_ARC_DIAMETER,
            ROUND_RECTANGLE_ARC_DIAMETER,
            getDrawingPanel()
        );
        components.put("arc", arc);

        Line
            rightEdge = (Line) components.get("rightEdge"),
            bottomEdge = (Line) components.get("bottomEdge"),
            arcLeftEdge = new Line(
                arc.getX(),
                arc.getY() + arc.getHeight() / 2,
                arc.getX(),
                rightEdge.getY2() + components.get("widthLabel").getHeight(),
                getDrawingPanel()
            ),
            arcRightEdge = new Line(
                arc.getX() + arc.getWidth(),
                arc.getY() + arc.getHeight() / 2,
                arc.getX() + arc.getWidth(),
                arcLeftEdge.getY2(),
                getDrawingPanel()
            ),
            arcTopEdge = new Line(
                arc.getX() + arc.getWidth() / 2,
                arc.getY(),
                bottomEdge.getX2() + components.get("originLabel").getHeight(),
                arc.getY(),
                getDrawingPanel()
            ),
            arcBottomEdge = new Line(
                arc.getX() + arc.getWidth() / 2,
                bottomEdge.getY1(),
                arcTopEdge.getX2(),
                bottomEdge.getY2(),
                getDrawingPanel()
            );
        components.put("arcLeftEdge", arcLeftEdge);
        components.put("arcRightEdge", arcRightEdge);
        components.put("arcTopEdge", arcTopEdge);
        components.put("arcBottomEdge", arcBottomEdge);

        Text
            arcWidthLabel1 = new Text("↔", 0, 0, getDrawingPanel()),
            arcWidthLabel2 = new Text("Arc", 0, 0, getDrawingPanel()),
            arcWidthLabel3 = new Text("Width", 0, 0, getDrawingPanel()),
            arcHeightLabel = new Text("↕ Arc Height", 0, 0, getDrawingPanel());
        components.put("arcWidthLabel1", arcWidthLabel1);
        components.put("arcWidthLabel2", arcWidthLabel2);
        components.put("arcWidthLabel3", arcWidthLabel3);
        components.put("arcHeightLabel", arcHeightLabel);

        arc.setStroke(GenerateFigures.DASHED);
        arc.setStrokeColor(HIGHLIGHT_2);

        arcLeftEdge.setStroke(DASHED);
        arcLeftEdge.setStrokeColor(arc.getStrokeColor());

        arcRightEdge.setStroke(DASHED);
        arcRightEdge.setStrokeColor(arc.getStrokeColor());

        arcTopEdge.setStroke(DASHED);
        arcTopEdge.setStrokeColor(arc.getStrokeColor());

        arcBottomEdge.setStroke(GenerateFigures.DASHED);
        arcBottomEdge.setStrokeColor(arc.getStrokeColor());

        rightEdge.setDrawingPanel(getDrawingPanel()); // cheat to pop this component forward z-wise
        bottomEdge.setDrawingPanel(getDrawingPanel()); // cheat to pop this component forward z-wise

        arcWidthLabel1.setFont(LABEL);
        arcWidthLabel1.setStrokeColor(arc.getStrokeColor());
        arcWidthLabel1.setLocation(arc.getX() + arc.getWidth() / 2 - arcWidthLabel1.getWidth() / 2, ((Line) components.get("leftEdge")).getY2());

        arcWidthLabel2.setFont(LABEL);
        arcWidthLabel2.setStrokeColor(arc.getStrokeColor());
        arcWidthLabel2.setLocation(arc.getX() + arc.getWidth() / 2 - arcWidthLabel2.getWidth() / 2, arcWidthLabel1.getY() + arcWidthLabel2.getHeight());

        arcWidthLabel3.setFont(LABEL);
        arcWidthLabel3.setStrokeColor(arc.getStrokeColor());
        arcWidthLabel3.setLocation(arc.getX() + arc.getWidth() / 2 - arcWidthLabel3.getWidth() / 2, arcWidthLabel2.getY() + arcWidthLabel3.getHeight());

        arcHeightLabel.setFont(LABEL);
        arcHeightLabel.setStrokeColor(arc.getStrokeColor());
        arcHeightLabel.setLocation(
            components.get("heightLabel3").getX() + components.get("originLabel").getHeight(),
            bounds.getY() + bounds.getHeight() - arc.getHeight() / 2 + arcHeightLabel.getMaxAscent() / 2);

        components.put("roundRectangle", new RoundRectangle(
            bounds.getX(),
            bounds.getY(),
            bounds.getWidth(),
            bounds.getHeight(),
            ROUND_RECTANGLE_ARC_DIAMETER,
            ROUND_RECTANGLE_ARC_DIAMETER,
            getDrawingPanel()
        ));
        saveFigure("geom", "RoundRectangle");
    }

    private void figureArc() {
        resetComponents();

        Arc
            startAngle = new Arc(
            bounds.getX() + (bounds.getWidth() * (1 - ARC_START_SCALE)) / 2,
            bounds.getY() + (bounds.getHeight() * (1 - ARC_START_SCALE)) / 2,
            bounds.getWidth() * ARC_START_SCALE,
            bounds.getHeight() * ARC_START_SCALE,
            0,
            ARC_START_ANGLE,
            getDrawingPanel()
        ),
            extentAngle = new Arc(
                bounds.getX() + (bounds.getWidth() * (1 - ARC_EXTENT_SCALE)) / 2,
                bounds.getY() + (bounds.getHeight() * (1 - ARC_EXTENT_SCALE)) / 2,
                bounds.getWidth() * ARC_EXTENT_SCALE,
                bounds.getHeight() * ARC_EXTENT_SCALE,
                ARC_START_ANGLE,
                ARC_EXTENT_ANGLE,
                getDrawingPanel()
            );
        components.put("startAngle", startAngle);
        components.put("extentAngle", extentAngle);

        Path
            startArrow = arrow(
            bounds.getX() + bounds.getWidth() / 2 + Math.cos(Math.toRadians(ARC_START_ANGLE)) * bounds.getWidth() * ARC_START_SCALE / 2,
            bounds.getY() + bounds.getHeight() / 2 - Math.sin(Math.toRadians(ARC_START_ANGLE)) * bounds.getHeight() * ARC_START_SCALE / 2,
            270 - ARC_START_ANGLE
        ),
            extentArrow = arrow(
                bounds.getX() + bounds.getWidth() / 2 + Math.cos(Math.toRadians(ARC_START_ANGLE + ARC_EXTENT_ANGLE)) * bounds.getWidth() * ARC_EXTENT_SCALE / 2,
                bounds.getY() + bounds.getHeight() / 2 - Math.sin(Math.toRadians(ARC_START_ANGLE + ARC_EXTENT_ANGLE)) * bounds.getHeight() * ARC_EXTENT_SCALE / 2,
                270 - (ARC_START_ANGLE + ARC_EXTENT_ANGLE)
            );
        components.put("startArrow", startArrow);
        components.put("extentArrow", extentArrow);

        Text
            startLabel = new Text("Start", 0, 0, getDrawingPanel()),
            extentLabel = new Text("Extent", 0, 0, getDrawingPanel());
        components.put("startLabel", startLabel);
        components.put("extentLabel", extentLabel);

        startAngle.setStroke(DASHED);
        startAngle.setStrokeColor(HIGHLIGHT_4);

        startArrow.setFillColor(startAngle.getStrokeColor());
        startArrow.setStroke(Drawable.NO_STROKE);

        extentAngle.setStroke(DASHED);
        extentAngle.setStrokeColor(HIGHLIGHT_3);

        extentArrow.setFillColor(extentAngle.getStrokeColor());
        extentArrow.setStroke(Drawable.NO_STROKE);

        startLabel.setFont(LABEL);
        startLabel.setStrokeColor(startAngle.getStrokeColor());
        startLabel.setLocation(startArrow.getX() + ARROW_SIDE, startArrow.getY());

        extentLabel.setFont(LABEL);
        extentLabel.setStrokeColor(extentAngle.getStrokeColor());
        extentLabel.setLocation(extentArrow.getX() - ARROW_SIDE - extentLabel.getWidth(), extentArrow.getY() + extentLabel.getHeight());

        components.put("arc", new Arc(
            bounds.getX(),
            bounds.getY(),
            bounds.getWidth(),
            bounds.getHeight(),
            ARC_START_ANGLE,
            ARC_EXTENT_ANGLE,
            getDrawingPanel()
        ));
        saveFigure("geom", "Arc");
    }

    private void figureLine() {
        resetComponents(false);
        components.remove("ctrlPt1Label").removeFromDrawingPanel();
        components.remove("ctrlHandle1").removeFromDrawingPanel();
        components.remove("ctrlPt1").removeFromDrawingPanel();
        components.remove("ctrlPt2Label").removeFromDrawingPanel();
        components.remove("ctrlHandle2").removeFromDrawingPanel();
        components.remove("ctrlPt2").removeFromDrawingPanel();

        components.put("line", new Line(
            bounds.getX(),
            bounds.getY(),
            bounds.getX() + bounds.getWidth(),
            bounds.getY() + bounds.getHeight(),
            getDrawingPanel()
        ));
        saveFigure("geom", "Line");
    }

    private void figureQuadCurve() {
        resetComponents(false);
        components.remove("ctrlPt2").removeFromDrawingPanel();
        components.remove("ctrlPt2Label").removeFromDrawingPanel();

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2");

        Text ctrlPt1Label = (Text) components.get("ctrlPt1Label");

        ctrlHandle2.setLine(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(), ctrlHandle1.getX2(), ctrlHandle1.getY2());

        ctrlPt1Label.setText("Control Point");
        ctrlPt1Label.setLocation(ctrlHandle1.getX2() - ctrlPt1Label.getWidth() / 2, ctrlPt1Label.getY());

        components.put("quadCurve", new QuadCurve(
            bounds.getX(),
            bounds.getY(),
            ctrlHandle1.getX2(),
            ctrlHandle1.getY2(),
            bounds.getX() + bounds.getWidth(),
            bounds.getY() + bounds.getHeight(),
            getDrawingPanel()
        ));
        saveFigure("geom", "QuadCurve");
    }

    private void figureCubicCurve() {
        resetComponents(false);

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2");

        components.put("cubicCurve", new CubicCurve(
            bounds.getX(),
            bounds.getY(),
            ctrlHandle1.getX2(),
            ctrlHandle1.getY2(),
            ctrlHandle2.getX2(),
            ctrlHandle2.getY2(),
            bounds.getX() + bounds.getWidth(),
            bounds.getY() + bounds.getHeight(),
            getDrawingPanel()
        ));
        saveFigure("geom", "CubicCurve");
    }

    private void figureImage() {
        getDrawingPanel().clear();
        components.clear();

        Text originLabel = new Text("(x, y)", 1, 0, getDrawingPanel());
        components.put("originLabel", originLabel);

        Ellipse origin = new Ellipse(0, 0, POINT_DIAMETER, POINT_DIAMETER, getDrawingPanel());
        components.put("origin", origin);

        Image image = new Image("/java-logo.png", 0, 0, getDrawingPanel());
        components.put("image", image);

        originLabel.setFont(LABEL);
        originLabel.setStrokeColor(DIAGRAM);
        originLabel.setLocation(originLabel.getX(), originLabel.getHeight() - originLabel.getMaxDescent() + 1);

        origin.setFillColor(DIAGRAM);
        origin.setLocation(
            originLabel.getX() + originLabel.getWidth() / 2 - POINT_DIAMETER / 2,
            originLabel.getY() + originLabel.getMaxDescent()
        );

        image.setStroke(DASHED);
        image.setStrokeColor(HIGHLIGHT_1);
        image.setLocation(origin.getX() + POINT_DIAMETER / 2, origin.getY() + POINT_DIAMETER / 2);
        image.setWidth(bounds.getHeight() / image.getHeight() * image.getWidth());
        image.setHeight(bounds.getHeight());

        saveFigure("", "Image");
    }

    private void figureText() {
        resetComponents();

        Line
            baseline = new Line(0, 0, 0, 0, getDrawingPanel()),
            maxAscent = (Line) components.get("topEdge"),
            maxDescent = (Line) components.get("bottomEdge"),
            leftEdge = (Line) components.get("leftEdge"),
            rightEdge = (Line) components.get("rightEdge");
        components.put("baseline", baseline);

        Text
            originLabel = (Text) components.get("originLabel"),
            text = new Text("String", 1, 0, getDrawingPanel()),
            baselineLabel = new Text("Baseline", 0, 0, getDrawingPanel()),
            maxAscentLabel = new Text("Max Ascent", 0, 0, getDrawingPanel()),
            maxDescentLabel = new Text("Max Descent", 0, 0, getDrawingPanel()),
            heightLabel1 = (Text) components.get("heightLabel1"),
            heightLabel2 = (Text) components.get("heightLabel2"),
            heightLabel3 = (Text) components.get("heightLabel3"),
            widthLabel = (Text) components.get("widthLabel");
        components.put("text", text);
        components.put("baselineLabel", baselineLabel);
        components.put("maxAscentLabel", maxAscentLabel);
        components.put("maxDescentLabel", maxDescentLabel);

        Ellipse origin = (Ellipse) components.get("origin");

        originLabel.setStrokeColor(HIGHLIGHT_2);

        text.setFont(new Font("Times New Roman", Font.ITALIC, (int) (DIAGRAM_HEIGHT * 0.85)));
        text.setLocation(originLabel.getX() + originLabel.getWidth() / 2, text.getMaxAscent() + 1);
        bounds.setFrame(text.getX(), text.getY() - text.getMaxAscent(), text.getWidth(), text.getHeight());

        widthLabel.setLocation(bounds.getX() + bounds.getWidth() / 2 - widthLabel.getWidth() / 2, bounds.getY() + bounds.getHeight() + widthLabel.getHeight());

        leftEdge.setLine(bounds.getX(), bounds.getY(), bounds.getX(), widthLabel.getY());
        rightEdge.setLine(bounds.getX() + bounds.getWidth(), bounds.getY(), bounds.getX() + bounds.getWidth(), widthLabel.getY());

        heightLabel2.setLocation(bounds.getX() + bounds.getWidth() + heightLabel2.getHeight(), bounds.getY() + bounds.getHeight() / 2 + heightLabel2.getMaxAscent() / 2);
        heightLabel1.setLocation(heightLabel2.getX() + heightLabel2.getWidth() / 2 - heightLabel1.getWidth() / 2, heightLabel2.getY() - heightLabel2.getHeight());
        heightLabel3.setLocation(heightLabel2.getX() + heightLabel2.getWidth() / 2 - heightLabel3.getWidth() / 2, heightLabel2.getY() + heightLabel3.getHeight());

        text.setLocation(originLabel.getX() + originLabel.getWidth() / 2, text.getMaxAscent() + 1);
        originLabel.setLocation(originLabel.getX(), text.getY() + originLabel.getHeight() - originLabel.getMaxDescent() + POINT_DIAMETER / 2);

        origin.setFillColor(originLabel.getStrokeColor());
        origin.setStrokeColor(originLabel.getStrokeColor());
        origin.setLocation(text.getX() - POINT_DIAMETER / 2, text.getY() - POINT_DIAMETER / 2);

        maxAscent.setLine(bounds.getX(), bounds.getY(), heightLabel2.getX() + heightLabel2.getWidth() / 2, bounds.getY());
        maxDescent.setLine(bounds.getX(), bounds.getY() + bounds.getHeight(), maxAscent.getX2(), bounds.getY() + bounds.getHeight());

        maxAscentLabel.setFont(LABEL);
        maxAscentLabel.setStrokeColor(maxAscent.getStrokeColor());
        maxAscentLabel.setLocation(maxAscent.getX2(), maxAscent.getY2() + maxAscentLabel.getMaxAscent());

        maxDescentLabel.setFont(LABEL);
        maxDescentLabel.setStrokeColor(maxDescent.getStrokeColor());
        maxDescentLabel.setLocation(maxDescent.getX2(), maxDescent.getY2());

        baseline.setStrokeColor(originLabel.getStrokeColor());
        baseline.setStroke(DASHED);
        baseline.setLine(text.getX(), text.getY(), maxAscent.getX2(), text.getY());

        baselineLabel.setFont(LABEL);
        baselineLabel.setStrokeColor(baseline.getStrokeColor());
        baselineLabel.setLocation(baseline.getX2(), baseline.getY2());

        saveFigure("", "Text");
    }

    private void figurePath() {
        resetComponents(false);

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2"),
            ctrlHandle3 = new Line(0, 0, 0, 0, getDrawingPanel()),
            ctrlHandle4 = new Line(0, 0, 0, 0, getDrawingPanel());
        components.put("ctrlHandle3", ctrlHandle3);
        components.put("ctrlHandle4", ctrlHandle4);

        Ellipse
            pt1 = (Ellipse) components.get("pt1"),
            pt2 = (Ellipse) components.get("pt2"),
            pt3 = new Ellipse(0, 0, POINT_DIAMETER, POINT_DIAMETER, getDrawingPanel()),
            pt4 = new Ellipse(0, 0, POINT_DIAMETER, POINT_DIAMETER, getDrawingPanel()),
            ctrlPt1 = (Ellipse) components.get("ctrlPt1"),
            ctrlPt2 = (Ellipse) components.get("ctrlPt2"),
            ctrlPt3 = new Ellipse(0, 0, POINT_DIAMETER, POINT_DIAMETER, getDrawingPanel());
        components.put("pt3", pt3);
        components.put("pt4", pt4);
        components.put("ctrlPt3", ctrlPt3);

        Text
            pt1Label = (Text) components.get("pt1Label"),
            pt2Label = (Text) components.get("pt2Label"),
            pt3Label = new Text("Point 3", 0, 0, getDrawingPanel()),
            pt4Label = new Text("Point 4", 0, 0, getDrawingPanel()),
            lineToLabel = (Text) components.get("ctrlPt1Label"),
            curveToLabel = (Text) components.get("ctrlPt2Label"),
            quadToLabel = new Text("Quad", 0, 0, getDrawingPanel());
        components.put("pt3Label", pt3Label);
        components.put("pt4Label", pt4Label);
        components.put("quadToLabel", quadToLabel);

        Path path = new Path(getDrawingPanel());
        components.put("path", path);

        lineToLabel.setText("Line");
        curveToLabel.setText("Curve");
        quadToLabel.setStrokeColor(lineToLabel.getStrokeColor());
        quadToLabel.setFont(LABEL);

        ctrlHandle3.setStroke(DASHED);
        ctrlHandle3.setStrokeColor(ctrlHandle1.getStrokeColor());

        ctrlHandle4.setStroke(DASHED);
        ctrlHandle4.setStrokeColor(ctrlHandle1.getStrokeColor());

        ctrlPt3.setStrokeColor(ctrlPt1.getStrokeColor());
        ctrlPt3.setStroke(ctrlPt1.getStroke());
        ctrlPt3.setFillColor(ctrlPt1.getFillColor());

        pt3.setStrokeColor(pt1.getStrokeColor());
        pt3.setFillColor(pt1.getFillColor());
        pt3.setStroke(pt1.getStroke());

        pt4.setStrokeColor(pt1.getStrokeColor());
        pt4.setFillColor(pt1.getFillColor());
        pt4.setStroke(pt1.getStroke());

        pt3Label.setStrokeColor(pt3.getFillColor());
        pt3Label.setFont(LABEL);

        pt4Label.setStrokeColor(pt4.getFillColor());
        pt4Label.setFont(LABEL);

        ctrlHandle1.setLine(
            bounds.getX() + bounds.getWidth() * 0.1, bounds.getY() + bounds.getHeight(),
            bounds.getX() + bounds.getWidth() * 0.4, bounds.getY() + bounds.getHeight()
        );
        ctrlHandle2.setLine(
            bounds.getX() + bounds.getWidth() * 0.5, bounds.getY() + bounds.getHeight() * 0.5,
            bounds.getX() + bounds.getWidth() * 0.5, bounds.getY() + bounds.getHeight() * 0.9
        );
        ctrlHandle3.setLine(
            ctrlHandle2.getX1(), ctrlHandle2.getY1(),
            ctrlHandle2.getX1(), bounds.getY()
        );
        ctrlHandle4.setLine(
            bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight() * 0.5,
            ctrlHandle3.getX2(), ctrlHandle3.getY2()
        );

        ctrlPt1.setLocation(ctrlHandle1.getX2() - POINT_DIAMETER / 2, ctrlHandle1.getY2() - POINT_DIAMETER / 2);
        ctrlPt2.setLocation(ctrlHandle2.getX2() - POINT_DIAMETER / 2, ctrlHandle2.getY2() - POINT_DIAMETER / 2);
        ctrlPt3.setLocation(ctrlHandle3.getX2() - POINT_DIAMETER / 2, ctrlHandle3.getY2() - POINT_DIAMETER / 2);

        pt2.setLocation(ctrlHandle1.getX1() - POINT_DIAMETER / 2, ctrlHandle1.getY1() - POINT_DIAMETER / 2);
        pt3.setLocation(ctrlHandle3.getX1() - POINT_DIAMETER / 2, ctrlHandle3.getY1() - POINT_DIAMETER / 2);
        pt4.setLocation(ctrlHandle4.getX1() - POINT_DIAMETER / 2, ctrlHandle4.getY1() - POINT_DIAMETER / 2);

        pt2Label.setLocation(Math.max(1, ctrlHandle1.getX1() - pt2Label.getWidth() / 2), ctrlHandle1.getY1() + pt2Label.getHeight());
        pt3Label.setLocation(ctrlHandle3.getX1() - pt3Label.getWidth() - POINT_DIAMETER, ctrlHandle3.getY1() + pt3Label.getMaxDescent());
        pt4Label.setLocation(ctrlHandle4.getX1() - pt4Label.getWidth() / 2, ctrlHandle4.getY1() + pt4Label.getHeight());

        lineToLabel.setLocation((bounds.getX() + ctrlHandle1.getX1()) / 2 + lineToLabel.getMaxDescent(), (bounds.getY() + ctrlHandle1.getY1()) / 2);
        curveToLabel.setLocation((ctrlHandle1.getX1() + ctrlHandle2.getX1()) / 2 - curveToLabel.getWidth() / 2, (ctrlHandle1.getY1() + ctrlHandle2.getY1()) / 2 + curveToLabel.getHeight());
        quadToLabel.setLocation((ctrlHandle3.getX1() + ctrlHandle4.getX1()) / 2 - quadToLabel.getWidth() / 2, (ctrlHandle3.getY1() + ctrlHandle4.getY1()) / 2);

        path.moveTo(bounds.getX(), bounds.getY());
        path.lineTo(ctrlHandle1.getX1(), ctrlHandle1.getY1());
        path.curveTo(
            ctrlHandle1.getX2(), ctrlHandle1.getY2(),
            ctrlHandle2.getX2(), ctrlHandle2.getY2(),
            ctrlHandle2.getX1(), ctrlHandle2.getY1()
        );
        path.quadTo(
            ctrlHandle3.getX2(), ctrlHandle3.getY2(),
            ctrlHandle4.getX1(), ctrlHandle4.getY1()
        );

        saveFigure("geom", "Path");
    }

    private void figureBezierQuadCurveFig1() {
        figureBezierQuadCurveFig1(true);
    }

    private void figureBezierQuadCurveFig1(boolean isStandAlone) {
        resetComponents(false);
        components.remove("ctrlPt2").removeFromDrawingPanel();
        components.remove("ctrlPt2Label").removeFromDrawingPanel();

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2");

        Ellipse pt2 = (Ellipse) components.get("pt2");

        Text
            ctrlPtLabel = (Text) components.get("ctrlPt1Label"),
            pt2Label = (Text) components.get("pt2Label");

        ctrlHandle2.setLine(
            bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight(),
            ctrlHandle1.getX2(), ctrlHandle1.getY2()
        );

        QuadCurve quadCurve = new QuadCurve(0, 0, 0, 0, 0, 0, getDrawingPanel());
        components.put("quadCurve", quadCurve);

        ctrlPtLabel.setText("Control Point");
        ctrlPtLabel.setLocation(ctrlHandle1.getX2() - ctrlPtLabel.getWidth() / 2, ctrlPtLabel.getY());

        pt2.setLocation(ctrlHandle2.getX1() - POINT_DIAMETER / 2, ctrlHandle2.getY1() - POINT_DIAMETER / 2);
        pt2Label.setLocation(ctrlHandle2.getX1() - pt2Label.getWidth() / 2, pt2Label.getY());

        quadCurve.setCurve(
            ctrlHandle1.getX1(), ctrlHandle1.getY1(),
            ctrlHandle1.getX2(), ctrlHandle1.getY2(),
            ctrlHandle2.getX1(), ctrlHandle2.getY1()
        );


        if (isStandAlone) {
            saveFigure("geom", "Bezier-QuadCurve-fig1");
        }
    }

    private void figureBezierQuadCurveFig2() {
        figureBezierQuadCurveFig2(true, 3);
    }

    private void figureBezierQuadCurveFig2(boolean isStandAlone, int interpolations) {
        figureBezierQuadCurveFig1(false);
        components.get("quadCurve").removeFromDrawingPanel();

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2");

        double
            dx1 = ctrlHandle1.getX2() - ctrlHandle1.getX1(),
            dy1 = ctrlHandle1.getY2() - ctrlHandle1.getY1(),
            dx2 = ctrlHandle2.getX1() - ctrlHandle2.getX2(),
            dy2 = ctrlHandle2.getY1() - ctrlHandle2.getY2();

        for (int i = 1; i < interpolations; i++) {
            double scale = (double) i / interpolations;
            Line line = new Line(
                ctrlHandle1.getX1() + dx1 * scale,
                ctrlHandle1.getY1() + dy1 * scale,
                ctrlHandle2.getX2() + dx2 * scale,
                ctrlHandle2.getY2() + dy2 * scale,
                getDrawingPanel()
            );
            components.put("interpolation" + i, line);

            Ellipse ellipse = new Ellipse(
                line.getX1() + (line.getX2() - line.getX1()) * scale - POINT_DIAMETER / 2,
                line.getY1() + (line.getY2() - line.getY1()) * scale - POINT_DIAMETER / 2,
                POINT_DIAMETER,
                POINT_DIAMETER,
                getDrawingPanel()
            );
            components.put("interpolationPt" + i, ellipse);

            line.setStrokeColor(Color.getHSBColor((float) scale, 1, 1));
            ellipse.setStrokeColor(line.getStrokeColor());
            ellipse.setFillColor(ellipse.getStrokeColor());
        }

        if (isStandAlone) {
            saveFigure("geom", "Bezier-QuadCurve-fig2");
        }
    }

    private void figureBezierQuadCurveFig3() {
        figureBezierQuadCurveFig3(true, BEZIER_CURVE_INTERPOLATIONS);
    }

    private void figureBezierQuadCurveFig3(boolean isStandAlone, int interpolations) {
        figureBezierQuadCurveFig2(false, interpolations);
        if (isStandAlone) {
            saveFigure("geom", "Bezier-QuadCurve-fig3");
        }
    }

    private void figureBezierQuadCurveFig4() {
        figureBezierQuadCurveFig4(BEZIER_CURVE_INTERPOLATIONS);
    }

    private void figureBezierQuadCurveFig4(int interpolations) {
        figureBezierQuadCurveFig3(false, interpolations);
        components.get("quadCurve").setDrawingPanel(getDrawingPanel());
        saveFigure("geom", "Bezier-QuadCurve-fig4");
    }

    private void figureBezierCubicCurveFig1() {
        figureBezierCubicCurveFig1(true);
    }

    private void figureBezierCubicCurveFig1(boolean isStandAlone) {
        resetComponents(false);

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2"),
            ctrlHandle3 = new Line(0, 0, 0, 0, getDrawingPanel());
        components.put("ctrlHandle3", ctrlHandle3);

        CubicCurve cubicCurve = new CubicCurve(0, 0, 0, 0, 0, 0, 0, 0, getDrawingPanel()
        );
        components.put("cubicCurve", cubicCurve);

        Ellipse
            ctrlPt2 = (Ellipse) components.get("ctrlPt2"),
            pt2 = (Ellipse) components.get("pt2");

        Text
            ctrlPt2Label = (Text) components.get("ctrlPt2Label"),
            pt2Label = (Text) components.get("pt2Label");

        components.get("ctrlPt1").setDrawingPanel(getDrawingPanel());
        components.get("ctrlPt2").setDrawingPanel(getDrawingPanel());

        ctrlHandle2.setLine(ctrlHandle2.getX2(), ctrlHandle2.getY2(), ctrlHandle2.getX1(), ctrlHandle2.getY1());

        pt2.setLocation(ctrlHandle2.getX1() - POINT_DIAMETER / 2, ctrlHandle2.getY1() - POINT_DIAMETER / 2);
        pt2Label.setLocation(ctrlHandle2.getX1() - pt2Label.getWidth() / 2, ctrlHandle2.getY() + pt2Label.getHeight());

        ctrlPt2.setLocation(ctrlHandle2.getX2() - POINT_DIAMETER / 2, ctrlHandle2.getY2() - POINT_DIAMETER / 2);
        ctrlPt2Label.setLocation(ctrlHandle2.getX2() - ctrlPt2Label.getWidth() / 2, ctrlHandle2.getY2() + ctrlPt2Label.getHeight());

        ctrlHandle3.setLine(ctrlHandle1.getX2(), ctrlHandle1.getY2(), ctrlHandle2.getX2(), ctrlHandle2.getY2());
        ctrlHandle3.setStrokeColor(ctrlHandle1.getStrokeColor());
        ctrlHandle3.setStroke(ctrlHandle1.getStroke());

        cubicCurve.setCurve(
            ctrlHandle1.getX1(), ctrlHandle1.getY1(),
            ctrlHandle1.getX2(), ctrlHandle1.getY2(),
            ctrlHandle2.getX2(), ctrlHandle2.getY2(),
            ctrlHandle2.getX1(), ctrlHandle2.getY1()
        );

        if (isStandAlone) {
            saveFigure("geom", "Bezier-CubicCurve-fig1");
        }
    }

    private void figureBezierCubicCurveFig2() {
        figureBezierCubicCurveFig2(true, BEZIER_CURVE_INTERPOLATIONS);
    }

    private void figureBezierCubicCurveFig2(boolean isStandAlone, int interpolations) {
        figureBezierCubicCurveFig1(false);
        components.get("cubicCurve").removeFromDrawingPanel();

        Line
            ctrlHandle1 = (Line) components.get("ctrlHandle1"),
            ctrlHandle2 = (Line) components.get("ctrlHandle2");

        double
            cx1 = ctrlHandle1.getX2() - ctrlHandle1.getX1(),
            cy1 = ctrlHandle1.getY2() - ctrlHandle1.getY1(),
            cxi = ctrlHandle2.getX2() - ctrlHandle1.getX2(),
            cyi = ctrlHandle2.getY2() - ctrlHandle1.getY2(),
            cx2 = ctrlHandle2.getX1() - ctrlHandle2.getX2(),
            cy2 = ctrlHandle2.getY1() - ctrlHandle2.getY2(),
            dx1i, dy1i, dxi2, dyi2;

        for (int i = 1; i < interpolations; i++) {
            double scale = (double) i / interpolations;
            Line
                line1 = new Line(
                ctrlHandle1.getX1() + cx1 * scale,
                ctrlHandle1.getY1() + cy1 * scale,
                ctrlHandle1.getX2() + cxi * scale,
                ctrlHandle1.getY2() + cyi * scale,
                getDrawingPanel()
            ),
                line2 = new Line(
                    line1.getX2(),
                    line1.getY2(),
                    ctrlHandle2.getX2() + cx2 * scale,
                    ctrlHandle2.getY2() + cy2 * scale,
                    getDrawingPanel()
                );
            components.put("interpolation" + (2 * i - 1), line1);
            components.put("interpolation" + (2 * i), line2);
            line1.setStrokeColor(Color.getHSBColor((float) scale, 1, 1));
            line2.setStrokeColor(line1.getStrokeColor());
        }

        if (isStandAlone) {
            saveFigure("geom", "Bezier-CubicCurve-fig2");
        }
    }

    private void figureBezierCubicCurveFig3() {
        figureBezierCubicCurveFig3(true, 2);
    }

    private void figureBezierCubicCurveFig3(boolean isStandAlone, int interpolations) {
        figureBezierCubicCurveFig2(false, interpolations);

        for (int i = 1; i < interpolations; i++) {
            double scale = (double) i / interpolations;
            Line
                line1 = (Line) components.get("interpolation" + (2 * i - 1)),
                line2 = (Line) components.get("interpolation" + (2 * i)),
                line3 = new Line(
                    line1.getX1() + (line1.getX2() - line1.getX1()) * scale,
                    line1.getY1() + (line1.getY2() - line1.getY1()) * scale,
                    line2.getX1() + (line2.getX2() - line2.getX1()) * scale,
                    line2.getY1() + (line2.getY2() - line2.getY1()) * scale,
                    getDrawingPanel()
                );
            components.put("interpolation" + (2 * i - 1) + "-" + 2 * i, line3);

            Ellipse ellipse = new Ellipse(
                line3.getX1() + (line3.getX2() - line3.getX1()) * scale - POINT_DIAMETER / 2,
                line3.getY1() + (line3.getY2() - line3.getY1()) * scale - POINT_DIAMETER / 2,
                POINT_DIAMETER,
                POINT_DIAMETER,
                getDrawingPanel()
            );
            components.put("interpolated-point" + i, ellipse);

            line3.setStrokeColor(line1.getStrokeColor());
            ellipse.setFillColor(line2.getStrokeColor());
            ellipse.setStrokeColor(ellipse.getFillColor());
        }

        if (isStandAlone) {
            saveFigure("geom", "Bezier-CubicCurve-fig3");
        }
    }

    private void figureBezierCubicCurveFig4() {
        figureBezierCubicCurveFig4(true, BEZIER_CURVE_INTERPOLATIONS);
    }

    private void figureBezierCubicCurveFig4(boolean isStandAlone, int interpolations) {
        figureBezierCubicCurveFig3(false, interpolations);
        if (isStandAlone) {
            saveFigure("geom", "Bezier-CubicCurve-fig4");
        }
    }

    private void figureBezierCubicCurveFig5() {
        figureBezierCubicCurveFig5(true, BEZIER_CURVE_INTERPOLATIONS);
    }

    private void figureBezierCubicCurveFig5(boolean isStandAlone, int interpolations) {
        figureBezierCubicCurveFig4(false, interpolations);
        components.get("cubicCurve").setDrawingPanel(getDrawingPanel());
        if (isStandAlone) {
            saveFigure("geom", "Bezier-CubicCurve-fig5");
        }
    }

    private void resetComponents() {
        resetComponents(true);
    }

    private void resetComponents(boolean is2D) {
        getDrawingPanel().clear();
        components.clear();
        if (is2D) {
            resetComponentsFor2D();
        } else {
            resetComponentsForPaths();
        }
    }

    private void resetComponentsFor2D() {
        Line
            leftEdge = new Line(0, 0, 0, 0, getDrawingPanel()),
            rightEdge = new Line(0, 0, 0, 0, getDrawingPanel()),
            topEdge = new Line(0, 0, 0, 0, getDrawingPanel()),
            bottomEdge = new Line(0, 0, 0, 0, getDrawingPanel());
        components.put("leftEdge", leftEdge);
        components.put("rightEdge", rightEdge);
        components.put("topEdge", topEdge);
        components.put("bottomEdge", bottomEdge);

        Text
            originLabel = new Text("(x, y)", 1, 0, getDrawingPanel()),
            widthLabel = new Text("← Width →", 0, 0, getDrawingPanel()),
            heightLabel1 = new Text("↑", 0, 0, getDrawingPanel()),
            heightLabel2 = new Text("Height", 0, 0, getDrawingPanel()),
            heightLabel3 = new Text("↓", 0, 0, getDrawingPanel());
        components.put("originLabel", originLabel);
        components.put("widthLabel", widthLabel);
        components.put("heightLabel1", heightLabel1);
        components.put("heightLabel2", heightLabel2);
        components.put("heightLabel3", heightLabel3);

        Ellipse origin = new Ellipse(0, 0, POINT_DIAMETER, POINT_DIAMETER, getDrawingPanel());
        components.put("origin", origin);

        for (String key : new String[]{"originLabel", "widthLabel", "heightLabel1", "heightLabel2", "heightLabel3"}) {
            Text t = (Text) components.get(key);
            t.setFont(LABEL);
            if (key.equals("originLabel")) {
                t.setStrokeColor(DIAGRAM);
                t.setLocation(t.getX(), t.getHeight() - t.getMaxDescent() + 1);
                bounds = new Rectangle2D.Double(
                    t.getX() + t.getWidth() / 2,
                    t.getY() + t.getMaxDescent() + POINT_DIAMETER,
                    DIAGRAM_WIDTH,
                    DIAGRAM_HEIGHT
                );
            } else {
                t.setStrokeColor(HIGHLIGHT_1);
            }
        }

        widthLabel.setLocation(bounds.getX() + bounds.getWidth() / 2 - widthLabel.getWidth() / 2, bounds.getY() + bounds.getHeight() + widthLabel.getHeight() + 2);

        heightLabel2.setLocation(bounds.getX() + bounds.getWidth() + heightLabel2.getHeight(), bounds.getY() + bounds.getHeight() / 2 + (heightLabel2.getMaxAscent() / 2));
        heightLabel1.setLocation(heightLabel2.getX() + heightLabel2.getWidth() / 2 - heightLabel1.getWidth() / 2, heightLabel2.getY() - heightLabel2.getHeight());
        heightLabel3.setLocation(heightLabel2.getX() + heightLabel2.getWidth() / 2 - heightLabel3.getWidth() / 2, heightLabel2.getY() + heightLabel3.getHeight());

        origin.setFillColor(DIAGRAM);
        origin.setLocation(bounds.getX() - POINT_DIAMETER / 2, bounds.getY() - POINT_DIAMETER / 2);

        leftEdge.setLine(bounds.getX(), bounds.getY(), bounds.getX(), widthLabel.getY());
        rightEdge.setLine(bounds.getX() + bounds.getWidth(), bounds.getY(), bounds.getX() + bounds.getWidth(), widthLabel.getY());
        topEdge.setLine(bounds.getX(), bounds.getY(), heightLabel1.getX() + heightLabel1.getWidth(), bounds.getY());
        bottomEdge.setLine(bounds.getX(), bounds.getY() + bounds.getHeight(), heightLabel1.getX() + heightLabel1.getWidth(), bounds.getY() + bounds.getHeight());

        leftEdge.setStroke(DASHED);
        leftEdge.setStrokeColor(widthLabel.getStrokeColor());

        rightEdge.setStroke(DASHED);
        rightEdge.setStrokeColor(widthLabel.getStrokeColor());

        topEdge.setStroke(DASHED);
        topEdge.setStrokeColor(heightLabel1.getStrokeColor());

        bottomEdge.setStroke(DASHED);
        bottomEdge.setStrokeColor(heightLabel1.getStrokeColor());
    }

    private void figurePathTranslate() {
        figurePathTranslate(true);
    }

    /**
     * TODO Diagram
     *
     * @param isStandAlone
     */
    private void figurePathTranslate(boolean isStandAlone) {
        getDrawingPanel().clear();
        components.clear();
        components.put("TODO", new Text("TODO Diagram", 1, 20, getDrawingPanel()));
        if (isStandAlone) {
        }

        saveFigure("geom", "Path-translate");
    }

    /**
     * TODO Diagram
     */
    private void figurePathScale() {
        getDrawingPanel().clear();
        components.clear();
        figurePathTranslate(false);
        saveFigure("geom", "Path-scale");
    }

    /**
     * TODO Diagram
     */
    private void figurePathRotate() {
        getDrawingPanel().clear();
        components.clear();
        figurePathTranslate(false);
        saveFigure("geom", "Path-rotate");
    }

    /**
     * TODO Diagram
     */
    private void figurePathShear() {
        getDrawingPanel().clear();
        components.clear();
        figurePathTranslate(false);
        saveFigure("geom", "Path-shear");
    }

    private void resetComponentsForPaths() {
        Text
            pt1Label = new Text("Point 1", 0, 0, getDrawingPanel()),
            pt2Label = new Text("Point 2", 0, 0, getDrawingPanel()),
            ctrlPt1Label = new Text("Control Point 1", 0, 0, getDrawingPanel()),
            ctrlPt2Label = new Text("Control Point 2", 0, 0, getDrawingPanel());
        components.put("pt1Label", pt1Label);
        components.put("pt2Label", pt2Label);
        components.put("ctrlPt1Label", ctrlPt1Label);
        components.put("ctrlPt2Label", ctrlPt2Label);

        pt1Label.setFont(LABEL);
        pt1Label.setLocation(1, pt1Label.getHeight() - pt1Label.getMaxDescent() + 1);

        bounds.setFrame(pt1Label.getX() + pt1Label.getWidth() / 2, bounds.getY(), bounds.getWidth(), bounds.getHeight());

        double
            ctrlX1 = bounds.getX() + bounds.getWidth() * 0.8,
            ctrlY1 = bounds.getY() + bounds.getHeight() * 0.1,
            ctrlX2 = bounds.getX() + bounds.getWidth() * 0.6,
            ctrlY2 = bounds.getY() + bounds.getHeight() * 0.95;

        Line
            ctrlHandle1 = new Line(bounds.getX(), bounds.getY(), ctrlX1, ctrlY1, getDrawingPanel()),
            ctrlHandle2 = new Line(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight(), ctrlX2, ctrlY2, getDrawingPanel());
        components.put("ctrlHandle1", ctrlHandle1);
        components.put("ctrlHandle2", ctrlHandle2);

        Ellipse
            pt1 = new Ellipse(
            bounds.getX() - POINT_DIAMETER / 2,
            bounds.getY() - POINT_DIAMETER / 2,
            POINT_DIAMETER,
            POINT_DIAMETER,
            getDrawingPanel()
        ),
            ctrlPt1 = new Ellipse(
                ctrlX1 - POINT_DIAMETER / 2,
                ctrlY1 - POINT_DIAMETER / 2,
                POINT_DIAMETER,
                POINT_DIAMETER,
                getDrawingPanel()
            ),
            ctrlPt2 = new Ellipse(
                ctrlX2 - POINT_DIAMETER / 2,
                ctrlY2 - POINT_DIAMETER / 2,
                POINT_DIAMETER,
                POINT_DIAMETER,
                getDrawingPanel()
            ),
            pt2 = new Ellipse(
                bounds.getX() + bounds.getWidth() - POINT_DIAMETER / 2,
                bounds.getY() + bounds.getHeight() - POINT_DIAMETER / 2,
                POINT_DIAMETER,
                POINT_DIAMETER,
                getDrawingPanel()
            );
        components.put("pt1", pt1);
        components.put("ctrlPt1", ctrlPt1);
        components.put("ctrlPt2", ctrlPt2);
        components.put("pt2", pt2);

        pt2Label.setStrokeColor(pt1Label.getStrokeColor());
        pt2Label.setFont(LABEL);
        pt2Label.setLocation(bounds.getX() + bounds.getWidth() - pt2Label.getWidth() / 2, bounds.getY() + bounds.getHeight() + POINT_DIAMETER / 2 + pt2Label.getHeight());

        ctrlPt1Label.setStrokeColor(HIGHLIGHT_1);
        ctrlPt1Label.setFont(LABEL);
        ctrlPt1Label.setLocation(ctrlX1 - ctrlPt1Label.getWidth() / 2, ctrlY1 - POINT_DIAMETER / 2 - ctrlPt1Label.getMaxDescent());

        ctrlPt2Label.setStrokeColor(ctrlPt1Label.getStrokeColor());
        ctrlPt2Label.setFont(LABEL);
        ctrlPt2Label.setLocation(ctrlX2 - ctrlPt2Label.getWidth() / 2, ctrlY2 + POINT_DIAMETER / 2 + ctrlPt2Label.getHeight());

        ctrlHandle1.setStroke(DASHED);
        ctrlHandle1.setStrokeColor(ctrlPt1Label.getStrokeColor());
        ctrlHandle2.setStroke(DASHED);
        ctrlHandle2.setStrokeColor(ctrlPt2Label.getStrokeColor());

        pt1.setFillColor(DIAGRAM);
        pt2.setFillColor(pt1.getFillColor());

        ctrlPt1.setStrokeColor(HIGHLIGHT_1);
        ctrlPt1.setFillColor(POINT_FILL);

        ctrlPt2.setStrokeColor(ctrlPt1.getStrokeColor());
        ctrlPt2.setFillColor(ctrlPt1.getFillColor());
    }

    @Override
    public void setup() {
        components = new HashMap<>();
        resetDir(BASE_PATH + "/doc-files");
        resetDir(BASE_PATH + "/geom/doc-files");
        resetDir(BASE_PATH + "/ui/doc-files");
        Figure[] figures = new Figure[]{
            this::figureDrawingApp,
            this::figureWindowCoordinates,
            this::figureStrokeFill,
            this::figureRectangle,
            this::figureEllipse,
            this::figureRoundRectangle,
            this::figureArc,
            this::figureBezierQuadCurveFig1,
            this::figureBezierQuadCurveFig2,
            this::figureBezierQuadCurveFig3,
            this::figureBezierQuadCurveFig4,
            this::figureBezierCubicCurveFig1,
            this::figureBezierCubicCurveFig2,
            this::figureBezierCubicCurveFig3,
            this::figureBezierCubicCurveFig4,
            this::figureBezierCubicCurveFig5,
            this::figureQuadCurve,
            this::figureLine,
            this::figureCubicCurve,
            this::figurePath,
            this::figurePathTranslate,
            this::figurePathScale,
            this::figurePathRotate,
            this::figurePathShear,
            this::figureImage,
            this::figureText
        };
        for (Figure figure : figures) {
            figure.generate();
        }
        close();
    }

    public static void main(String[] args) {
        new GenerateFigures();
    }
}

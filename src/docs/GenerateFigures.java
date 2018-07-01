package docs;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.geom.*;
import org.gannacademy.cdf.graphics.geom.Rectangle;

import java.awt.*;
import java.io.File;

public class GenerateFigures extends DrawingApp {
  private static final String BASE_PATH = "src/org/gannacademy/cdf";

  private static String docs(String subpackage, String name) {
    return BASE_PATH + "/" + subpackage + "/doc-files/" + name + ".png";
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
    }
  }

  public static void main(String[] args) {
    resetDir(BASE_PATH + "/graphics/doc-files");
    resetDir(BASE_PATH + "/graphics/geom/doc-files");
    resetDir(BASE_PATH + "/graphics/ui/doc-files");
    new GenerateFigures();
  }

  @Override
  public void setup() {
    // DrawingApp.png
    super.setup();
    getDrawingPanel().saveAs(docs("graphics", "DrawingApp"));
    getDrawingPanel().clear();

    // Rectangle.png
    Rectangle r = new Rectangle(50, 50, 200, 200 / 1.618, getDrawingPanel());
    Ellipse origin = new Ellipse(48, 48, 4, 4, getDrawingPanel());
    origin.setFillColor(Color.black);
    Font labelFont = new Font("Arial", Font.PLAIN, 12);
    Text
            originLabel = new Text("(x, y)", 0, 0, getDrawingPanel()),
            widthLabel = new Text("← Width →", 0, 0, getDrawingPanel()),
            heightLabel1 = new Text("↑", 0, 0, getDrawingPanel()),
            heightLabel2 = new Text("Height", 0, 0, getDrawingPanel()),
            heightLabel3 = new Text("↓", 0, 0, getDrawingPanel());
    originLabel.setFont(labelFont);
    widthLabel.setFont(labelFont);
    widthLabel.setStrokeColor(new Color(77, 122, 151));
    heightLabel1.setFont(labelFont);
    heightLabel1.setStrokeColor(widthLabel.getStrokeColor());
    heightLabel2.setFont(labelFont);
    heightLabel2.setStrokeColor(heightLabel1.getStrokeColor());
    heightLabel3.setFont(labelFont);
    heightLabel3.setStrokeColor(heightLabel1.getStrokeColor());
    originLabel.setY(originLabel.getHeight() - originLabel.getMaxDescent() + 1);
    r.setOrigin(originLabel.getX() + originLabel.getWidth() / 2, originLabel.getY() + originLabel.getMaxDescent() + origin.getHeight() / 2 + 1);
    origin.setOrigin(r.getX() - origin.getWidth() / 2, r.getY() - origin.getHeight() / 2);
    widthLabel.setOrigin(r.getX() + r.getWidth() / 2 - widthLabel.getWidth() / 2, r.getY() + r.getHeight() + widthLabel.getHeight() + 2);
    heightLabel2.setOrigin(r.getX() + r.getWidth() + 5, r.getY() + r.getHeight() / 2 + (heightLabel2.getHeight() - heightLabel2.getMaxDescent() - heightLabel2.getMaxAscent()) / 2);
    heightLabel1.setOrigin(heightLabel2.getX() + heightLabel2.getWidth() / 2 - heightLabel1.getWidth() / 2, heightLabel2.getY() - heightLabel2.getHeight());
    heightLabel3.setOrigin(heightLabel2.getX() + heightLabel2.getWidth() / 2 - heightLabel3.getWidth() / 2, heightLabel2.getY() + heightLabel3.getHeight());
    BasicStroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, new float[]{3, 3}, 0);
    Line leftBottomHash = new Line(r.getX(), r.getY() + r.getHeight() / 2, r.getX(), widthLabel.getY(), getDrawingPanel()),
            rightBottomHash = new Line(r.getX() + r.getWidth(), r.getY() + r.getHeight() / 2, r.getX() + r.getWidth(), widthLabel.getY(), getDrawingPanel()),
            topRightHash = new Line(r.getX() + r.getWidth() / 2, r.getY(), heightLabel1.getX() + heightLabel1.getWidth(), r.getY(), getDrawingPanel()),
            bottomRightHash = new Line(r.getX() + r.getWidth() / 2, r.getY() + r.getHeight(), heightLabel1.getX() + heightLabel1.getWidth(), r.getY() + r.getHeight(), getDrawingPanel());
    leftBottomHash.setStroke(dashed);
    leftBottomHash.setStrokeColor(widthLabel.getStrokeColor());
    rightBottomHash.setStroke(dashed);
    rightBottomHash.setStrokeColor(widthLabel.getStrokeColor());
    topRightHash.setStroke(dashed);
    topRightHash.setStrokeColor(heightLabel1.getStrokeColor());
    bottomRightHash.setStroke(dashed);
    bottomRightHash.setStrokeColor(heightLabel1.getStrokeColor());
    setSize((int) (heightLabel2.getX() + heightLabel2.getWidth() + 1), (int) (widthLabel.getY() + widthLabel.getMaxDescent()));
    r.removeFromDrawingPanel();
    r.setDrawingPanel(getDrawingPanel());
    getDrawingPanel().saveAs(docs("graphics/geom", "Rectangle"));

    // Ellipse.png
    r.removeFromDrawingPanel();
    Ellipse e = new Ellipse(r.getX(), r.getY(), r.getWidth(), r.getHeight(), getDrawingPanel());
    getDrawingPanel().saveAs(docs("graphics/geom", "Ellipse"));

    // RoundRectangle.png
    e.removeFromDrawingPanel();
    double size = 40;
    e.setDrawingPanel(getDrawingPanel());
    e.setFrame(r.getX() + r.getWidth() - size, r.getY() + r.getHeight() - size, size, size);
    e.setStroke(dashed);
    e.setStrokeColor(new Color(248, 152, 29));
    BasicStroke dashedAlt = new BasicStroke(dashed.getLineWidth(), dashed.getEndCap(), dashed.getLineJoin(), dashed.getMiterLimit(), dashed.getDashArray(), (float) (size / 2));
    Line
            leftArcHash = new Line(e.getX(), e.getY() + e.getHeight() / 2, e.getX(), rightBottomHash.getY2() + originLabel.getHeight(), getDrawingPanel()),
            rightArcHash = new Line(e.getX() + e.getWidth(), e.getY() + e.getHeight() / 2, e.getX() + e.getWidth(), leftArcHash.getY2(), getDrawingPanel()),
            topArcHash = new Line(e.getX() + e.getWidth() / 2, e.getY(), bottomRightHash.getX2() + originLabel.getHeight(), e.getY(), getDrawingPanel()),
            bottomArcHash = new Line(e.getX() + e.getWidth() / 2, bottomRightHash.getY1(), topArcHash.getX2(), bottomRightHash.getY2(), getDrawingPanel());
    leftArcHash.setStroke(dashedAlt);
    leftArcHash.setStrokeColor(e.getStrokeColor());
    rightArcHash.setStroke(dashedAlt);
    rightArcHash.setStrokeColor(e.getStrokeColor());
    topArcHash.setStroke(dashedAlt);
    topArcHash.setStrokeColor(e.getStrokeColor());
    bottomArcHash.setStroke(dashed);
    bottomArcHash.setStrokeColor(e.getStrokeColor());
    rightBottomHash.removeFromDrawingPanel();
    rightBottomHash.setDrawingPanel(getDrawingPanel());
    bottomRightHash.removeFromDrawingPanel();
    bottomRightHash.setDrawingPanel(getDrawingPanel());
    Text
            arcWidthLabel1 = new Text("↔", 0, 0, getDrawingPanel()),
            arcWidthLabel2 = new Text("Arc", 0, 0, getDrawingPanel()),
            arcWidthLabel3 = new Text("Width", 0, 0, getDrawingPanel()),
            arcHeightLabel = new Text("↕ Arc Height", 0, 0, getDrawingPanel());
    arcWidthLabel1.setFont(labelFont);
    arcWidthLabel1.setStrokeColor(e.getStrokeColor());
    arcWidthLabel1.setOrigin(e.getX() + e.getWidth() / 2 - arcWidthLabel1.getWidth() / 2, leftBottomHash.getY2());
    arcWidthLabel2.setFont(labelFont);
    arcWidthLabel2.setStrokeColor(e.getStrokeColor());
    arcWidthLabel2.setOrigin(e.getX() + e.getWidth() / 2 - arcWidthLabel2.getWidth() / 2, arcWidthLabel1.getY() + arcWidthLabel2.getHeight());
    arcWidthLabel3.setFont(labelFont);
    arcWidthLabel3.setStrokeColor(e.getStrokeColor());
    arcWidthLabel3.setOrigin(e.getX() + e.getWidth() / 2 - arcWidthLabel3.getWidth() / 2, arcWidthLabel2.getY() + arcWidthLabel3.getHeight());
    arcHeightLabel.setFont(labelFont);
    arcHeightLabel.setStrokeColor(e.getStrokeColor());
    arcHeightLabel.setOrigin(heightLabel3.getX() + originLabel.getHeight(), r.getY() + r.getHeight() - e.getHeight() / 2 + (arcHeightLabel.getHeight() - arcHeightLabel.getMaxDescent() - arcHeightLabel.getMaxAscent()) / 2);
    double heightLabelOffset = 1;
    heightLabel1.setOrigin(heightLabel1.getX(), heightLabel1.getY() + heightLabelOffset);
    heightLabel2.setOrigin(heightLabel2.getX(), heightLabel2.getY() + heightLabelOffset);
    heightLabel3.setOrigin(heightLabel3.getX(), heightLabel3.getY() + heightLabelOffset);
    RoundRectangle rr = new RoundRectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight(), size, size, getDrawingPanel());
    setSize((int) (arcHeightLabel.getX() + arcHeightLabel.getWidth() + 1), (int) (arcWidthLabel3.getY() + arcWidthLabel3.getMaxDescent()));
    getDrawingPanel().saveAs(docs("graphics/geom", "RoundRectangle"));

    // Arc.png
    getDrawingPanel().clear();
    originLabel.setDrawingPanel(getDrawingPanel());
    origin.setDrawingPanel(getDrawingPanel());
    leftBottomHash.setDrawingPanel(getDrawingPanel());
    rightBottomHash.setDrawingPanel(getDrawingPanel());
    topRightHash.setDrawingPanel(getDrawingPanel());
    bottomRightHash.setDrawingPanel(getDrawingPanel());
    widthLabel.setDrawingPanel(getDrawingPanel());
    heightLabel1.setDrawingPanel(getDrawingPanel());
    heightLabel2.setDrawingPanel(getDrawingPanel());
    heightLabel3.setDrawingPanel(getDrawingPanel());
    heightLabel1.setOrigin(heightLabel1.getX(), heightLabel1.getY() - heightLabelOffset);
    heightLabel2.setOrigin(heightLabel2.getX(), heightLabel2.getY() - heightLabelOffset);
    heightLabel3.setOrigin(heightLabel3.getX(), heightLabel3.getY() - heightLabelOffset);
    double start = 90, arc = 360 - start;
    Arc startAngle = new Arc(r.getX() + r.getWidth() / 4, r.getY() + r.getHeight() / 4, r.getWidth() / 2, r.getHeight() / 2, 0, start, getDrawingPanel());
    startAngle.setStroke(dashed);
    startAngle.setStrokeColor(new Color(230, 100, 80));
    Path startArrow = new Path(getDrawingPanel());
    double
            stArrX = r.getX() + r.getWidth() / 2,
            stArrY = r.getY() + r.getHeight() / 4,
            arrowSize = 8;
    startArrow.moveTo(stArrX, stArrY);
    startArrow.lineTo(stArrX + arrowSize, stArrY - arrowSize / 2);
    startArrow.lineTo(stArrX + arrowSize, stArrY + arrowSize / 2);
    startArrow.setFillColor(startAngle.getStrokeColor());
    startArrow.setStrokeColor(Drawable.TRANSPARENT);
    Arc arcLength = new Arc(r.getX() + r.getWidth() / 6, r.getY() + r.getHeight() / 6, r.getWidth() * 2 / 3, r.getHeight() * 2 / 3, start, arc, getDrawingPanel());
    arcLength.setStroke(dashed);
    arcLength.setStrokeColor(new Color(80, 210, 80));
    Path arcArrow = new Path(getDrawingPanel());
    double aArrX = r.getX() + r.getWidth() * 5 / 6, aArrY = r.getY() + r.getHeight() / 2;
    arcArrow.moveTo(aArrX, aArrY);
    arcArrow.lineTo(aArrX + arrowSize / 2 - 1, aArrY + arrowSize + 1);
    arcArrow.lineTo(aArrX - arrowSize / 2 - 1, aArrY + arrowSize - 1);
    arcArrow.setFillColor(arcLength.getStrokeColor());
    arcArrow.setStrokeColor(Drawable.TRANSPARENT);
    Text
            startLabel = new Text("Start", startArrow.getX() + arrowSize, startArrow.getY(), getDrawingPanel()),
            arcLabel = new Text("Extent", 0, 0, getDrawingPanel());
    startLabel.setFont(labelFont);
    startLabel.setStrokeColor(startAngle.getStrokeColor());
    arcLabel.setFont(labelFont);
    arcLabel.setStrokeColor(arcLength.getStrokeColor());
    arcLabel.setOrigin(arcArrow.getX() - arrowSize - arcLabel.getWidth(), arcArrow.getY() + arcLabel.getHeight());
    Arc a = new Arc(r.getX(), r.getY(), r.getWidth(), r.getHeight(), start, arc, getDrawingPanel());
    setSize((int) (heightLabel2.getX() + heightLabel2.getWidth() + 1), (int) (widthLabel.getY() + widthLabel.getMaxDescent()));
    getDrawingPanel().saveAs(docs("graphics/geom", "Arc"));

    // CubicCurve.png
    getDrawingPanel().clear();
    Text
            label1 = originLabel,
            label2 = new Text("Point 2", 0, 0, getDrawingPanel()),
            cLabel1 = new Text("Control Point 1", 0, 0, getDrawingPanel()),
            cLabel2 = new Text("Control Point 2", 0, 0, getDrawingPanel());
    label1.setDrawingPanel(getDrawingPanel());
    label1.setText("Point 1");
    label1.setOrigin(1, label1.getY());
    label2.setStrokeColor(label1.getStrokeColor());
    label2.setFont(labelFont);
    cLabel1.setStrokeColor(widthLabel.getStrokeColor());
    cLabel1.setFont(labelFont);
    cLabel2.setStrokeColor(cLabel1.getStrokeColor());
    cLabel2.setFont(labelFont);
    r.setFrame(label1.getX() + label1.getWidth() / 2, r.getY(), r.getWidth(), r.getHeight());
    double
            cx1 = r.getX() + r.getWidth() * 0.8,
            cy1 = r.getY() + r.getHeight() * 0.1,
            cx2 = r.getX() + r.getWidth() * 0.6,
            cy2 = r.getY() + r.getHeight() * 0.95;
    label2.setOrigin(r.getX() + r.getWidth() - label2.getWidth() / 2, r.getY() + r.getHeight() + origin.getHeight() / 2 + label2.getHeight());
    cLabel1.setOrigin(cx1 - cLabel1.getWidth() / 2, cy1 - origin.getHeight() / 2 - cLabel1.getMaxDescent());
    cLabel2.setOrigin(cx2 - cLabel2.getWidth() / 2, cy2 + origin.getHeight() / 2 + cLabel2.getHeight());
    Line
            ctrl1 = new Line(r.getX(), r.getY(), cx1, cy1, getDrawingPanel()),
            ctrl2 = new Line(r.getX() + r.getWidth(), r.getY() + r.getHeight(), cx2, cy2, getDrawingPanel());
    ctrl1.setStroke(dashed);
    ctrl1.setStrokeColor(cLabel1.getStrokeColor());
    ctrl2.setStroke(dashed);
    ctrl2.setStrokeColor(cLabel2.getStrokeColor());
    Ellipse
            p1 = origin,
            c1 = new Ellipse(cx1 - p1.getWidth() / 2, cy1 - p1.getHeight() / 2, p1.getWidth(), p1.getHeight(), getDrawingPanel()),
            c2 = new Ellipse(cx2 - p1.getWidth() / 2, cy2 - p1.getHeight() / 2, p1.getWidth(), p1.getHeight(), getDrawingPanel()),
            p2 = new Ellipse(r.getX() + r.getWidth() - p1.getWidth() / 2, r.getY() + r.getHeight() - p1.getHeight() / 2, p1.getWidth(), p1.getHeight(), getDrawingPanel());
    p1.setDrawingPanel(getDrawingPanel());
    p1.setOrigin(label1.getX() + label1.getWidth() / 2 - p1.getWidth() / 2, p1.getY());
    c1.setStrokeColor(ctrl1.getStrokeColor());
    c1.setFillColor(Color.WHITE);
    c2.setStrokeColor(c1.getStrokeColor());
    c2.setFillColor(c1.getFillColor());
    p2.setStrokeColor(origin.getStrokeColor());
    p2.setFillColor(origin.getFillColor());
    CubicCurve cc = new CubicCurve(r.getX(), r.getY(), cx1, cy1, cx2, cy2, r.getX() + r.getWidth(), r.getY() + r.getHeight(), getDrawingPanel());
    setSize((int) (label2.getX() + label2.getWidth() + 1), (int) (label2. getY() + label2.getMaxDescent() + 1));
    getDrawingPanel().saveAs(docs("graphics/geom", "CubicCurve"));

    // Line.png
    getDrawingPanel().clear();
    p1.setDrawingPanel(getDrawingPanel());
    p2.setDrawingPanel(getDrawingPanel());
    label1.setDrawingPanel(getDrawingPanel());
    label2.setDrawingPanel(getDrawingPanel());
    Line l = new Line (r.getX(), r.getY(), r.getX() + r.getWidth(), r.getY() + r.getHeight(), getDrawingPanel());
    getDrawingPanel().saveAs(docs("graphics/geom", "Line"));

    // QuadCurve.png
    l.removeFromDrawingPanel();
    ctrl1.setDrawingPanel(getDrawingPanel());
    ctrl2.setDrawingPanel(getDrawingPanel());
    cLabel1.setDrawingPanel(getDrawingPanel());
    c1.setDrawingPanel(getDrawingPanel());
    ctrl2.setLine(p2.getX(), p2.getY(), cx1, cy1);
    cLabel1.setText("Control Point");
    cLabel1.setOrigin(c1.getX() + c1.getWidth() / 2 - cLabel1.getWidth() / 2, cLabel1.getY());
    QuadCurve qc = new QuadCurve(r.getX(), r.getY(), cx1, cy1, r.getX() + r.getWidth(), r.getY() + r.getHeight(), getDrawingPanel());
    getDrawingPanel().saveAs(docs("graphics/geom", "QuadCurve"));
  }
}

package example.shapes;

import org.gannacademy.cdf.graphics.geom.QuadCurve;

public class QuadCurveTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new QuadCurve(width, height, width * 1.5, height * 2, width * 2, height, getDrawingPanel());
    }

    public static void main(String[] args) {
        new QuadCurveTest();
    }
}

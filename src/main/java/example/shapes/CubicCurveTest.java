package example.shapes;

import org.gannacademy.cdf.graphics.geom.CubicCurve;
import org.gannacademy.cdf.graphics.geom.QuadCurve;

public class CubicCurveTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new CubicCurve(width, height * 2, width, height, width * 2, height * 2, width * 2, height, getDrawingPanel());
    }

    @Override
    protected void loop() {
        super.loop();
    }

    public static void main(String[] args) {
        new CubicCurveTest();
    }
}

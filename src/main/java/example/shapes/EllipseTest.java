package example.shapes;

import org.gannacademy.cdf.graphics.geom.Ellipse;

public class EllipseTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new Ellipse(width, height, width, height, getDrawingPanel());
    }

    public static void main(String[] args) {
        new EllipseTest();
    }
}

package example.shapes;

import org.gannacademy.cdf.graphics.geom.RoundRectangle;

public class RoundRectangleTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new RoundRectangle(width, height, width, height, width / 3.0, height / 3.0, getDrawingPanel());
    }

    public static void main(String[] args) {
        new RoundRectangleTest();
    }
}

package example.shapes;

import org.gannacademy.cdf.graphics.geom.Rectangle;

public class RectangleTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new Rectangle(width, height, width, height, getDrawingPanel());
    }

    public static void main(String[] args) {
        new RectangleTest();
    }
}

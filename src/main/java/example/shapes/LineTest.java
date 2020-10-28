package example.shapes;

import org.gannacademy.cdf.graphics.geom.Line;

public class LineTest extends ShapeTest{

    @Override
    protected void setup() {
        super.setup();
        shape = new Line(width, height, width * 2, height * 2, getDrawingPanel());
    }

    public static void main(String[] args) {
        new LineTest();
    }
}

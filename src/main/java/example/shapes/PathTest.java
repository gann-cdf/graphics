package example.shapes;

import org.gannacademy.cdf.graphics.geom.Path;

public class PathTest extends ShapeTest {

    protected double dTheta;

    @Override
    protected void setup() {
        super.setup();
        Path path = new Path(getDrawingPanel());
        path.moveTo(width, height);
        path.lineTo(width, height * 2);
        path.curveTo(width * 2, height * 2, width, height, width * 2, height);
        path.quadTo(width, height * 2, width * 2, height * 2);
        path.closePath();
        shape = path;
        dTheta = Math.PI / 180.0;
    }

    @Override
    protected void loop() {
        ((Path) shape).rotate(dTheta, shape.getX() + shape.getWidth() / 2.0, shape.getY() + shape.getHeight() / 2.0);
        super.loop();
    }

    public static void main(String[] args) {
        new PathTest();
    }
}

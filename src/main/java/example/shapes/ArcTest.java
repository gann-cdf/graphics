package example.shapes;

import org.gannacademy.cdf.graphics.geom.Arc;

public class ArcTest extends ShapeTest {

    protected double dAngle;
    protected boolean isStart;

    @Override
    protected void setup() {
        super.setup();
        shape = new Arc(width, height, width, height, 0, 360, getDrawingPanel());
        dAngle = 1;
        isStart = true;
    }

    @Override
    protected void loop() {
        Arc arc = (Arc) shape;
        if (isStart) {
            arc.setAngleExtent(arc.getAngleExtent() - dAngle);
            arc.setAngleStart(arc.getAngleStart() + dAngle);
            if (arc.getAngleStart() > 359) {
                dAngle = -dAngle;
            }
            if (arc.getAngleStart() < 1) {
                isStart = false;
            }
        } else {
            arc.setAngleExtent(arc.getAngleExtent() + dAngle);
            if (arc.getAngleExtent() < 1) {
                dAngle = -dAngle;
            }
            if (arc.getAngleExtent() > 359) {
                isStart = true;
            }
        }
        super.loop();
    }

    public static void main(String[] args) {
        new ArcTest();
    }
}

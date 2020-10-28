package example.shapes;

import org.gannacademy.cdf.graphics.Image;

public class ImageTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new Image("/java-logo.png", width, height, getDrawingPanel());
        double ratio = shape.getWidth() / shape.getHeight();
        if (shape.getWidth() > shape.getHeight()) {
            shape.setWidth(width);
            shape.setHeight(ratio * width);
        } else {
            shape.setWidth(ratio * height);
            shape.setHeight(height);
        }
    }

    public static void main(String[] args) {
        new ImageTest();
    }
}

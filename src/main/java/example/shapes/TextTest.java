package example.shapes;

import org.gannacademy.cdf.graphics.Text;

public class TextTest extends ShapeTest {

    @Override
    protected void setup() {
        super.setup();
        shape = new Text("Gann Graphics", width, height, getDrawingPanel());
        shape.setWidth(width);
    }

    public static void main(String[] args) {
        new TextTest();
    }
}

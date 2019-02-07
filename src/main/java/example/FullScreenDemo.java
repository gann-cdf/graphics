package example;

import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;

public class FullScreenDemo extends AppWindow {
    public FullScreenDemo() {
        super("Full Screen", true);
    }

    protected void setup() {
        getDrawingPanel().setBackground(Color.BLACK);
        (new Text(getDrawingPanel().getWidth() + " × " + getDrawingPanel().getHeight() + " pixels", 100, 100, getDrawingPanel())).setStrokeColor(Color.WHITE);
    }

    public static void main(String[] args) {
        new FullScreenDemo();
    }
}

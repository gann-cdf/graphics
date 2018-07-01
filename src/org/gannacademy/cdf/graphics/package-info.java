/**
 * <h3>Sample Program</h3>
 *
 * <pre>
import org.gannacademy.cdf.graphics.geom.*;
import org.gannacademy.cdf.graphics.ui.*;

import java.awt.Color;

// Build your app as an extension of the AppWindow class (which sets up the window and drawing panel for you)
public class DrawingApp extends AppWindow {

  // Override the setup() method to define your drawing
  &#064;Override
  protected void setup() {
    // draw a blue rectangle with a thick outline
    Rectangle r = new Rectangle(200, 75, 200, 200, getDrawingPanel());
    r.setFillColor(Color.BLUE);

    // draw a red, transparent circle with a thick outline
    Ellipse e = new Ellipse(250, 125, 200, 200, getDrawingPanel());
    e.setFillColor(new Color(255, 100, 100, 200));
  }

  // Start your app by instantiating it in your main method
  public static void main(String[] args) {
     new DrawingApp();
  }
}
</pre>
 *
 * <p><img src="doc-files/DrawingApp.png" alt="DrawingApp output"></p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
package org.gannacademy.cdf.graphics;
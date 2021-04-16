package org.gannacademy.cdf.graphics.ui;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Extendable window controller for {@link DrawingPanel} objects</p>
 *
 * <p>Extend this class to create your own drawings. Override the abstract method {@link #setup()} to make your drawing
 * instructions. The recommended approach is to create a new Java class file and to type exactly this (replacing
 * {@code MyDrawingApp} with your preferred name, of course):</p>
 *
 * <pre>
 *   public class MyDrawingApp extends AppWindow {}
 * </pre>
 *
 * <p>This will generate a number of errors in your IDE. Go ahead and let the IDE fix them automagically. The IDE will
 * import the AppWindow class, and then create a method stub for {@code setup()}, resulting in something like this:</p>
 *
 * <pre>
 *   import org.gannacademy.cdf.graphics.ui.*;
 *
 *   public class MyDrawingApp extends AppWindow {
 *     &#064;Override
 *     public void setup() {
 *
 *     }
 *   }
 * </pre>
 *
 * <p>Instantiate a {@code MyDrawingApp} object to create a window containing your drawing.</p>
 *
 * <p>Additional overrideable methods include {@link #loop()} and {@link #done()}. {@code loop()} will be
 * called repeatedly (inside a loop!) while {@code done()} returns {@code false} &mdash; when {@code done()} returns
 * {@code true}, the control loop ends (although the application will continue running until the window is closed).</p>
 *
 * @author <a href="https://github.com/gann-cdf/graphics/issues" target="_blank">Seth Battis</a>
 */
public abstract class AppWindow extends JFrame {
    public static final String DEFAULT_TITLE = "Gann Graphics App";
    public static final boolean DEFAULT_FULLSCREEN = false;
    public static final long DEFAULT_REPAINT_DELAY = 10;

    private DrawingPanel drawingPanel;
    private boolean threadStarted = false;

    /**
     * Construct a new {@link DrawingPanel} in a window with the default title
     */
    public AppWindow() {
        this(DEFAULT_TITLE, DEFAULT_FULLSCREEN, DEFAULT_REPAINT_DELAY);
    }

    /**
     * Construct a new {@link DrawingPanel} in a window with a custom name
     *
     * @param title The title of the window (shown in the draggable titlebar)
     */
    public AppWindow(String title) {
        this(title, DEFAULT_FULLSCREEN, DEFAULT_REPAINT_DELAY);
    }

    public AppWindow(String title, boolean isFullScreen) {
        this(title, isFullScreen, DEFAULT_REPAINT_DELAY);
    }

    /**
     * <p>Construct a new {@link DrawingPanel} in a window</p>
     *
     * <p>Full screen windows default to the main display.</p>
     *
     * @param title        for the window
     * @param isFullScreen whether or not the window is framed or full screen
     */
    public AppWindow(String title, boolean isFullScreen, long repaintDelay) {
        super(title);
        AppWindow self = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                drawingPanel = new DrawingPanel();
                add(drawingPanel);
                if (isFullScreen) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                    setUndecorated(true);
                    DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode();
                    setSize(display.getWidth(), display.getHeight());
                }
                pack();
                setup();
                setLocationRelativeTo(null);
                setVisible(true);
                (new Repainter(self, repaintDelay)).execute();
                (new Animator(self)).execute();
            }
        });
    }

    private void repaintOnEDT() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }

    private static class Animator extends SwingWorker<Void,Void> {

        AppWindow window;

        Animator(AppWindow window) {
            this.window = window;
        }

        @Override
        protected Void doInBackground() throws Exception {
            while(!window.done()) {
                window.loop();
                window.repaintOnEDT();
            }
            return null;
        }
    }

    private static class Repainter extends SwingWorker<Void,Void> {
        AppWindow window;
        long delay;

        Repainter(AppWindow window, long delay) {
            this.window = window;
            this.delay = Math.max(1, delay);
        }

        @Override
        protected Void doInBackground() throws Exception {
            //noinspection InfiniteLoopStatement
            while (true) {
                window.repaintOnEDT();
                //noinspection BusyWait
                Thread.sleep(delay);
            }
        }
    }
    /**
     * Set the size of the window
     *
     * @param width  in pixels
     * @param height in pixels
     */
    public void setSize(int width, int height) {
        drawingPanel.setPreferredSize(new Dimension(width, height));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Pause the control loop
     *
     * @param delay in milliseconds
     */
    protected void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Override this method to define your drawing</p>
     *
     * <p>This method is called before the window is made visible. All drawing components that need to be rendered at the
     * start of the program run should be defined in this method.</p>
     */
    protected abstract void setup();

    /**
     * <p>Override this method to update drawings in the control loop</p>
     *
     * <p>This method represents a <i>single</i> iteration of the control loop. Care should be taken to avoid writing
     * blocking code in this method (e.g. {@code for} loops designed to animate a single object) &mdash; rather, drawing
     * components should be adjusted based on the values of instance variables, whose values are changed incrementally
     * in each call to this method. For example:</p>
     *
     * <pre>
     *   private double x, y;
     *   private Rectangle r;
     *
     *   // setup() and other methods&hellip;
     *
     *   &#064;Override
     *   public void loop() {
     *     x += 1;
     *     y = 20 * Math.sin(x * 20);
     *     r.moveTo(x, y);
     *   }
     * </pre>
     *
     * <p>The above code animates a rectangle moving elegantly in a sine wave (scaled up 20&times;)across the window.</p>
     */
    protected void loop() {
    }

    /**
     * <p>Override this method to set the condition that ends the control loop</p>
     *
     * <p>By default, this method will always return {@code false}, so that the control loop runs indefinitely.</p>
     *
     * @return {@code true} if the control loop should end, {@code false} otherwise
     */
    protected boolean done() {
        return false;
    }

    /**
     * <p>Access the {@link DrawingPanel} object contained in this window</p>
     *
     * <p>All drawing instructions require a drawing panel on which to execute them. This is the default drawing panel.</p>
     *
     * @return The drawing panel in this window
     */
    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }
}

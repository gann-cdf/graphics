package org.gannacademy.cdf.graphics.curriculum;

import org.gannacademy.cdf.graphics.Drawable;
import org.gannacademy.cdf.graphics.geom.Rectangle;
import org.gannacademy.cdf.graphics.ui.AppWindow;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

import java.awt.*;

/**
 * <p>A visual representation of an array being sorted.</p>
 *
 * <p>This class is meant to be extended with a specific sort algorithm. For the visualization to work, the extending
 * subclass should make use of a number of inherited methods and data fields:</p>
 *
 * <ul>
 * <li>{@link #list} is an array of {@code int} that contains the data being sorted.</li>
 * <li>{@link #finger} is an {@code int} that represents the current position being examined in the array by the sorting algorithm.</li>
 * <li>{@link #swap(int, int)} is a method that swaps two elements of the array.</li>
 * <li>{@link #sleep(long)} allows the extending class to control the delay between steps (and thus the speed of the animation).</li>
 * </ul>
 *
 * <p>For example, a simple extending class might be:</p>
 *
 * <pre>
 * public class BubbleSort extends VisualSort() {
 *     public BubbleSort(DrawingPanel drawingPanel) {
 *         super(drawingPanel);
 *     }
 *
 *     public void sort() {
 *         for (int pass = 0; pass &lt; list.length; pass++) {
 *             for (finger = 0; finger &lt; list.length - 1; finger++) {
 *                 if (list[finger] &gt; list[finger + 1]) {
 *                     swap(finger, finger + 1);
 *                 }
 *                 sleep(25);
 *             }
 *         }
 *     }
 * }
 * </pre>
 *
 * <p>This subclass of VisualSort could be animated in a simple AppWindow:</p>
 *
 * <pre>
 * public class Visualization extends AppWindow() {
 *     private VisualSort bubbleSort;
 *
 *     protected void setup() {
 *         bubbleSort = new BubbleSort(getDrawingPanel());
 *         bubbleSort.begin(); // begins the sort
 *     }
 *
 *     &#064;Override
 *     protected void loop() {
 *         bubbleSort.update();
 *         sleep(5);
 *     }
 *
 *     public static void main(String[] args) {
 *         new Visualization();
 *     }
 * }
 * </pre>
 *
 * <p>Note that the {@link #sort()} method is iterating over the {@link #list} array using {@link #finger} as
 * the array counter. The {@link #sleep(long)} delay after each comparison slows the algorithm down so that the
 * {@link #update()} method can be invoked to periodically display the current state of the array in your
 * animation loop in your {@link AppWindow}.</p>
 *
 * @author <a href="http://github.com/gann-cdf/graphics/issues">Seth Battis</a>
 */
public abstract class VisualSort implements Runnable {
    /**
     * A list index value that will prevent that index from being highlighted
     */
    protected static final int INVALID = -1;

    /**
     * The number of frames to leave the positions of a swap call highlighted
     */
    protected static int swapVisualLifetime = 5;

    /**
     * The color of a normal list element
     */
    protected static Color elementColor = Color.BLUE;

    /**
     * The color of the list element indexed by {@link #finger}
     */
    protected static Color fingerColor = Color.RED;

    /**
     * The color of the list elements affected by the most recent call to {@link #swap(int, int)}
     */
    protected static Color swapColor = Color.YELLOW;

    /**
     * The list of data to be sorted
     */
    protected int[] list;

    /**
     * A variable to be used as the list index if the finger is to be highlighted visually. If this field is
     * set to {@link #INVALID} it will not be highlighted.
     */
    protected int finger;

    /**
     * The list indices most recently swapped by {@link #swap(int, int)}
     */
    private int left, right;

    /**
     * The number of frames since a swap was performed
     */
    private int swapVisualCount;

    /**
     *
     */
    private boolean hasBegun;

    /**
     * The visuals representing the list elements
     */
    protected Rectangle[] visuals;
    private DrawingPanel drawingPanel;

    /**
     * <p>Construct a new VisualSort instance with default parameters</p>
     *
     * @param drawingPanel The DrawingPanel on which to display the VisualSort
     */
    public VisualSort(DrawingPanel drawingPanel) {
        this(100, drawingPanel);
    }

    /**
     * <p>Construct a new VisualSort instance with a specified number of elements</p>
     *
     * @param elementCount The number of elements in the list
     * @param drawingPanel The DrawingPanel on which to display the VisualSort
     */
    public VisualSort(int elementCount, DrawingPanel drawingPanel) {
        this(elementCount, false, drawingPanel);
    }

    /**
     * <p>Construct a new VisualSort instance with a specified number of elements, which may also be unique</p>
     *
     * @param elementCount   The number of elements in the list
     * @param uniqueElements Whether or not the elements should be unique (or will there be repeat values)
     * @param drawingPanel   The DrawingPanel on which to display the VisualSort
     */
    public VisualSort(int elementCount, boolean uniqueElements, DrawingPanel drawingPanel) {
        hasBegun = false;
        swapVisualCount = 0;
        finger = INVALID;
        resetSwapVisual();

        list = new int[elementCount];
        if (uniqueElements) {
            initializeWithUniqueElements();
        } else {
            initializeWithRandomElements();
        }

        this.drawingPanel = drawingPanel;
        visuals = new Rectangle[elementCount];
        for (int i = 0; i < visuals.length; i++) {
            visuals[i] = new Rectangle(0, 0, 0, 0, drawingPanel);
            visuals[i].setStroke(Drawable.NO_STROKE);
            visuals[i].setFillColor(elementColor);
        }

        new Thread(this).start();
    }

    /**
     * Disable the highlighting of the swap indices
     */
    private void resetSwapVisual() {
        swapVisualCount++;
        if (swapVisualCount == swapVisualLifetime) {
            left = INVALID;
            right = INVALID;
        }
    }

    /**
     * Initialize the list with random non-unique elements
     */
    private void initializeWithRandomElements() {
        for (int i = 0; i < list.length; i++) {
            list[i] = (int) (Math.random() * list.length);
        }
    }

    /**
     * Initialize the list with unique elements and then shuffle them
     */
    private void initializeWithUniqueElements() {
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }
        for (int i = 0; i < 7; i++) {
            shuffle();
        }
    }

    /**
     * Shuffle the list elements by randomly swapping them
     */
    private void shuffle() {
        for (int i = 0; i < list.length; i++) {
            swap((int) (Math.random() * list.length), (int) (Math.random() * list.length));
        }
    }

    /**
     * Swap the values of two list elements
     *
     * @param a The index of one element to be swapped
     * @param b The index of the second element to be swapped
     */
    protected synchronized void swap(int a, int b) {
        swapVisualCount = 0;
        left = a;
        right = b;
        int temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }

    /**
     * Begin the animation of the sort algorithm
     */
    public void begin() {
        hasBegun = true;
    }

    /**
     * Pause execution of the sort algorithm
     *
     * @param millis The number of milliseconds to pause
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Begin the sorting thread
     *
     * @deprecated This should not be called manually
     */
    public void run() {
        while (!hasBegun) {
            sleep(10);
        }
        sort();
    }

    /**
     * The sorting algorithm to be animated
     */
    protected abstract void sort();

    /**
     * Update the visual display of the list elements to reflect the progress of the sorting algorithm
     */
    public synchronized void update() {
        double
                width = (double) (drawingPanel.getWidth() - 1) / (double) list.length,
                height = (double) (drawingPanel.getHeight() - 2) / (double) list.length;
        for (int i = 0; i < list.length; i++) {
            visuals[i].setLocation(i * width + 1, drawingPanel.getHeight() - 1 - list[i] * height);
            visuals[i].setWidth(Math.max(width - 1, width * 0.5));
            visuals[i].setHeight(list[i] * height);
            visuals[i].setFillColor(elementColor);
        }
        if (left >= 0 && left < list.length) {
            visuals[left].setFillColor(swapColor);
        }
        if (right >= 0 && right < list.length) {
            visuals[right].setFillColor(swapColor);
        }
        resetSwapVisual();
        if (finger >= 0 && finger < list.length) {
            visuals[finger].setFillColor(fingerColor);
        }
    }
}
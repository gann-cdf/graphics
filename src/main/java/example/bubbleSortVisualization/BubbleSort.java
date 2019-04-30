package example.bubbleSortVisualization;

import org.gannacademy.cdf.graphics.curriculum.VisualSort;
import org.gannacademy.cdf.graphics.ui.DrawingPanel;

public class BubbleSort extends VisualSort {
    /**
     * Construct BubbleSort using default parameters
     *
     * @param drawingPanel The DrawingPanel on which to display the sort
     */
    public BubbleSort(DrawingPanel drawingPanel) {
        super(1000, drawingPanel);
    }

    /**
     * A lazy implementation of the Bubble Sort algorithm
     */
    protected void sort() {
        for (int pass = 0; pass < list.length; pass++) {
            for (finger = 0; finger < list.length - 1; finger++) {
                if (list[finger] > list[finger + 1]) {
                    swap(finger, finger + 1);
                }
                sleep(2); // ideally this delay is a multiple of the animation frame delay
            }
        }
    }
}

package example.bubbleSortVisualization;

import org.gannacademy.cdf.graphics.curriculum.VisualSort;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Display a VisualSort visualization of Bubble Sort (click to start)
 */
public class Visualizer extends AppWindow implements MouseListener {
    private VisualSort bubbleSort;
    private boolean started;

    protected void setup() {
        bubbleSort = new BubbleSort(getDrawingPanel());
        started = false;
        getDrawingPanel().addMouseListener(this);
        getDrawingPanel().requestFocus();
    }

    @Override
    protected void loop() {
        bubbleSort.update();
        sleep(10);
    }

    public static void main(String[] args) {
        new Visualizer();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!started) {
            started = true;
            bubbleSort.begin();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

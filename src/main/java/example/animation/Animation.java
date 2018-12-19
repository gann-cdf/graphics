package example.animation;

import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.util.ArrayList;
import java.util.List;

public class Animation extends AppWindow {
    List<Bubble> bubbles;

    @Override
    protected void setup() {
        bubbles = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            bubbles.add(new Bubble(getDrawingPanel()));
        }
    }

    @Override
    protected void loop() {
        long start = System.currentTimeMillis();
        for (Bubble b : bubbles) {
            b.step();
        }

        List<Bubble> trash = new ArrayList<>();
        for (Bubble b : bubbles) {
            if (!trash.contains(b)) {
                for (Bubble c : bubbles) {
                    if (b != c && !trash.contains(c) && b.intersects(c)) {
                        b.absorb(c);
                        trash.add(c);
                    }
                }
            }
        }
        for (Bubble b : trash) {
            bubbles.remove(b);
            b.close();
        }
        sleep(50 - (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        new Animation();
    }
}

package org.gannacademy.cdf.graphics.example;

import org.gannacademy.cdf.graphics.Text;
import org.gannacademy.cdf.graphics.ui.AppWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FullScreenDemo extends AppWindow implements KeyListener {
    public FullScreenDemo() {
        super("Full Screen", true);
    }

    protected void setup() {
        getDrawingPanel().setBackground(Color.BLACK);
        (new Text(getDrawingPanel().getWidth() + " × " + getDrawingPanel().getHeight() + " pixels", 100, 100, getDrawingPanel())).setStrokeColor(Color.WHITE);
        (new Text("Esc to exit", 100, 200, getDrawingPanel())).setStrokeColor(Color.WHITE);
        getDrawingPanel().addKeyListener(this);
        getDrawingPanel().requestFocus();
    }

    public static void main(String[] args) {
        new FullScreenDemo();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

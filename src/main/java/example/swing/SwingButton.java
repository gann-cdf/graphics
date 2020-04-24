package example.swing;

import org.gannacademy.cdf.graphics.ui.AppWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements ActionListener so that it can receive button action events
 */
public class SwingButton extends AppWindow implements ActionListener {

    private List<Ball> balls;

    // Swing is the "modern" Java UI system (as opposed to AWT) and all its components are prefixed "J"
    private JButton addButton;

    // best practice is to avoid retyping literals (to avoid fat-fingering them)
    private static final String ADD_COMMAND = "add";

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == ADD_COMMAND) {
            balls.add(new Ball(getDrawingPanel()));
        }
    }

    @Override
    protected void setup() {
        balls = new ArrayList<>();
        balls.add(new Ball(getDrawingPanel()));

        // create a new button with the ext "Add Ball"
        addButton = new JButton("Add Ball");

        // associate a keyboard shortcut with the button (Alt+B)
        addButton.setMnemonic(KeyEvent.VK_B);

        // assign a "command" keyword to the button (in case there are many buttons being handled)
        addButton.setActionCommand(ADD_COMMAND);

        // assign ourselves as the actionlistener for this button
        addButton.addActionListener(this);

        /*
         * Position the button on the screen.
         *
         * Note that we use the button's preferred size (which is calculated based on its contents), rather than its
         * width, since it has _no width_, having not yet been added to the layout!
         *
         * Note also that we are turning off the layout manager (setLayout(null)) to allow for absolute positioning
         */
        addButton.setBounds(
                (getDrawingPanel().getWidth() - addButton.getPreferredSize().width) / 2,
                (getDrawingPanel().getHeight() - addButton.getPreferredSize().height) / 2,
                addButton.getPreferredSize().width,
                addButton.getPreferredSize().height
        );
        getDrawingPanel().setLayout(null);

        // actually add the button to the Swing UI hierarchy on top of the drawing panel
        getDrawingPanel().add(addButton);
    }

    @Override
    protected void loop() {
        for (Ball ball : balls) {
            ball.move();
        }
        sleep(10);
    }

    public static void main(String[] args) {
        new SwingButton();
    }
}

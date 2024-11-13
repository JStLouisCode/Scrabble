import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BoardPressed implements ActionListener {
    private JButton button;
    private View view;

    public BoardPressed(View view, JButton button) {
        this.view = view;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton lastPressedButton = new JButton();
        lastPressedButton = view.getLastPressedButton();

        if (lastPressedButton != null) {
            lastPressedButton.setBackground(Color.RED); // Turn the last button red
        }
        button.setBackground(Color.GREEN); // Turn the current button green
        view.setLastPressedButton(button); // Update the last pressed button
    }

}


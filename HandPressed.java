//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.*;
//
//class HandPressed implements ActionListener {
//    private final JButton button;
//    private final View view;
//
//    public HandPressed(View view, JButton button) {
//        this.view = view;
//        this.button = button;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        JButton lastPressedButton = view.getLastPressedButton();
//
//        // Reset the color of the previously pressed button in the hand
//        if (lastPressedButton != null) {
//            lastPressedButton.setBackground(null);
//        }
//
//        // Set the current button's background color to green
//        button.setBackground(Color.GREEN);
//
//        // Update the last pressed button
//        view.setLastPressedButton(button);
//    }
//}
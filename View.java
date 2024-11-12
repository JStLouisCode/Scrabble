import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
 class View {
     private JButton[][] buttons;
     private JPanel handPanel;
     private Game model;

     public View(Game model) {
         this.model = model;

         buttons = new JButton[15][15];
         for (int row = 0; row < 15; row++) {
             for (int col = 0; col < 15; col++) {
                 buttons[row][col] = new JButton();
                 buttons[row][col].setPreferredSize(new
                         Dimension(25, 25));
             }
         }

         handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         handPanel.setPreferredSize(new Dimension(600, 50));
         updateHandPanel();

         JFrame frame = new JFrame();
         JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
         for (int row = 0; row < 15; row++) {
             for (int col = 0; col < 15; col++) {
                 container.add(buttons[row][col]);
             }
         }

         frame.add(handPanel, BorderLayout.SOUTH);
         frame.add(container, BorderLayout.NORTH);
         frame.pack();
         frame.setVisible(true);
     }

     public void updateHandPanel() {
         handPanel.removeAll();
         for (Tile tile : model.getCurrentPlayer().getHand()) {
             JButton tileButton = new JButton(String.valueOf(tile.getLetter()));
             handPanel.add(tileButton);
         }
         handPanel.revalidate();
         handPanel.repaint();
     }
 }

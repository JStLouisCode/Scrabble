import java.awt.*;
import javax.swing.*;

class View {
    private JButton[][] buttons;
    private final Game model;
    private JPanel handPanel;
    private JButton lastPressedButton = null;

    public View(Game model) {
        this.model = model;
        initializeBoard();
        initializeAnswer();


        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        handPanel.setPreferredSize(new Dimension(650, 50));
        updateHandPanel();

        initializeFrame();


    }
    public void initializeBoard(){
        //initialize buttons
        buttons = new JButton[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(25,25));
                // Add action listener to turn the button green on click
                button.addActionListener(new BoardPressed(this,button));

                buttons[row][col] = button;
            }
        }

        buttons[7][7].setText("A");
    }

     public void initializeAnswer(){
         JButton answerButton = new JButton("Answer");
         answerButton.addActionListener(e -> System.out.println("Answer button clicked")); // Example action

         JPanel answerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     }
     public void initializeFrame(){
         JFrame frame = new JFrame();
         JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
         //initialize panels
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
             tileButton.addActionListener(new HandPressed(this, tileButton));
             handPanel.add(tileButton);
         }
         handPanel.revalidate();
         handPanel.repaint();
     }


     public JButton getLastPressedButton() {
         return lastPressedButton;
     }

     public void setLastPressedButton(JButton button) {
         this.lastPressedButton = button;
     }
}

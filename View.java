import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
 class View {
    private JButton[][] buttons;
    private final Game model;

    public View(Game model) {
        this.model = model;

        //initialize buttons
        buttons = new JButton[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setPreferredSize(new Dimension(25,25));
            }
        }

        JFrame frame = new JFrame();
        JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
        //initialize panels
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
                container.add(buttons[row][col]);
            }

        }

        //initialize guess panel
        JPanel guessPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        guessPanel.setPreferredSize(new Dimension(600,150));
        JButton answer = new JButton("Answer");
        guessPanel.add(answer);
        frame.add(guessPanel, BorderLayout.SOUTH);

        frame.add(container, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);


    }

}

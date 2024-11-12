import javax.swing.*;
import java.awt.*;

public class ScrabbleModelViewFrame extends JFrame implements ScrabbleModelView {

    JButton[][] buttons;
    Game model;

    public ScrabbleViewFrame(){
        super("Scrabble");
        this.setLayout(new GridLayout(15, 15));
        model = new Game();
        model.addScrabbleView(this);
        buttons = new JButton[15][15];
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        ScrabbleController  sc = new ScrabbleController(model) ;
        for(int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                JButton button = new JButton(" ");
                button.setActionCommand(i + " " + j);
                button.addActionListener(sc);
                this.buttons[i][j] = button;
                this.add(button, BorderLayout.CENTER);
            }
        }

        this.setVisible(true);
    }

        public void handleScrabbleStatusUpdate(ScrabbleEvent e){
        }

        public static void main(String[] args) {
            new ScrabbleViewFrame();
        }
    }

}

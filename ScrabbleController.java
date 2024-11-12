import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrabbleController implements ActionListener {
    Game model;
    public ScrabbleController(Game model){
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] position = e.getActionCommand().split(" ");
        model.play(row, col, word, direction);
    }
}
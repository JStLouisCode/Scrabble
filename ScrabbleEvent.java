import java.util.EventObject;

public class ScrabbleEvent extends EventObject {
    int x;
    int y;
    String word;
    char direction;

    public ScrabbleEvent(Game model, int x, int y, String word, char direction){
        super(model);
        this.x = x;
        this.y = y;
        this.word = word;
        this.direction = direction;
    }

    public int getX(){ return x;}
    public int getY(){ return y;}

}

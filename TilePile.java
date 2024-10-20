import java.util.ArrayList;

public class TilePile {
    ArrayList<Tile> tiles;
    TilePile(){
        this.tiles = new ArrayList<>();
    }

    public void addTile(char letter, int count){
        for (int i = 0; i < count; i++) {
            this.tiles.add(new Tile(letter));
        }
    }

}

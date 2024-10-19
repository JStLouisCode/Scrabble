import java.util.ArrayList;

public class TileStack {
    ArrayList<Tile> tiles;
    TileStack(){
        this.tiles = new ArrayList<>();
    }
    public void addTile(char letter, int points, int count){
        for (int i = 0; i < count; i++) {
            this.tiles.add(new Tile(letter,points));
        }
    }

}

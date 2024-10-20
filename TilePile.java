import java.util.ArrayList;

public class TilePile {
    ArrayList<Tile> tiles;
    TilePile(){
        this.tiles = new ArrayList<>();
    }

    public ArrayList<Tile> getPile(){
        return tiles;
    }

    public void addTile(char letter, int count){
        for (int i = 0; i < count; i++) {
            this.tiles.add(new Tile(letter));
        }
    }

    public Tile deleteTile() {
        if (!tiles.isEmpty()){
            return tiles.remove(0);
        }
        return null;
    }
}

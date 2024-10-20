import java.util.*;
public class Player {
    private ArrayList<Tile> hand;
    private int points;
    private String name;

    public Player(String name){
        this.hand = new ArrayList<>();
        this.points = 0;
        this.name  = name;
    }

    public void addTile(Tile tile){
        this.hand.add(tile);
    }

    public void setName(int number) {
        this.name = ("Player"+number);
    }

    public void displayHand(){
        System.out.println(name + " has tiles: ");
        for (Tile tile : hand) {
            System.out.print(tile.getLetter() + " ");
        }
    }
}

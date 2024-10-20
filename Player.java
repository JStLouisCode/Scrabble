import java.util.*;
public class Player {
    private ArrayList<Tile> hand;
    private int points;
    private String name;

    public Player(int number){
        this.hand = new ArrayList<>();
        this.points = 0;
        this.name  = ("Player"+(number+1));
    }

    public void addTile(Tile tile){
        this.hand.add(tile);
    }

    public String getName(){
        return this.name;
    }

    public void displayHand(){
        System.out.print(name + " has tiles: ");
        for (Tile tile : hand) {
            System.out.print(tile.getLetter() + " ");
        }
        System.out.println();
    }
}

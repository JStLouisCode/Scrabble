import java.util.*;

public class Player {
    final private ArrayList<Tile> hand;
    final private int points;
    final private String name;

    public Player(int number){
        this.hand = new ArrayList<>();
        this.points = 0;
        this.name  = ("Player"+(number+1));
    }

    public void addTile(Tile tile){
        this.hand.add(tile);
    }

    public boolean hasTile(Tile tile) {
        // Check if the player has the specified tile in their hand
        for (Tile handTile : hand) {
            if (handTile.getLetter() == tile.getLetter()) {
                return true;
            }
        }
        return false;
    }

    public Tile removeTile(Tile tile) {
        // Remove and return the specified tile if it exists in the player's hand
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getLetter() == tile.getLetter()) {
                return hand.remove(i);
            }
        }
        return null;
    }

    public boolean hasAllTiles(String word, Tile[][] board, int row, int col, boolean isVertical) {
        // Check if the player has all the required tiles to form the word
        ArrayList<Character> requiredTiles = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            char boardLetter = isVertical ? board[row + i][col].getLetter() : board[row][col + i].getLetter();
            char wordLetter = word.charAt(i);

            if (boardLetter == ' ') {
                // If the board space is empty, the player must have the corresponding tile
                requiredTiles.add(wordLetter);
            } else if (boardLetter != wordLetter) {
                // If there's a mismatch between the word and the board, the word is invalid
                return false;
            }
        }

        // Check if the player has all the required tiles in their hand
        for (char tileLetter : requiredTiles) {
            Tile tile = new Tile(tileLetter); // Create a Tile object to check
            if (!hasTile(tile)) {
                return false;
            }
        }

        return true;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Tile> getHand() {
        return hand;
    }
    
    public void displayHand(){
        System.out.print(name + " has tiles: ");
        for (Tile tile : hand) {
            System.out.print(tile.getLetter() + " ");
        }
        System.out.println();
    }
}

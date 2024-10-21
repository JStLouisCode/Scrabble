import java.util.*;

public class Game {
    final private TilePile tilePile;
    final private Player[] player;
    final private Board board;
    final private Word check;

    public Game(){
        this.player = new Player[4];
        this.tilePile = new TilePile();
        this.board = new Board();
        this.check = new Word();

    }

    public void initializeTiles(){
        this.tilePile.addTile('A', 9);
        this.tilePile.addTile('B', 2);
        this.tilePile.addTile('C', 2);
        this.tilePile.addTile('D', 4);
        this.tilePile.addTile('E', 12);
        this.tilePile.addTile('F', 2);
        this.tilePile.addTile('G', 3);
        this.tilePile.addTile('H', 2);
        this.tilePile.addTile('I', 9);
        this.tilePile.addTile('J', 1);
        this.tilePile.addTile('K', 1);
        this.tilePile.addTile('L', 4);
        this.tilePile.addTile('M', 2);
        this.tilePile.addTile('N', 6);
        this.tilePile.addTile('O', 8);
        this.tilePile.addTile('P', 2);
        this.tilePile.addTile('Q', 1);
        this.tilePile.addTile('R', 6);
        this.tilePile.addTile('S', 4);
        this.tilePile.addTile('T', 6);
        this.tilePile.addTile('U', 4);
        this.tilePile.addTile('V', 2);
        this.tilePile.addTile('W', 2);
        this.tilePile.addTile('X', 1);
        this.tilePile.addTile('Y', 2);
        this.tilePile.addTile('Z', 1);
        Collections.shuffle(tilePile.getPile());
    }

    public void initializePlayer(){
        for (int i = 0; i < 4; i++) {
            player[i] = new Player(i);
            for (int j = 0; j < 7; j++) {
                player[i].addTile(tilePile.deleteTile());
            }
        }
    }

    public void play(){
        System.out.println("Welcome to scrabble! Here is the current board");
        board.displayBoard();
        
        Scanner scanner = new Scanner(System.in); // input scanner

            for (int i = 0; i < 4; i++) {
            System.out.println(player[i].getName() + "'s turn:");
            player[i].displayHand();

            
            System.out.println("What word would you like to play?"); // Get word
            String word = scanner.nextLine().toUpperCase().trim();
            System.out.println(check.isWord(word.toLowerCase()));
            if(!check.isWord(word.toLowerCase())){ // i cannot get this to work ***********
                System.out.println("Invalid word, try again.");
                i--;
                continue;
            }
            // Get direction of the word
            System.out.println("Would you like to play the word vertically (V) or horizontally (H)?");
            char direction = scanner.nextLine().toUpperCase().charAt(0);

            System.out.println("Enter starting row (1-15):"); // acquire starting coordinates
            int row = scanner.nextInt() - 1; // adjust for array representation
            System.out.println("Enter starting column (1-15):");
            int col = scanner.nextInt() - 1; // adjust for array representation
            scanner.nextLine(); // Consume newline

            // Validate word placement
            if (canPlaceWord(word, row, col, direction, player[i])) {
                placeWord(word, row, col, direction, player[i]);
                board.displayBoard();
            } else {
                System.out.println("Invalid move, try again.");
                i--; // Repeat the player's turn
            }
            if(i == 3){i = -1;}
        }
    }

    // does not check compatibility with surrounding words *********
    private boolean canPlaceWord(String word, int row, int col, char direction, Player player) { 
        ArrayList<Character> hand = new ArrayList<>();
        for (Tile tile : player.getHand()) {
            hand.add(tile.getLetter());
        }

        for (int i = 0; i < word.length(); i++) {
            // We must check from left to right for a horizontal word, and top down for vertical
            char boardCheck = (direction == 'H') ? board.getTile(row, col + i).getLetter() : board.getTile(row + i, col).getLetter();
            char wordChar = word.charAt(i);

            if (boardCheck == ' ') { 
                if (!hand.contains(wordChar)) {
                    return false; // Players rack does not contain all letters
                }
                hand.remove((Character) wordChar); // Use the letter from hand
            } else if (boardCheck != wordChar) {
                return false; // An existing letter prevents this word
            }
        }
        return true; // Word can be placed
    }

    private void placeWord(String word, int row, int col, char direction, Player player) {
        for (int i = 0; i < word.length(); i++) {
            char wordtile = word.charAt(i);
            Tile boardTile = (direction == 'H') ? board.getTile(row, col + i) : board.getTile(row + i, col); 

            if (boardTile.getLetter() == ' ') { // If it's an empty space, place the tile
                Tile newTile = player.removeTile(new Tile(wordtile)); // Remove from player hand
                player.addTile(tilePile.deleteTile());
                if (direction == 'H') {
                    board.setTile(row, col + i, newTile);
                } else {
                    board.setTile(row + i, col, newTile);
                }
            }
        }
    }

    public static void main(String[] args) {

        Game game = new Game();

        game.initializeTiles();
        game.initializePlayer();

        System.out.println();

        game.play();
    }
}

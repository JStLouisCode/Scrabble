import java.util.*;

public class Game {
    final private TilePile tilePile;
    final private Player[] player;
    final private Board board;
    final private Word check;

    public Game(){
        this.player = new Player[4];
        this.tilePile = new TilePile();
        this.initializeTiles();
        this.initializePlayer();
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
                board.displayBoard();
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

    private boolean canPlaceWord(String word, int row, int col, char direction, Player player){ 
        ArrayList<Character> hand = new ArrayList<>();
        boolean flag = true;
    
        for (Tile tile : player.getHand()) {
            hand.add(tile.getLetter());
        }
    
        if (direction == 'H') {
            // Check for horizontal adjacency
            flag &= horizontalAdjacencyCheck(word, row, col);
            for (int j = 0; j < word.length(); j++) {
                // Call vertical adjacency check for each new tile
                if (!verticalAdjacencyCheck(String.valueOf(word.charAt(j)), row, col + j)) {
                    flag = false;
                }
            }
        } else {
            // Check for vertical adjacency
            flag &= verticalAdjacencyCheck(word, row, col);
            for (int k = 0; k < word.length(); k++) {
                // Call horizontal adjacency check for each new tile
                if (!horizontalAdjacencyCheck(String.valueOf(word.charAt(k)), row + k, col)) {
                    flag = false;
                }
            }
        }
        return flag; 
    }

    private boolean horizontalAdjacencyCheck(String word, int row, int col) {
        StringBuilder adjacent = new StringBuilder();

        // Go left from the starting column
        int startCol = col;
        while (startCol > 0 && board.getTile(row, startCol - 1).getLetter() != ' ') {
            startCol--;
        }

        // Append the letters to the left
        for (int i = startCol; i < col; i++) {
            adjacent.append(board.getTile(row, i).getLetter());
        }

        // Append the new word
        adjacent.append(word);

        // Go right from the end of the new word
        for (int i = col + word.length(); i < 15; i++) {
            if (board.getTile(row, i).getLetter() == ' ') {
                break; // Stop if we hit an empty tile
            }
            adjacent.append(board.getTile(row, i).getLetter());
        }

        String adjacentWord = adjacent.toString().toLowerCase();
        return check.isWord(adjacentWord);
    }

    private boolean verticalAdjacencyCheck(String word, int row, int col) {
        StringBuilder adjacent = new StringBuilder();

        // Go up from the starting row
        int startRow = row;
        while (startRow > 0 && board.getTile(startRow - 1, col).getLetter() != ' ') {
            startRow--;
        }

        // Append the letters above
        for (int i = startRow; i < row; i++) {
            adjacent.append(board.getTile(i, col).getLetter());
        }

        // Append the new word
        adjacent.append(word);

        // Go down from the end of the new word
        for (int i = row + word.length(); i < 15; i++) {
            if (board.getTile(i, col).getLetter() == ' ') {
                break; // Stop if we hit an empty tile
            }
            adjacent.append(board.getTile(i, col).getLetter());
        }

        String adjacentWord = adjacent.toString().toLowerCase();
        return check.isWord(adjacentWord);
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

        game.play();
    }
}

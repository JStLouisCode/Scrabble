import java.util.*;

/**
 * The Game class manages the overall flow of the Scrabble game.
 * It initializes the game components, handles player actions, word validation, and tile placement.
 */
public class Game {

    // Attributes

    // The collection of tiles available in the game.
    final public TilePile tilePile;

    // The array of players participating in the game.
    final private Player[] player;

    // Game board where words are placed.
    final public Board board;

    // Word validator that checks the validity of words against a dictionary.
    final private Word check;

    // Constructor

    /**
     * Initializes the game components, including the players, tile pile, board, and word validator.
     */
    public Game(){
        this.player = new Player[4];
        this.tilePile = new TilePile();
        this.initializeTiles();
        this.initializePlayer();
        this.board = new Board(tilePile.deleteTile());
        this.check = new Word();
    }

    // Methods

    /**
     * Initializes the tile pile with the correct quantity of each letter tile.
     * Shuffles the tiles to randomize their order.
     */
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

    /**
     * Initializes the players and gives each of them 7 tiles from the tile pile.
     */
    public void initializePlayer(){
        for (int i = 0; i < 4; i++) {
            player[i] = new Player(i);
            for (int j = 0; j < 7; j++) {
                player[i].addTile(tilePile.deleteTile());
            }
        }
    }

    /**
     * Validates user input for row and column, ensuring valid coordinates on the board.
     *
     * @param scanner The scanner for user input.
     * @return An array with two integers representing the row and column.
     */
    public int[] checkValidRowCol(Scanner scanner) {
        int row = -1, col = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter starting row (1-15):");
                row = Integer.parseInt(scanner.nextLine()) - 1; // Adjust for array indexing

                System.out.println("Enter starting column (1-15):");
                col = Integer.parseInt(scanner.nextLine()) - 1; // Adjust for array indexing

                if (row >= 0 && row < 15 && col >= 0 && col < 15) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter numbers between 1 and 15.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return new int[]{row, col};
    }

    /**
     * Continuously manages player turns, collects input for word placement,
     * and updates the board until the game ends.
     */
    public void play() {
        System.out.println("Welcome to Scrabble! Here is the current board");
        board.displayBoard();

        Scanner scanner = new Scanner(System.in); // Input scanner

        int currentPlayer = 0; // Track the current player
        while (true) { // Infinite loop for continuous turns
            System.out.println(player[currentPlayer].getName() + "'s turn:");
            player[currentPlayer].displayHand();

            // Get word
            System.out.println("What word would you like to play?");
            String word = scanner.nextLine().toUpperCase().trim();

            // Check if the word is valid
            if (!check.isWord(word.toLowerCase())) {
                System.out.println("Invalid word, try again.");
                continue; // Start the turn over
            }

            // Get direction of the word
            char direction = ' ';
            while (direction != 'V' && direction != 'H') {
                System.out.println("Would you like to play the word vertically (V) or horizontally (H)?");
                String input = scanner.nextLine().toUpperCase().trim();
                if (input.length() > 0) { //ensure that the input is not empty
                    direction = input.charAt(0);
                }
                if (direction != 'V' && direction != 'H') {
                    System.out.println("Invalid direction. Please enter 'V' for vertical or 'H' for horizontal.");
                }
            }

            int[] rowCol = checkValidRowCol(scanner);
            int row = rowCol[0];
            int col = rowCol[1];

            // Validate word placement
            if (canPlaceWord(word, row, col, direction, player[currentPlayer])) {
                placeWord(word, row, col, direction, player[currentPlayer]);
                board.displayBoard();
            } else {
                System.out.println("Invalid move, try again.");
                continue; // Repeat the player's turn
            }

            // Move to the next player
            currentPlayer = (currentPlayer + 1) % 4;
        }
    }

    /**
     * Checks if the word can be legally placed on the board at the specified position.
     *
     * @param word The word to be placed.
     * @param row The starting row index for the word (0-14).
     * @param col The starting column index for the word (0-14).
     * @param direction The direction of the word: 'H' for horizontal, 'V' for vertical.
     * @param player The player attempting to place the word.
     * @return true if the word can be legally placed, false otherwise.
     */
    public boolean canPlaceWord(String word, int row, int col, char direction, Player player) {
        boolean flag = true;

        // out of bounds check
        if (direction == 'H') {
            if (word.length() + col > 15) return false;
        } else {
            if (word.length() + row > 15) return false;
        }

        // first see if the word fits on the board
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            Tile boardTile = (direction == 'H') ? board.getTile(row, col + i) : board.getTile(row + i, col);
            
            if (boardTile.getLetter() == ' ') {
                continue;
            }

            if (boardTile.getLetter() != currentChar) {
                return false;
            }
        }

        // now second layer check, new formed adjacent words must also be valid
        if (direction == 'H') {
            if (word.length() + col > 15) return false;
            flag &= horizontalAdjacencyCheck(word, row, col); // does the word extend other letters?

            for (int i = 0; i < word.length(); i++) {
                if (board.getTile(row, col + i).getLetter() == ' ') {
                    if (!verticalAdjacencyCheck(String.valueOf(word.charAt(i)), row, col + i)) { // does each letter create new valid vertical adjacent word
                        return false;
                    }
                } 
            }
        } 
        else {
            if (word.length() + row > 15) return false;
            flag &= verticalAdjacencyCheck(word, row, col); // does the word extend other letters?

            for (int i = 0; i < word.length(); i++) {
                if (board.getTile(row + i, col).getLetter() == ' ') {
                    if (!horizontalAdjacencyCheck(String.valueOf(word.charAt(i)), row + i, col)) { // does each letter create new valid horizontal adjacent word
                        return false;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Checks if placing a word horizontally forms a valid word with adjacent tiles.
     *
     * @param word The word being placed.
     * @param row The starting row of the word.
     * @param col The starting column of the word.
     * @return true if the word forms valid horizontal adjacent words, false otherwise.
     */
    public boolean horizontalAdjacencyCheck(String word, int row, int col) {
        StringBuilder adjacent = new StringBuilder();
        int startCol = col;
        
        while (startCol > 0 && board.getTile(row, startCol - 1).getLetter() != ' ') { // find starting letter index within row
            startCol--;
        }
    
        for (int i = startCol; i < col; i++) {
            adjacent.append(board.getTile(row, i).getLetter()); // add extension left of new letter(s), if present
        }
    
        adjacent.append(word); // add newly placed letter(s)
    
        for (int i = col + word.length(); i < 15; i++) {
            if (board.getTile(row, i).getLetter() == ' ') break;
            adjacent.append(board.getTile(row, i).getLetter()); // add letters to the right of new letter(s), if present 
        }
    
        String formedWord = adjacent.toString().toLowerCase(); // form word as String
        return check.isWord(formedWord); // check valid word, (single character words are valid)
    }

    /**
     * Checks if placing a word vertically forms a valid word with adjacent tiles.
     *
     * @param word The word being placed.
     * @param row The starting row of the word.
     * @param col The starting column of the word.
     * @return true if the word forms valid vertical adjacent words, false otherwise.
     */
    public boolean verticalAdjacencyCheck(String word, int row, int col) {
        StringBuilder adjacent = new StringBuilder();
        int startRow = row;
    
        while (startRow > 0 && board.getTile(startRow - 1, col).getLetter() != ' ') { // find starting letter index within col
            startRow--;
        }
    
        for (int i = startRow; i < row; i++) {
            adjacent.append(board.getTile(i, col).getLetter()); // add extension above new word, if present
        }
    
        adjacent.append(word); // add newly placed letter(s)
    
        for (int i = row + word.length(); i < 15; i++) {
            if (board.getTile(i, col).getLetter() == ' ') break;
            adjacent.append(board.getTile(i, col).getLetter()); // add letters below the new letter(s), if present
        }
    
        String formedWord = adjacent.toString().toLowerCase(); // form word as String
        return check.isWord(formedWord); //check valid word, (single character words are valid)
    }

    /**
     * Places a word on the board at the specified position and updates the player's hand.
     *
     * @param word The word to be placed on the board.
     * @param row The starting row index for the word (0-14).
     * @param col The starting column index for the word (0-14).
     * @param direction The direction of the word: 'H' for horizontal, 'V' for vertical.
     * @param player The player placing the word.
     */
    public void placeWord(String word, int row, int col, char direction, Player player) {
        for (int i = 0; i < word.length(); i++) {
            char wordtile = word.charAt(i);
            Tile boardTile = (direction == 'H') ? board.getTile(row, col + i) : board.getTile(row + i, col); 

            if (boardTile.getLetter() == ' ') { // If it's an empty space, place the tile (this is checked within canPlaceWord() as well)
                Tile newTile = player.removeTile(new Tile(wordtile)); // remove from player rack
                player.addTile(tilePile.deleteTile()); // take from tilePile (bag)
                if (direction == 'H') {
                    board.setTile(row, col + i, newTile);
                } else { // direction == 'V'
                    board.setTile(row + i, col, newTile);
                }
            }
        }
        addPoints(word, player);
    }

    /**
     * Adds points to the player's total score based on the word placed on the board.
     *
     * @param word The word that was placed on the board.
     * @param player The player who placed the word and earned the points.
     */
    public void addPoints(String word, Player player){
        for (char letter : word.toCharArray()){
            Tile tile = new Tile(letter);
            player.addPoints(tile.getPoints());
        }
    }

    /**
     * The main entry point of the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}

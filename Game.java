import java.util.*;

public class Game {
    final private TilePile tilePile;
    final private Player[] player;

    final private Board board;
    final private Word check;

    public Game(){
        this.player = new Player[4];
        this.tilePile = new TilePile();
        initializeTiles();
        initializePlayer();
        this.board = new Board(tilePile.deleteTile());
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

    private int[] checkValidRowCol(Scanner scanner) {
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

    private void addPoints(String word, Player player){
        for (char letter : word.toCharArray()){
            Tile tile = new Tile(letter);
            player.addPoints(tile.getPoints());
        }
    }
    public static void main(String[] args) {

        Game game = new Game();



        System.out.println();

        game.play();
    }
}

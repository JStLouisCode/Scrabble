public class Board {
    final private Tile[][] board;

    public Board() {
        board = new Tile[15][15]; // Initialize the board with blank tiles
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Tile(' ');  // Empty tile with a space
            }
        }
    }

    
    public void placeTile(int row, int col, char letter) { // Method to set a tile at a specific position *overwrites current letter*
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            board[row][col] = new Tile(letter);
        } else {
            System.out.println("Invalid index");
        }
    }

    // Method to display the board
    public void printBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class Board {
    private Tile[][] board = new Tile[15][15];

    public Board(){
        initializeBoard();
        displayBoard();
    }

    public void displayBoard(){
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                System.out.print("  |  " + board[row][col].getLetter() + " ");
            }
            System.out.println("|");
        }
    }

    public void initializeBoard(){
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                board[row][col] = new Tile('A', 0);
            }
        }
    }
}


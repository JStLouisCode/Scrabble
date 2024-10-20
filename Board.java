public class Board {
    private Tile[][] board = new Tile[15][15];

    public Board(Tile centerLetter){
        initializeBoard(centerLetter);
        displayBoard();
    }

    public void displayBoard(){
        System.out.print("   ");
        for (int i = 0; i < 15; i++) {
            if (i < 12) {
                System.out.print("   " + (i+1) + "   ");
            }
            else {
                System.out.print(" " + (i + 1) + "   ");
            }
        }

        System.out.println();
        for (int row = 0; row < 15; row++) {
            System.out.print(row+1);
            if(row<9){
                System.out.print(" ");
            }
            for (int col = 0; col < 15; col++) {
                System.out.print("  |  " + board[row][col].getLetter() + " ");
            }
            System.out.println("  |");

        }
    }

    public void initializeBoard(Tile centerLetter){
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                board[row][col] = new Tile(' ');
            }
        }
        board[7][7] = centerLetter;
    }
}
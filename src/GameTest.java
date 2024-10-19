public class GameTest {
    public static void main(String[] args) {
        Board board = new Board();
        
        board.placeTile(8, 8, 't'); //arbirary test with different value letters
        board.placeTile(8, 9, 'e');
        board.placeTile(8, 10, 's');
        board.placeTile(8, 11, 't');
        board.placeTile(8, 12, 'b');
        board.placeTile(8, 13, 'x');
        board.placeTile(8, 14, 'q');

        board.printBoard(); // dsiplay board
    }
}

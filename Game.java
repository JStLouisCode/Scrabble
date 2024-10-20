import java.util.Collections;

public class Game {
    private TilePile tilePile;
    private Player[] player = new Player[4];
    private Board board;
    public Game(){
        this.tilePile = new TilePile();
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
        System.out.println("Welcome to scrabble! here is the current board");
        Board board = new Board(new Tile('A'));
        board.displayBoard();

            for (int i = 0; i < 4; i++) {
                player[i].displayHand();
                System.out.println("What word would you like to play?");
                //impliment logic

                System.out.println("Would you like to play the word vertically or horizontally?\n\n");
                //imppliment logicil
            }

    }

    public static void main(String[] args) {
        Board board = new Board(new Tile('A'));
        Game game = new Game();

        game.initializeTiles();
        game.initializePlayer();

        game.play();
    }
}

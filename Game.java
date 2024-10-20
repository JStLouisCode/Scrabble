import java.util.Collections;

public class Game {
    private TilePile tilePile;
    private Player[] player = new Player[4];
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
        this.tilePile.addTile('K', 12);
        this.tilePile.addTile('L', 2);
        this.tilePile.addTile('M', 9);
        this.tilePile.addTile('N', 2);
        this.tilePile.addTile('O', 2);
        this.tilePile.addTile('P', 4);
        this.tilePile.addTile('Q', 12);
        this.tilePile.addTile('R', 2);
        this.tilePile.addTile('S', 9);
        this.tilePile.addTile('T', 2);
        this.tilePile.addTile('U', 2);
        this.tilePile.addTile('V', 4);
        this.tilePile.addTile('W', 12);
        this.tilePile.addTile('X', 2);
        this.tilePile.addTile('Y', 12);
        this.tilePile.addTile('Z', 2);
        Collections.shuffle(tilePile.getPile());
    }
    public void initializePlayer(){

        for (int i = 0; i < 4; i++) {
            player[i].setName(i);
            for (int j = 0; j < 7; j++) {

            }
            this.player[i].addTile(tilePile.deleteTile());
        }

    }

    public void play(){

    }

    public static void main(String[] args) {
        Board board = new Board(new Tile('A'));
        Game game = new Game();
        game.initializeTiles();
        game.initializePlayer();

        game.play();
    }
}

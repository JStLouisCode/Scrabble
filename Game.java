public class Game {
    private TilePile tileStack;
    public Game(){
        this.tileStack = new TilePile();
    }

    public void initializeTiles(){
        this.tileStack.addTile('A', 9);
        this.tileStack.addTile('B', 2);
        this.tileStack.addTile('C', 2);
        this.tileStack.addTile('D', 4);
        this.tileStack.addTile('E', 12);
        this.tileStack.addTile('F', 2);
        this.tileStack.addTile('G', 3);
        this.tileStack.addTile('H', 2);
        this.tileStack.addTile('I', 9);
        this.tileStack.addTile('J', 1);
        this.tileStack.addTile('K', 12);
        this.tileStack.addTile('L', 2);
        this.tileStack.addTile('M', 9);
        this.tileStack.addTile('N', 2);
        this.tileStack.addTile('O', 2);
        this.tileStack.addTile('P', 4);
        this.tileStack.addTile('Q', 12);
        this.tileStack.addTile('R', 2);
        this.tileStack.addTile('S', 9);
        this.tileStack.addTile('T', 2);
        this.tileStack.addTile('U', 2);
        this.tileStack.addTile('V', 4);
        this.tileStack.addTile('W', 12);
        this.tileStack.addTile('X', 2);
        this.tileStack.addTile('Y', 12);
        this.tileStack.addTile('Z', 2);

    }

    public static void main(String[] args) {
        Board board = new Board();
    }
}

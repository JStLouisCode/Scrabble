import java.util.ArrayList;

public class Game {
    private TileStack tileStack;
    public Game(){
        this.tileStack = new TileStack();
    }

    public void initializeTiles(){
        this.tileStack.addTile('A',1,9);
        this.tileStack.addTile('B',3,2);
        this.tileStack.addTile('C',3,2);
        this.tileStack.addTile('D',2,4);
        this.tileStack.addTile('E',1,12);
        this.tileStack.addTile('F',4,2);
        this.tileStack.addTile('G',4,3);
        this.tileStack.addTile('H',4,2);
        this.tileStack.addTile('I',1,9);
        this.tileStack.addTile('J',8,1);
        this.tileStack.addTile('K',5,12);
        this.tileStack.addTile('L',1,2);
        this.tileStack.addTile('M',3,9);
        this.tileStack.addTile('N',1,2);
        this.tileStack.addTile('O',1,2);
        this.tileStack.addTile('P',3,4);
        this.tileStack.addTile('Q',10,12);
        this.tileStack.addTile('R',1,2);
        this.tileStack.addTile('S',1,9);
        this.tileStack.addTile('T',1,2);
        this.tileStack.addTile('U',1,2);
        this.tileStack.addTile('V',2,4);
        this.tileStack.addTile('W',4,12);
        this.tileStack.addTile('X',8,2);
        this.tileStack.addTile('Y',4,12);
        this.tileStack.addTile('Z',10,2);


    }
}

public class Tile {
    char letter;
    int points;

    public Tile(char letter){
        this.letter = letter;
        this.points = assignValue(letter);
    }
    private static int assignValue(char letter) { //assign value to each letter according to scrabble rules
        return switch (Character.toLowerCase(letter)) {
            case 'a', 'e', 'i', 'o', 'u', 'l', 'n', 's', 't', 'r' -> 1;
            case 'd', 'g' -> 2;
            case 'b', 'c', 'm', 'p' -> 3;
            case 'f', 'h', 'v', 'w', 'y' -> 4;
            case 'k' -> 5;
            case 'j', 'x' -> 8;
            case 'q', 'z' -> 10;
            default -> 0; // In case of an invalid or blank ' ' character
        };
    }
    public char getLetter() {
        return letter;
    }

    public int getPoints() {
        return points;
    }

}

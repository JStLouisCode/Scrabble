/**
 * The Tile class represents a tile in the Scrabble game.
 * Each tile has a letter and a point value associated with it.
 */
public class Tile {

    // Attributes

    // Letter printed on the tile (e.g., 'A', 'J', 'Y').
    private char letter;

    // Point value of the tile (e.g., 'A' = 1, 'J' = 3, 'Z' = 10).
    private int value;


    // Constructor

    /**
     * Constructor for the Tile class.
     * Initializes the tile with a specific letter and value.
     *
     * @param letter The character on the tile.
     * @param value  The point value of the tile.
     */
    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }


    // Getters

    /**
     * Gets the letter on the tile.
     *
     * @return The character printed on the tile.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Gets the point value of the tile.
     *
     * @return The point value associated with the tile.
     */
    public int getValue() {
        return value;
    }
}


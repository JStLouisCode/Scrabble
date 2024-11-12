import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The GameTest class contains JUnit test cases to test the functionality of the Game class.
 * It performs various tests on the placement of words and scoring calculations.
 */
public class GameTest {
    // Attributes
    private static Game game;
    private static Player player;
    public static int counter;

    // Constructor

    /**
     * Default constructor for the GameTest class.
     * Creates a new instance of the GameTest class.
     */
    public GameTest() {}

    // Methods

    /**
     * BeforeAll method runs once before any test cases in the class.
     * It initializes the game and player attributes.
     */
    @BeforeAll
    private void setup() {
        game = new Game();
        player = new Player(0);
        game.initializeTiles();
        game.initializePlayer();
    }

    /**
     * AfterEach method runs after each individual test case.
     * It increments the counter attribute by 1 and prints the number of tests executed so far.
     */
    @AfterEach
    private void summary() {
        counter++;
        System.out.println("The number of tests: " + counter);
    }

    /**
     * AfterAll method runs once after all test cases in the class.
     * It prints a message indicating that all tests are done.
     */
    @AfterAll static void tearDown() {
        System.out.print("All tests are done")
    }


    // Test Cases
    /**
     * Test case for valid horizontal word placement.
     * Ensures the word "HELLO" can be placed correctly on the board.
     */
    @Test
    public void test_ValidHorizontalPlacement() {
        player.addTile(new Tile('H'));
        player.addTile(new Tile('E'));
        player.addTile(new Tile('L'));
        player.addTile(new Tile('L'));
        player.addTile(new Tile('O'));

        boolean canPlace = game.canPlaceWord("HELLO", 7, 7, 'H', player);
        assertTrue(canPlace, "Should be able to place 'HELLO' horizontally at (7, 7)");

        game.placeWord("HELLO", 7, 7, 'H', player);
        assertEquals('H', game.board.getTile(7, 7).getLetter());
        assertEquals('O', game.board.getTile(7, 11).getLetter());
    }

    /**
     * Test case for invalid word placement that goes out of bounds.
     * Ensures the word "WORD" cannot be placed starting at (14, 14).
     */
    @Test
    public void test_InvalidPlacementOutOfBounds() {
        player.addTile(new Tile('W'));
        player.addTile(new Tile('O'));
        player.addTile(new Tile('R'));
        player.addTile(new Tile('D'));

        boolean canPlace = game.canPlaceWord("WORD", 14, 14, 'H', player);
        assertFalse(canPlace, "Should not be able to place 'WORD' horizontally starting at (14, 14)");
    }

    /**
     * Test case for invalid placement due to tile overlap conflict.
     * Ensures the word "ABC" cannot overlap with an existing word "CAT".
     */
    @Test
    public void test_InvalidPlacementOverlapConflict() {
        player.addTile(new Tile('A'));
        player.addTile(new Tile('B'));
        player.addTile(new Tile('C'));

        game.placeWord("CAT", 7, 7, 'H', player);

        boolean canPlace = game.canPlaceWord("ABC", 7, 7, 'H', player);
        assertFalse(canPlace, "Should not be able to place 'ABC' overlapping with 'CAT' at (7, 7)");
    }

    /**
     * Test case for single word score calculation.
     * Verifies the score calculation for the word "HI".
     */
    @Test
    public void test_SingleWordScore() {
        player.addTile(new Tile('H'));
        player.addTile(new Tile('I'));

        game.placeWord("HI", 7, 7, 'H', player);
        assertEquals(5, player.getPoints(), "Score for 'HI' should be 5 points (H=4, I=1)");
    }

    /**
     * Test case for score calculation with high-value tiles.
     * Verifies the score for the word "QUIZ".
     */
    @Test
    public void test_ScoreWithBonus() {
        player.addTile(new Tile('Q'));
        player.addTile(new Tile('U'));
        player.addTile(new Tile('I'));
        player.addTile(new Tile('Z'));

        game.placeWord("QUIZ", 7, 7, 'H', player);
        assertEquals(22, player.getPoints(), "Score for 'QUIZ' should be 22 points (Q=10, U=1, I=1, Z=10)");
    }

    /**
     * Test case for score calculation with multiple words.
     * Verifies the cumulative score for placing "HELP" and "POT".
     */
    @Test
    public void test_ScoreWithMultipleWords() {
        player.addTile(new Tile('H'));
        player.addTile(new Tile('E'));
        player.addTile(new Tile('L'));
        player.addTile(new Tile('P'));

        game.placeWord("HELP", 7, 7, 'H', player);
        assertEquals(9, player.getPoints(), "Score for 'HELP' should be 9 points");

        player.addTile(new Tile('P'));
        player.addTile(new Tile('O'));
        player.addTile(new Tile('T'));

        game.placeWord("POT", 8, 7, 'H', player);
        assertEquals(15, player.getPoints(), "Total score should be 15 points after placing 'HELP' and 'POT'");
    }
}

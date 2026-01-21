package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Comprehensive unit tests for the Dice Game application.
 * Tests cover DiceGame, Player, and rollDie() functionality.
 */
public class AppTest 
{
    private DiceGame game;
    private Player player1;
    private Player player2;

    @Before
    public void setUp()
    {
        game = new DiceGame();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
    }

    // ============= TESTS FOR rollDie() METHOD =============

    /**
     * Test that rollDie() returns a valid die value between 1 and 6
     */
    @Test
    public void testRollDieReturnsValidValue()
    {
        int dieValue = game.rollDie();
        assertTrue("Die value should be between 1 and 6", dieValue >= 1 && dieValue <= 6);
    }

    /**
     * Test that rollDie() returns values within the valid range on multiple calls
     */
    @Test
    public void testRollDieMultipleTimes()
    {
        for (int i = 0; i < 100; i++)
        {
            int dieValue = game.rollDie();
            assertTrue("Die value " + dieValue + " should be between 1 and 6", 
                       dieValue >= 1 && dieValue <= 6);
        }
    }

    /**
     * Test that rollDie() produces a variety of values (basic randomness check)
     */
    @Test
    public void testRollDieRandomness()
    {
        boolean hasVariety = false;
        int firstValue = game.rollDie();
        
        // Roll multiple times to verify we get different values
        for (int i = 0; i < 20; i++)
        {
            int value = game.rollDie();
            if (value != firstValue)
            {
                hasVariety = true;
                break;
            }
        }
        
        assertTrue("rollDie() should produce varying values", hasVariety);
    }

    // ============= TESTS FOR PLAYER CLASS =============

    /**
     * Test Player initialization - name and score
     */
    @Test
    public void testPlayerInitialization()
    {
        Player player = new Player("TestPlayer");
        assertEquals("Player name should be TestPlayer", "TestPlayer", player.getName());
        assertEquals("Initial score should be 0", 0, player.getScore());
    }

    /**
     * Test Player getName() getter
     */
    @Test
    public void testPlayerGetName()
    {
        Player player = new Player("Alice");
        assertEquals("getName() should return correct name", "Alice", player.getName());
    }

    /**
     * Test Player getScore() getter initially
     */
    @Test
    public void testPlayerGetScoreInitial()
    {
        Player player = new Player("Bob");
        assertEquals("getScore() should return 0 initially", 0, player.getScore());
    }

    /**
     * Test addScore() adds points correctly
     */
    @Test
    public void testPlayerAddScore()
    {
        Player player = new Player("TestPlayer");
        player.addScore(5);
        assertEquals("Score should be 5 after adding 5", 5, player.getScore());
    }

    /**
     * Test addScore() accumulates points correctly on multiple calls
     */
    @Test
    public void testPlayerAddScoreAccumulates()
    {
        Player player = new Player("TestPlayer");
        player.addScore(3);
        player.addScore(4);
        player.addScore(2);
        assertEquals("Score should be 9 after adding 3, 4, and 2", 9, player.getScore());
    }

    /**
     * Test addScore() with various point values
     */
    @Test
    public void testPlayerAddScoreDifferentValues()
    {
        Player player = new Player("TestPlayer");
        
        for (int i = 1; i <= 6; i++)
        {
            player.addScore(i);
        }
        
        assertEquals("Score should be 21 after adding 1+2+3+4+5+6", 21, player.getScore());
    }

    /**
     * Test resetScore() clears the score to 0
     */
    @Test
    public void testPlayerResetScore()
    {
        Player player = new Player("TestPlayer");
        player.addScore(10);
        assertEquals("Score should be 10 after adding 10", 10, player.getScore());
        
        player.resetScore();
        assertEquals("Score should be 0 after resetScore()", 0, player.getScore());
    }

    /**
     * Test resetScore() works after multiple score additions
     */
    @Test
    public void testPlayerResetScoreAfterMultipleAdditions()
    {
        Player player = new Player("TestPlayer");
        player.addScore(5);
        player.addScore(3);
        player.addScore(6);
        assertEquals("Score should be 14", 14, player.getScore());
        
        player.resetScore();
        assertEquals("Score should be 0 after reset", 0, player.getScore());
    }

    /**
     * Test that player can accumulate score again after reset
     */
    @Test
    public void testPlayerScoreAfterReset()
    {
        Player player = new Player("TestPlayer");
        player.addScore(10);
        player.resetScore();
        player.addScore(3);
        assertEquals("Score should be 3 after reset and adding 3", 3, player.getScore());
    }

    // ============= TESTS FOR DICEGAME CLASS =============

    /**
     * Test DiceGame rollDie() returns valid values
     */
    @Test
    public void testDiceGameRollDie()
    {
        int dieValue = game.rollDie();
        assertTrue("DiceGame rollDie() should return value between 1-6", 
                   dieValue >= 1 && dieValue <= 6);
    }

    /**
     * Test DiceGame rollDie() on multiple consecutive calls
     */
    @Test
    public void testDiceGameRollDieMultiple()
    {
        for (int i = 0; i < 50; i++)
        {
            int value = game.rollDie();
            assertTrue("Roll " + i + ": value should be 1-6, got " + value, 
                       value >= 1 && value <= 6);
        }
    }

    /**
     * Test determineWinner() returns player1 when player1 has higher score
     */
    @Test
    public void testDetermineWinnerPlayer1Wins()
    {
        game.getPlayer1().addScore(20);
        game.getPlayer2().addScore(15);
        
        Player winner = game.determineWinner();
        assertEquals("Player 1 should win", game.getPlayer1(), winner);
    }

    /**
     * Test determineWinner() returns player2 when player2 has higher score
     */
    @Test
    public void testDetermineWinnerPlayer2Wins()
    {
        game.getPlayer1().addScore(10);
        game.getPlayer2().addScore(25);
        
        Player winner = game.determineWinner();
        assertEquals("Player 2 should win", game.getPlayer2(), winner);
    }

    /**
     * Test determineWinner() returns null when scores are tied
     */
    @Test
    public void testDetermineWinnerTie()
    {
        game.getPlayer1().addScore(15);
        game.getPlayer2().addScore(15);
        
        Player winner = game.determineWinner();
        assertNull("Winner should be null for a tie", winner);
    }

    /**
     * Test determineWinner() with equal high scores (tie)
     */
    @Test
    public void testDetermineWinnerTieHighScores()
    {
        game.getPlayer1().addScore(60);
        game.getPlayer2().addScore(60);
        
        Player winner = game.determineWinner();
        assertNull("determineWinner() should return null for tie", winner);
    }

    /**
     * Test determineWinner() with zero scores (tie)
     */
    @Test
    public void testDetermineWinnerBothZeroScore()
    {
        Player winner = game.determineWinner();
        assertNull("determineWinner() should return null when both have 0 score", winner);
    }

    /**
     * Test getPlayer1() returns correct player
     */
    @Test
    public void testGetPlayer1()
    {
        Player retrievedPlayer = game.getPlayer1();
        assertNotNull("getPlayer1() should not return null", retrievedPlayer);
        assertSame("getPlayer1() should return the same player instance", 
                   retrievedPlayer, game.getPlayer1());
    }

    /**
     * Test getPlayer2() returns correct player
     */
    @Test
    public void testGetPlayer2()
    {
        Player retrievedPlayer = game.getPlayer2();
        assertNotNull("getPlayer2() should not return null", retrievedPlayer);
        assertSame("getPlayer2() should return the same player instance", 
                   retrievedPlayer, game.getPlayer2());
    }

    /**
     * Test getPlayer1() and getPlayer2() return different players
     */
    @Test
    public void testGetPlayersAreDifferent()
    {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        assertNotSame("getPlayer1() and getPlayer2() should return different players", 
                      player1, player2);
    }

    /**
     * Test getCurrentDieValue() after rolling
     */
    @Test
    public void testGetCurrentDieValue()
    {
        int dieValue = game.rollDie();
        game.setCurrentDieValue(dieValue); // Store the value since rollDie doesn't auto-store it
        int currentValue = game.getCurrentDieValue();
        assertEquals("getCurrentDieValue() should match the rolled value", 
                     dieValue, currentValue);
    }

    /**
     * Test complex scenario: multiple rolls with winner determination
     */
    @Test
    public void testComplexGameScenario()
    {
        // Simulate game play
        game.getPlayer1().addScore(5);
        game.getPlayer1().addScore(4);
        game.getPlayer1().addScore(6);
        
        game.getPlayer2().addScore(3);
        game.getPlayer2().addScore(2);
        game.getPlayer2().addScore(1);
        
        assertEquals("Player 1 should have score 15", 15, game.getPlayer1().getScore());
        assertEquals("Player 2 should have score 6", 6, game.getPlayer2().getScore());
        assertEquals("Player 1 should be the winner", game.getPlayer1(), game.determineWinner());
    }
}

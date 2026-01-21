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

    // ============= ADDITIONAL EDGE CASE TESTS =============

    /**
     * Test Player toString() method format
     */
    @Test
    public void testPlayerToString()
    {
        Player player = new Player("Alice");
        player.addScore(25);
        String result = player.toString();
        assertTrue("toString should contain player name", result.contains("Alice"));
        assertTrue("toString should contain score", result.contains("25"));
    }

    /**
     * Test Player toString() with zero score
     */
    @Test
    public void testPlayerToStringZeroScore()
    {
        Player player = new Player("Bob");
        String result = player.toString();
        assertTrue("toString should contain player name", result.contains("Bob"));
        assertTrue("toString should contain 0 score", result.contains("0"));
    }

    /**
     * Test adding score of 1 (minimum die value)
     */
    @Test
    public void testPlayerAddScoreMinimum()
    {
        Player player = new Player("TestPlayer");
        player.addScore(1);
        assertEquals("Score should be 1", 1, player.getScore());
    }

    /**
     * Test adding score of 6 (maximum die value)
     */
    @Test
    public void testPlayerAddScoreMaximum()
    {
        Player player = new Player("TestPlayer");
        player.addScore(6);
        assertEquals("Score should be 6", 6, player.getScore());
    }

    /**
     * Test adding score of 0
     */
    @Test
    public void testPlayerAddScoreZero()
    {
        Player player = new Player("TestPlayer");
        player.addScore(0);
        assertEquals("Score should remain 0", 0, player.getScore());
    }

    /**
     * Test adding large scores
     */
    @Test
    public void testPlayerAddScoreLarge()
    {
        Player player = new Player("TestPlayer");
        player.addScore(100);
        assertEquals("Score should be 100", 100, player.getScore());
    }

    /**
     * Test player with very high accumulated score
     */
    @Test
    public void testPlayerHighScore()
    {
        Player player = new Player("TestPlayer");
        for (int i = 0; i < 10; i++)
        {
            player.addScore(6); // Max die value
        }
        assertEquals("Score should be 60 after 10 rolls of 6", 60, player.getScore());
    }

    /**
     * Test rollDie distribution includes all values 1-6
     */
    @Test
    public void testRollDieAllValuesAppear()
    {
        boolean[] values = new boolean[7]; // Index 0 unused, 1-6 for die values
        
        // Roll enough times to likely see all values
        for (int i = 0; i < 1000; i++)
        {
            int value = game.rollDie();
            values[value] = true;
        }
        
        // Check all values 1-6 appeared at least once
        for (int i = 1; i <= 6; i++)
        {
            assertTrue("Die value " + i + " should appear in 1000 rolls", values[i]);
        }
    }

    /**
     * Test determineWinner with Player 1 having significantly higher score
     */
    @Test
    public void testDetermineWinnerLargeMargin()
    {
        game.getPlayer1().addScore(50);
        game.getPlayer2().addScore(10);
        
        Player winner = game.determineWinner();
        assertEquals("Player 1 should win with large margin", game.getPlayer1(), winner);
    }

    /**
     * Test determineWinner with Player 2 winning by 1 point
     */
    @Test
    public void testDetermineWinnerSmallMargin()
    {
        game.getPlayer1().addScore(20);
        game.getPlayer2().addScore(21);
        
        Player winner = game.determineWinner();
        assertEquals("Player 2 should win by 1 point", game.getPlayer2(), winner);
    }

    /**
     * Test multiple resets on same player
     */
    @Test
    public void testPlayerMultipleResets()
    {
        Player player = new Player("TestPlayer");
        player.addScore(10);
        player.resetScore();
        assertEquals("Score should be 0 after first reset", 0, player.getScore());
        
        player.addScore(5);
        player.resetScore();
        assertEquals("Score should be 0 after second reset", 0, player.getScore());
    }

    /**
     * Test player name with empty string
     */
    @Test
    public void testPlayerEmptyName()
    {
        Player player = new Player("");
        assertEquals("Empty name should be stored", "", player.getName());
    }

    /**
     * Test player name with special characters
     */
    @Test
    public void testPlayerNameSpecialCharacters()
    {
        Player player = new Player("Player-1!");
        assertEquals("Name with special chars should be stored", "Player-1!", player.getName());
    }

    /**
     * Test player name with long string
     */
    @Test
    public void testPlayerLongName()
    {
        String longName = "VeryLongPlayerNameWithManyCharacters";
        Player player = new Player(longName);
        assertEquals("Long name should be stored", longName, player.getName());
    }

    /**
     * Test score accumulation pattern simulating 10 turns
     */
    @Test
    public void testPlayerFullGameScoreAccumulation()
    {
        Player player = new Player("TestPlayer");
        int[] rolls = {3, 5, 2, 6, 4, 1, 5, 3, 6, 2}; // 10 turns
        int expectedTotal = 0;
        
        for (int roll : rolls)
        {
            player.addScore(roll);
            expectedTotal += roll;
        }
        
        assertEquals("Total score should match sum of all rolls", expectedTotal, player.getScore());
    }

    /**
     * Test getCurrentDieValue initial state
     */
    @Test
    public void testGetCurrentDieValueInitial()
    {
        DiceGame newGame = new DiceGame();
        assertEquals("Initial die value should be 0", 0, newGame.getCurrentDieValue());
    }

    /**
     * Test that two dice games are independent
     */
    @Test
    public void testMultipleDiceGamesIndependent()
    {
        DiceGame game1 = new DiceGame();
        DiceGame game2 = new DiceGame();
        
        game1.getPlayer1().addScore(10);
        game2.getPlayer1().addScore(20);
        
        assertEquals("Game 1 player 1 should have score 10", 10, game1.getPlayer1().getScore());
        assertEquals("Game 2 player 1 should have score 20", 20, game2.getPlayer1().getScore());
    }

    /**
     * Test determineWinner with maximum possible scores
     */
    @Test
    public void testDetermineWinnerMaxScores()
    {
        game.getPlayer1().addScore(60); // 10 turns * 6
        game.getPlayer2().addScore(59);
        
        Player winner = game.determineWinner();
        assertEquals("Player 1 should win with max possible score", game.getPlayer1(), winner);
    }

    /**
     * Test determineWinner with minimum possible scores (both zero)
     */
    @Test
    public void testDetermineWinnerMinScores()
    {
        Player winner = game.determineWinner();
        assertNull("Should be tie when both have 0", winner);
    }

    /**
     * Test player names are correctly initialized in DiceGame
     */
    @Test
    public void testDiceGamePlayerNames()
    {
        assertNotNull("Player 1 should have a name", game.getPlayer1().getName());
        assertNotNull("Player 2 should have a name", game.getPlayer2().getName());
    }

    /**
     * Test score immutability through getName (getName shouldn't affect score)
     */
    @Test
    public void testPlayerGetNameDoesNotAffectScore()
    {
        Player player = new Player("Alice");
        player.addScore(10);
        String name = player.getName();
        assertEquals("Score should remain unchanged after getName", 10, player.getScore());
        assertEquals("Name should be Alice", "Alice", name);
    }

    /**
     * Test consecutive rolls produce values in valid range
     */
    @Test
    public void testConsecutiveRollsValid()
    {
        int roll1 = game.rollDie();
        int roll2 = game.rollDie();
        int roll3 = game.rollDie();
        
        assertTrue("First roll should be 1-6", roll1 >= 1 && roll1 <= 6);
        assertTrue("Second roll should be 1-6", roll2 >= 1 && roll2 <= 6);
        assertTrue("Third roll should be 1-6", roll3 >= 1 && roll3 <= 6);
    }

    /**
     * Test winner determination after reset
     */
    @Test
    public void testDetermineWinnerAfterReset()
    {
        game.getPlayer1().addScore(20);
        game.getPlayer2().addScore(30);
        
        game.getPlayer1().resetScore();
        game.getPlayer2().resetScore();
        
        Player winner = game.determineWinner();
        assertNull("Should be tie after both scores reset to 0", winner);
    }

    /**
     * Test setCurrentDieValue with various values
     */
    @Test
    public void testSetCurrentDieValue()
    {
        game.setCurrentDieValue(3);
        assertEquals("Should store value 3", 3, game.getCurrentDieValue());
        
        game.setCurrentDieValue(6);
        assertEquals("Should store value 6", 6, game.getCurrentDieValue());
        
        game.setCurrentDieValue(1);
        assertEquals("Should store value 1", 1, game.getCurrentDieValue());
    }

    /**
     * Test Player class with same names are different instances
     */
    @Test
    public void testPlayersDifferentInstances()
    {
        Player p1 = new Player("Test");
        Player p2 = new Player("Test");
        
        p1.addScore(10);
        p2.addScore(20);
        
        assertEquals("Player 1 should have score 10", 10, p1.getScore());
        assertEquals("Player 2 should have score 20", 20, p2.getScore());
    }
}

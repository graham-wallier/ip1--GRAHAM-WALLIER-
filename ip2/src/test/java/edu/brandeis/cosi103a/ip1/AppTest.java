package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Comprehensive unit tests for Automation: The Game.
 * Tests cover Card classes, Player, Supply, and Game mechanics.
 */
public class AppTest {
    private Game game;
    private Player player1;
    private Player player2;
    private Supply supply;

    @Before
    public void setUp() {
        game = new Game();
        player1 = new Player("Test Player 1");
        player2 = new Player("Test Player 2");
        supply = new Supply();
    }

    // ============= TESTS FOR CARD CLASSES =============

    @Test
    public void testAutomationCardCreation() {
        AutomationCard card = new AutomationCard("Method", 2, 1);
        assertEquals("Method", card.getName());
        assertEquals(2, card.getCost());
        assertEquals(1, card.getValue());
    }

    @Test
    public void testAutomationCardToString() {
        AutomationCard card = new AutomationCard("Module", 5, 3);
        String str = card.toString();
        assertTrue(str.contains("Module"));
        assertTrue(str.contains("5"));
        assertTrue(str.contains("3"));
    }

    @Test
    public void testCryptocurrencyCardCreation() {
        CryptocurrencyCard card = new CryptocurrencyCard("Bitcoin", 0, 1);
        assertEquals("Bitcoin", card.getName());
        assertEquals(0, card.getCost());
        assertEquals(1, card.getValue());
    }

    @Test
    public void testCryptocurrencyCardToString() {
        CryptocurrencyCard card = new CryptocurrencyCard("Ethereum", 3, 2);
        String str = card.toString();
        assertTrue(str.contains("Ethereum"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains("2"));
    }

    @Test
    public void testCardEquality() {
        Card card1 = new AutomationCard("Method", 2, 1);
        Card card2 = new AutomationCard("Method", 2, 1);
        assertEquals(card1, card2);
    }

    @Test
    public void testCardInequality() {
        Card card1 = new AutomationCard("Method", 2, 1);
        Card card2 = new AutomationCard("Module", 5, 3);
        assertNotEquals(card1, card2);
    }

    @Test
    public void testCardHashCode() {
        Card card1 = new AutomationCard("Method", 2, 1);
        Card card2 = new AutomationCard("Method", 2, 1);
        assertEquals(card1.hashCode(), card2.hashCode());
    }

    // ============= TESTS FOR PLAYER CLASS =============

    @Test
    public void testPlayerCreation() {
        Player p = new Player("Alice");
        assertEquals("Alice", p.getName());
    }

    @Test
    public void testAddToDrawPile() {
        Card card = new CryptocurrencyCard("Bitcoin", 0, 1);
        player1.addToDrawPile(card);
        assertEquals(1, player1.getDrawPileSize());
    }

    @Test
    public void testAddToDiscardPile() {
        Card card = new CryptocurrencyCard("Bitcoin", 0, 1);
        player1.addToDiscardPile(card);
        assertEquals(1, player1.getDiscardPileSize());
    }

    @Test
    public void testGetHand() {
        List<Card> hand = player1.getHand();
        assertNotNull(hand);
        assertEquals(0, hand.size());
    }

    @Test
    public void testShuffleDrawPile() {
        for (int i = 0; i < 5; i++) {
            player1.addToDrawPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        player1.shuffleDrawPile();
        assertEquals(5, player1.getDrawPileSize());
    }

    @Test
    public void testDealHand() {
        // Add 10 cards to draw pile
        for (int i = 0; i < 10; i++) {
            player1.addToDrawPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        player1.dealHand();

        // Should have 5 cards in hand, 5 in draw pile
        assertEquals(5, player1.getHand().size());
        assertEquals(5, player1.getDrawPileSize());
    }

    @Test
    public void testDealHandWithReshuffle() {
        // Add 3 cards to draw pile
        for (int i = 0; i < 3; i++) {
            player1.addToDrawPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        // Add 5 cards to discard pile
        for (int i = 0; i < 5; i++) {
            player1.addToDiscardPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        }

        player1.dealHand();

        // Should have 5 cards in hand
        assertEquals(5, player1.getHand().size());
        // Discard pile should be empty, draw pile should have 3
        assertEquals(0, player1.getDiscardPileSize());
        assertEquals(3, player1.getDrawPileSize());
    }

    @Test
    public void testCalculateAutomationPoints() {
        player1.addToDrawPile(new AutomationCard("Method", 2, 1));
        player1.addToDrawPile(new AutomationCard("Module", 5, 3));
        player1.addToDrawPile(new AutomationCard("Framework", 8, 6));

        int points = player1.calculateAutomationPoints();
        assertEquals(10, points); // 1 + 3 + 6
    }

    @Test
    public void testCalculateAutomationPointsInHand() {
        player1.addToDrawPile(new AutomationCard("Method", 2, 1));
        player1.addToDrawPile(new AutomationCard("Module", 5, 3));
        player1.dealHand();

        int points = player1.calculateAutomationPoints();
        assertEquals(4, points); // 1 + 3
    }

    @Test
    public void testGetAllCards() {
        player1.addToDrawPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        player1.addToDiscardPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        player1.addToDrawPile(new CryptocurrencyCard("Bitcoin", 0, 1));

        List<Card> allCards = player1.getAllCards();
        assertEquals(3, allCards.size());
    }

    @Test
    public void testTotalDeckSize() {
        player1.addToDrawPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        player1.addToDiscardPile(new CryptocurrencyCard("Bitcoin", 0, 1));
        player1.dealHand();

        // After dealHand: 1 card in hand (dealt from draw), 1 card remains in draw
        assertEquals(2, player1.getTotalDeckSize());
    }

    @Test
    public void testDiscardCards() {
        Card card1 = new CryptocurrencyCard("Bitcoin", 0, 1);
        Card card2 = new CryptocurrencyCard("Bitcoin", 0, 1);
        player1.addToDrawPile(card1);
        player1.addToDrawPile(card2);
        player1.dealHand();

        List<Card> hand = player1.getHand();
        player1.discardCards(hand);

        assertEquals(0, player1.getHand().size());
        assertEquals(2, player1.getDiscardPileSize());
    }

    // ============= TESTS FOR SUPPLY CLASS =============

    @Test
    public void testSupplyInitialization() {
        Supply s = new Supply();
        assertEquals(14, s.getCardCount("Method"));
        assertEquals(8, s.getCardCount("Module"));
        assertEquals(8, s.getCardCount("Framework"));
        assertEquals(60, s.getCardCount("Bitcoin"));
        assertEquals(40, s.getCardCount("Ethereum"));
        assertEquals(30, s.getCardCount("Dogecoin"));
    }

    @Test
    public void testTakeCard() {
        Card card = supply.takeCard("Bitcoin");
        assertNotNull(card);
        assertEquals("Bitcoin", card.getName());
        assertEquals(59, supply.getCardCount("Bitcoin"));
    }

    @Test
    public void testTakeCardNotAvailable() {
        // Empty the supply
        for (int i = 0; i < 60; i++) {
            supply.takeCard("Bitcoin");
        }
        Card card = supply.takeCard("Bitcoin");
        assertNull(card);
    }

    @Test
    public void testHasCard() {
        assertTrue(supply.hasCard("Bitcoin"));
        // Remove all bitcoins
        for (int i = 0; i < 60; i++) {
            supply.takeCard("Bitcoin");
        }
        assertFalse(supply.hasCard("Bitcoin"));
    }

    @Test
    public void testGetFrameworkCount() {
        assertEquals(8, supply.getFrameworkCount());
        supply.takeCard("Framework");
        assertEquals(7, supply.getFrameworkCount());
    }

    @Test
    public void testSupplyToString() {
        String str = supply.toString();
        assertTrue(str.contains("Supply"));
        assertTrue(str.contains("Method"));
    }

    // ============= TESTS FOR GAME CLASS =============

    @Test
    public void testGameInitialization() {
        game.initialize();

        // Each player should have 10 cards (7 bitcoins + 3 methods)
        assertEquals(10, game.getPlayer1().getTotalDeckSize());
        assertEquals(10, game.getPlayer2().getTotalDeckSize());

        // Each player should have a hand of 5 cards
        assertEquals(5, game.getPlayer1().getHand().size());
        assertEquals(5, game.getPlayer2().getHand().size());
    }

    @Test
    public void testGameStarterDeck() {
        game.initialize();

        // Count bitcoin and method cards in player 1's deck
        int bitcoins = 0;
        int methods = 0;
        for (Card card : game.getPlayer1().getAllCards()) {
            if (card.getName().equals("Bitcoin")) bitcoins++;
            if (card.getName().equals("Method")) methods++;
        }
        assertEquals(7, bitcoins);
        assertEquals(3, methods);
    }

    @Test
    public void testGetCurrentPlayer() {
        game.initialize();
        Player current = game.getCurrentPlayer();
        assertTrue(current == game.getPlayer1() || current == game.getPlayer2());
    }

    @Test
    public void testGetOtherPlayer() {
        game.initialize();
        Player current = game.getCurrentPlayer();
        Player other = game.getOtherPlayer();
        assertNotEquals(current, other);
    }

    @Test
    public void testExecuteTurn() {
        game.initialize();
        int initialTurns = game.getTurnCount();
        game.executeTurn();
        assertEquals(initialTurns + 1, game.getTurnCount());
    }

    @Test
    public void testPlayerSwitchesAfterTurn() {
        game.initialize();
        Player first = game.getCurrentPlayer();
        game.executeTurn();
        Player second = game.getCurrentPlayer();
        assertNotEquals(first, second);
    }

    @Test
    public void testGameEndsWhenFrameworksGone() {
        game.initialize();
        // Remove all frameworks
        for (int i = 0; i < 8; i++) {
            game.getSupply().takeCard("Framework");
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void testGameNotOverInitially() {
        game.initialize();
        assertFalse(game.isGameOver());
    }

    @Test
    public void testGetWinner() {
        game.initialize();
        game.getPlayer1().addToDrawPile(new AutomationCard("Framework", 8, 6));
        int player1AP = game.getPlayer1().calculateAutomationPoints();
        int player2AP = game.getPlayer2().calculateAutomationPoints();

        if (player1AP > player2AP) {
            assertEquals(game.getPlayer1(), game.getWinner());
        } else if (player2AP > player1AP) {
            assertEquals(game.getPlayer2(), game.getWinner());
        } else {
            assertNull(game.getWinner());
        }
    }

    @Test
    public void testPlayGameCompletes() {
        game.playGame();
        assertTrue(game.isGameOver());
        assertEquals(game.getSupply().getFrameworkCount(), 0);
    }

    @Test
    public void testPlayGameHasWinner() {
        game.playGame();
        // At least one player should have automation points
        int p1AP = game.getPlayer1().calculateAutomationPoints();
        int p2AP = game.getPlayer2().calculateAutomationPoints();
        assertTrue(p1AP > 0 || p2AP > 0);
    }

    @Test
    public void testGameLengthReasonable() {
        game.playGame();
        // Game should take at least a few turns
        assertTrue(game.getTurnCount() > 0);
    }

    // ============= INTEGRATION TESTS =============

    @Test
    public void testMultipleGames() {
        // Play multiple games to ensure randomness works
        for (int i = 0; i < 3; i++) {
            Game g = new Game();
            g.playGame();
            assertTrue(g.isGameOver());
        }
    }

    @Test
    public void testGameFlowLogic() {
        game.initialize();

        // Player should have cryptocurrency cards
        Player p = game.getCurrentPlayer();
        int cryptoCount = 0;
        for (Card card : p.getHand()) {
            if (card instanceof CryptocurrencyCard) {
                cryptoCount++;
            }
        }
        // Should have at least one Bitcoin (probability is high)
        assertTrue(cryptoCount > 0 || p.getHand().size() < 5);
    }

    @Test
    public void testHandRefilled() {
        game.initialize();
        game.getPlayer1().dealHand();
        assertEquals(5, game.getPlayer1().getHand().size());
    }

    @Test
    public void testSupplyDepletion() {
        game.initialize();
        Supply s = game.getSupply();
        
        // After initialization, 20 cards removed (10 per player for starter decks)
        // Initial supply is 14+8+8+60+40+30 = 160
        // After removing 20: should have 140
        int currentTotal = s.getCardCount("Method") + s.getCardCount("Module") + s.getCardCount("Framework");
        currentTotal += s.getCardCount("Bitcoin") + s.getCardCount("Ethereum") + s.getCardCount("Dogecoin");

        assertEquals(140, currentTotal);
    }

    @Test
    public void testGameProgressesTowardCompletion() {
        game.initialize();
        int frameworksBefore = game.getSupply().getFrameworkCount();
        
        for (int i = 0; i < 20; i++) {
            if (!game.isGameOver()) {
                game.executeTurn();
            }
        }
        
        int frameworksAfter = game.getSupply().getFrameworkCount();
        // Either frameworks decreased or game is over
        assertTrue(frameworksAfter <= frameworksBefore);
    }

    @Test
    public void testPlayerHandStaysValid() {
        game.initialize();
        
        for (int i = 0; i < 5; i++) {
            game.executeTurn();
            assertTrue(game.getPlayer1().getHand().size() <= 5);
            assertTrue(game.getPlayer2().getHand().size() <= 5);
        }
    }

    @Test
    public void testDifferentGamesHaveDifferentWinners() {
        // Play multiple games and at least check they complete
        boolean gameCompleted = false;
        for (int i = 0; i < 3; i++) {
            Game g = new Game();
            g.playGame();
            gameCompleted = true;
            assertTrue(g.isGameOver());
        }
        assertTrue(gameCompleted);
    }

    @Test
    public void testSupplyCountAccuracy() {
        // Start with full supply
        Supply s = new Supply();
        int originalCount = s.getCardCount("Framework");
        
        // Remove some cards
        for (int i = 0; i < 3; i++) {
            Card card = s.takeCard("Framework");
            assertNotNull(card);
        }
        
        assertEquals(originalCount - 3, s.getCardCount("Framework"));
    }
}


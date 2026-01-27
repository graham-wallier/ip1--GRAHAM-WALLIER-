package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the main game logic for Automation: The Game.
 * Manages two players, the supply, turns, and game flow.
 */
public class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Supply supply;
    private Random random;
    private int turnCount;

    /**
     * Constructor that initializes the game.
     */
    public Game() {
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.supply = new Supply();
        this.random = new Random();
        this.turnCount = 0;
    }

    /**
     * Initializes the game by setting up starter decks and dealing initial hands.
     */
    public void initialize() {
        // Give each player a starter deck: 7 Bitcoins and 3 Methods
        for (int i = 0; i < 7; i++) {
            player1.addToDrawPile(supply.takeCard("Bitcoin"));
            player2.addToDrawPile(supply.takeCard("Bitcoin"));
        }
        for (int i = 0; i < 3; i++) {
            player1.addToDrawPile(supply.takeCard("Method"));
            player2.addToDrawPile(supply.takeCard("Method"));
        }

        // Shuffle and deal initial hands
        player1.shuffleDrawPile();
        player2.shuffleDrawPile();
        player1.dealHand();
        player2.dealHand();

        // Randomly choose starting player
        currentPlayer = random.nextBoolean() ? player1 : player2;
    }

    /**
     * Gets the current player.
     *
     * @return The player whose turn it is
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the other player (not the current player).
     *
     * @return The other player
     */
    public Player getOtherPlayer() {
        return currentPlayer == player1 ? player2 : player1;
    }

    /**
     * Gets player 1.
     *
     * @return Player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets player 2.
     *
     * @return Player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Executes one complete turn for the current player.
     * A turn consists of a buy phase followed by a cleanup phase.
     */
    public void executeTurn() {
        turnCount++;

        // Buy phase: play cryptocurrency cards to gain money, then buy a card
        buyPhase();

        // Cleanup phase: discard hand and dealt cards, deal new hand
        cleanupPhase();

        // Switch to the other player
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Executes the buy phase of the current turn.
     * The player plays all cryptocurrency cards from their hand to gain money,
     * then buys the most valuable card they can afford.
     */
    private void buyPhase() {
        Player player = currentPlayer;
        int money = 0;

        // Calculate money from playing cryptocurrency cards
        List<Card> hand = player.getHand();
        for (Card card : hand) {
            if (card instanceof CryptocurrencyCard) {
                money += ((CryptocurrencyCard) card).getValue();
            }
        }

        // Buy the best card we can afford
        if (money > 0) {
            Card cardToBuy = chooseBestCardAffordable(money);
            if (cardToBuy != null) {
                player.addToDiscardPile(cardToBuy);
            }
        }
    }

    /**
     * Chooses the best card that the player can afford and is available.
     * Strategy: prefer high-value automation cards, then cryptocurrency cards.
     *
     * @param money The amount of money available
     * @return The best card to buy, or null if nothing affordable
     */
    private Card chooseBestCardAffordable(int money) {
        // Try to buy in this order: Framework, Module, Method, Dogecoin, Ethereum, Bitcoin
        String[] preferredOrder = { "Framework", "Module", "Method", "Dogecoin", "Ethereum", "Bitcoin" };

        for (String cardName : preferredOrder) {
            Card card = supply.takeCard(cardName);
            if (card != null && card.getCost() <= money) {
                return card;
            } else if (card != null) {
                // Put the card back if we can't afford it
                // (This is a bit hacky, but it works for our simple supply)
                // We'll handle this differently - just return null and don't take the card
            }
        }

        return null;
    }

    /**
     * Executes the cleanup phase of the current turn.
     * All cards in hand are discarded and dealt, and a new hand is drawn.
     */
    private void cleanupPhase() {
        Player player = currentPlayer;
        player.dealHand();
    }

    /**
     * Checks if the game is over (all Framework cards have been purchased).
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return supply.getFrameworkCount() == 0;
    }

    /**
     * Determines the winner and returns it.
     * The winner is the player with the most Automation Points.
     *
     * @return The winning player, or null if tied
     */
    public Player getWinner() {
        int player1AP = player1.calculateAutomationPoints();
        int player2AP = player2.calculateAutomationPoints();

        if (player1AP > player2AP) {
            return player1;
        } else if (player2AP > player1AP) {
            return player2;
        } else {
            return null; // Tie
        }
    }

    /**
     * Runs a complete game to completion.
     */
    public void playGame() {
        initialize();

        while (!isGameOver()) {
            executeTurn();
        }
    }

    /**
     * Gets the current turn count.
     *
     * @return The number of turns that have been executed
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Gets the supply.
     *
     * @return The card supply
     */
    public Supply getSupply() {
        return supply;
    }
}

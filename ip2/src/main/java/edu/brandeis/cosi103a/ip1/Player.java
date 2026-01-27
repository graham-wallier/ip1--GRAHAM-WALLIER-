package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player in Automation: The Game.
 * Each player has a draw pile, discard pile, and hand.
 */
public class Player {
    private String name;
    private List<Card> drawPile;      // Cards to draw from
    private List<Card> discardPile;   // Cards that have been played/discarded
    private List<Card> hand;          // Current hand of cards

    /**
     * Constructor for a player.
     *
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.hand = new ArrayList<>();
    }

    /**
     * Gets the player's name.
     *
     * @return The player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a card to the discard pile.
     *
     * @param card The card to add
     */
    public void addToDiscardPile(Card card) {
        discardPile.add(card);
    }

    /**
     * Adds a card to the draw pile (used during game setup).
     *
     * @param card The card to add
     */
    public void addToDrawPile(Card card) {
        drawPile.add(card);
    }

    /**
     * Gets the player's current hand.
     *
     * @return A list of cards in the player's hand
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Deals a new hand of cards from the draw pile.
     * If the draw pile doesn't have enough cards, the discard pile is shuffled
     * and becomes the new draw pile.
     */
    public void dealHand() {
        int cardsNeeded = 5;

        // Add current hand to discard pile
        discardPile.addAll(hand);
        hand.clear();

        // Deal cards from draw pile
        while (cardsNeeded > 0 && !drawPile.isEmpty()) {
            hand.add(drawPile.remove(drawPile.size() - 1));
            cardsNeeded--;
        }

        // If we need more cards, shuffle discard pile into draw pile
        if (cardsNeeded > 0 && !discardPile.isEmpty()) {
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();

            // Continue dealing
            while (cardsNeeded > 0 && !drawPile.isEmpty()) {
                hand.add(drawPile.remove(drawPile.size() - 1));
                cardsNeeded--;
            }
        }
    }

    /**
     * Shuffles the draw pile.
     */
    public void shuffleDrawPile() {
        Collections.shuffle(drawPile);
    }

    /**
     * Gets the size of the draw pile.
     *
     * @return The number of cards in the draw pile
     */
    public int getDrawPileSize() {
        return drawPile.size();
    }

    /**
     * Gets the size of the discard pile.
     *
     * @return The number of cards in the discard pile
     */
    public int getDiscardPileSize() {
        return discardPile.size();
    }

    /**
     * Gets the total deck size (draw + discard + hand).
     *
     * @return The total number of cards the player owns
     */
    public int getTotalDeckSize() {
        return drawPile.size() + discardPile.size() + hand.size();
    }

    /**
     * Calculates the total Automation Points in the player's entire deck.
     *
     * @return The sum of AP values of all Automation cards in the player's deck
     */
    public int calculateAutomationPoints() {
        int points = 0;

        // Check draw pile
        for (Card card : drawPile) {
            if (card instanceof AutomationCard) {
                points += ((AutomationCard) card).getValue();
            }
        }

        // Check discard pile
        for (Card card : discardPile) {
            if (card instanceof AutomationCard) {
                points += ((AutomationCard) card).getValue();
            }
        }

        // Check hand
        for (Card card : hand) {
            if (card instanceof AutomationCard) {
                points += ((AutomationCard) card).getValue();
            }
        }

        return points;
    }

    /**
     * Gets all cards in the player's deck (for testing purposes).
     *
     * @return A list of all cards in the deck
     */
    public List<Card> getAllCards() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(drawPile);
        allCards.addAll(discardPile);
        allCards.addAll(hand);
        return allCards;
    }

    /**
     * Removes cards from the hand and adds them to the discard pile.
     *
     * @param cards The cards to discard
     */
    public void discardCards(List<Card> cards) {
        for (Card card : cards) {
            if (hand.remove(card)) {
                discardPile.add(card);
            }
        }
    }
}

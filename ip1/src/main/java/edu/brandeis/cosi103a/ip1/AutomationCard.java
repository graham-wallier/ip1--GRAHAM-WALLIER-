package edu.brandeis.cosi103a.ip1;

/**
 * Represents an Automation card in the game.
 * Automation cards contribute Automation Points (APs) to the player's score.
 */
public class AutomationCard extends Card {
    private int value;

    /**
     * Constructor for an Automation card.
     *
     * @param name The name of the card
     * @param cost The cost in cryptocoins to purchase
     * @param value The number of Automation Points this card is worth
     */
    public AutomationCard(String name, int cost, int value) {
        super(name, cost);
        this.value = value;
    }

    /**
     * Gets the Automation Points value of this card.
     *
     * @return The number of APs this card is worth
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets a string representation of this card.
     *
     * @return The card's name, cost, and AP value
     */
    @Override
    public String toString() {
        return name + " (cost: " + cost + ", value: " + value + " AP)";
    }
}

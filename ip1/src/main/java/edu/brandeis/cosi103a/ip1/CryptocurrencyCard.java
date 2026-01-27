package edu.brandeis.cosi103a.ip1;

/**
 * Represents a Cryptocurrency card in the game.
 * Cryptocurrency cards provide spending power when played during the buy phase.
 */
public class CryptocurrencyCard extends Card {
    private int value;

    /**
     * Constructor for a Cryptocurrency card.
     *
     * @param name The name of the card
     * @param cost The cost in cryptocoins to purchase
     * @param value The number of cryptocoins this card is worth when played
     */
    public CryptocurrencyCard(String name, int cost, int value) {
        super(name, cost);
        this.value = value;
    }

    /**
     * Gets the monetary value of this card when played.
     *
     * @return The number of cryptocoins this card is worth
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets a string representation of this card.
     *
     * @return The card's name, cost, and monetary value
     */
    @Override
    public String toString() {
        return name + " (cost: " + cost + ", value: " + value + " coin)";
    }
}

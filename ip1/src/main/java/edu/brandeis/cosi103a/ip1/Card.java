package edu.brandeis.cosi103a.ip1;

/**
 * Abstract base class representing a card in Automation: The Game.
 * All cards have a cost and some value.
 */
public abstract class Card {
    protected String name;
    protected int cost;

    /**
     * Constructor for a card.
     *
     * @param name The name of the card
     * @param cost The cost in cryptocoins to purchase this card
     */
    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Gets the name of the card.
     *
     * @return The card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost of the card.
     *
     * @return The cost in cryptocoins
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets a string representation of this card.
     *
     * @return The card's name and cost
     */
    @Override
    public String toString() {
        return name + " (cost: " + cost + ")";
    }

    /**
     * Checks if two cards are equal based on name and cost.
     *
     * @param obj The object to compare
     * @return true if both cards have the same name and cost
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        Card other = (Card) obj;
        return this.name.equals(other.name) && this.cost == other.cost;
    }

    /**
     * Gets the hash code for this card.
     *
     * @return Hash code based on name and cost
     */
    @Override
    public int hashCode() {
        return name.hashCode() * 31 + cost;
    }
}

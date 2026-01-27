package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the supply of cards available for purchase in the game.
 * The supply contains all card types and tracks how many of each are available.
 */
public class Supply {
    // Automation cards
    private List<Card> methods;      // Cost: 2, Value: 1 AP, Quantity: 14
    private List<Card> modules;      // Cost: 5, Value: 3 AP, Quantity: 8
    private List<Card> frameworks;   // Cost: 8, Value: 6 AP, Quantity: 8

    // Cryptocurrency cards
    private List<Card> bitcoins;     // Cost: 0, Value: 1 coin, Quantity: 60
    private List<Card> ethereums;    // Cost: 3, Value: 2 coins, Quantity: 40
    private List<Card> dogecoins;    // Cost: 6, Value: 3 coins, Quantity: 30

    /**
     * Constructor that initializes the supply with all cards.
     */
    public Supply() {
        methods = new ArrayList<>();
        modules = new ArrayList<>();
        frameworks = new ArrayList<>();
        bitcoins = new ArrayList<>();
        ethereums = new ArrayList<>();
        dogecoins = new ArrayList<>();

        // Initialize Automation cards
        for (int i = 0; i < 14; i++) {
            methods.add(new AutomationCard("Method", 2, 1));
        }
        for (int i = 0; i < 8; i++) {
            modules.add(new AutomationCard("Module", 5, 3));
        }
        for (int i = 0; i < 8; i++) {
            frameworks.add(new AutomationCard("Framework", 8, 6));
        }

        // Initialize Cryptocurrency cards
        for (int i = 0; i < 60; i++) {
            bitcoins.add(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        for (int i = 0; i < 40; i++) {
            ethereums.add(new CryptocurrencyCard("Ethereum", 3, 2));
        }
        for (int i = 0; i < 30; i++) {
            dogecoins.add(new CryptocurrencyCard("Dogecoin", 6, 3));
        }
    }

    /**
     * Removes and returns a card of the specified type from the supply.
     *
     * @param cardName The name of the card type
     * @return The card, or null if not available
     */
    public Card takeCard(String cardName) {
        switch (cardName.toLowerCase()) {
            case "method":
                return methods.isEmpty() ? null : methods.remove(methods.size() - 1);
            case "module":
                return modules.isEmpty() ? null : modules.remove(modules.size() - 1);
            case "framework":
                return frameworks.isEmpty() ? null : frameworks.remove(frameworks.size() - 1);
            case "bitcoin":
                return bitcoins.isEmpty() ? null : bitcoins.remove(bitcoins.size() - 1);
            case "ethereum":
                return ethereums.isEmpty() ? null : ethereums.remove(ethereums.size() - 1);
            case "dogecoin":
                return dogecoins.isEmpty() ? null : dogecoins.remove(dogecoins.size() - 1);
            default:
                return null;
        }
    }

    /**
     * Gets the number of available cards of a specific type.
     *
     * @param cardName The name of the card type
     * @return The number of cards available
     */
    public int getCardCount(String cardName) {
        switch (cardName.toLowerCase()) {
            case "method":
                return methods.size();
            case "module":
                return modules.size();
            case "framework":
                return frameworks.size();
            case "bitcoin":
                return bitcoins.size();
            case "ethereum":
                return ethereums.size();
            case "dogecoin":
                return dogecoins.size();
            default:
                return 0;
        }
    }

    /**
     * Checks if a card type is available in the supply.
     *
     * @param cardName The name of the card type
     * @return true if the card is available
     */
    public boolean hasCard(String cardName) {
        return getCardCount(cardName) > 0;
    }

    /**
     * Gets the number of frameworks remaining in the supply.
     *
     * @return The number of frameworks available
     */
    public int getFrameworkCount() {
        return frameworks.size();
    }

    /**
     * Gets all available card types and their counts.
     *
     * @return A string representation of the supply
     */
    @Override
    public String toString() {
        return "Supply: Method=" + methods.size() + ", Module=" + modules.size()
                + ", Framework=" + frameworks.size() + ", Bitcoin=" + bitcoins.size()
                + ", Ethereum=" + ethereums.size() + ", Dogecoin=" + dogecoins.size();
    }
}

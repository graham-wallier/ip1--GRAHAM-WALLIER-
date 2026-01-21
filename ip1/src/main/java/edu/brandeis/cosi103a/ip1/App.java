package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;

/**
 * Dice Game - A two-player dice game where players take turns rolling a die
 * and accumulating points over 10 turns each.
 */
public class App 
{
    public static void main(String[] args)
    {
        DiceGame game = new DiceGame();
        game.playGame();
    }
}

/**
 * Player class represents a player in the dice game
 */
class Player
{
    private String name;
    private int score;

    public Player(String name)
    {
        this.name = name;
        this.score = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getScore()
    {
        return score;
    }

    public void addScore(int points)
    {
        score += points;
    }

    public void resetScore()
    {
        score = 0;
    }

    @Override
    public String toString()
    {
        return name + " (Score: " + score + ")";
    }
}

/**
 * DiceGame class manages the game logic and flow
 */
class DiceGame
{
    private static final int MAX_TURNS = 10;
    private static final int MAX_REROLLS = 2;
    private static final int DIE_SIDES = 6;

    private Player player1;
    private Player player2;
    private Scanner scanner;
    private int currentDieValue;

    public DiceGame()
    {
        this.scanner = new Scanner(System.in);
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.currentDieValue = 0;
    }

    /**
     * Rolls a single die and returns a value between 1 and 6
     * @return die value (1-6)
     */
    public int rollDie()
    {
        return (int) (Math.random() * DIE_SIDES) + 1;
    }

    /**
     * Handles one complete turn for a player including rolling and re-rolling
     * @param player the player taking their turn
     * @return the final die value for this turn
     */
    public int playerTurn(Player player)
    {
        System.out.println("\n" + player.getName() + "'s Turn");
        System.out.println("================================");

        int dieValue = rollDie();
        currentDieValue = dieValue;
        int rerollsRemaining = MAX_REROLLS;

        System.out.println("Initial roll: " + dieValue);

        // Allow player to re-roll up to 2 times
        while (rerollsRemaining > 0)
        {
            System.out.print("Do you want to re-roll? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y"))
            {
                dieValue = rollDie();
                currentDieValue = dieValue;
                rerollsRemaining--;
                System.out.println("New roll: " + dieValue);
                System.out.println("Re-rolls remaining: " + rerollsRemaining);
            }
            else if (input.equals("no") || input.equals("n"))
            {
                break;
            }
            else
            {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        System.out.println("Final value for this turn: " + dieValue);
        player.addScore(dieValue);
        System.out.println(player.getName() + "'s new score: " + player.getScore());

        return dieValue;
    }

    /**
     * Initializes the game with two players
     */
    public void initializeGame()
    {
        System.out.println("===== WELCOME TO DICE GAME =====");
        System.out.println("Two players will take 10 turns each rolling a die.");
        System.out.println("Each turn, you can re-roll up to 2 times.");
        System.out.println("The player with the highest total score wins!\n");

        System.out.print("Enter Player 1 name: ");
        String player1Name = scanner.nextLine().trim();

        System.out.print("Enter Player 2 name: ");
        String player2Name = scanner.nextLine().trim();

        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
    }

    /**
     * Runs the main game loop with 10 turns per player
     */
    public void playGame()
    {
        initializeGame();

        // Play 10 rounds (each player gets 10 turns)
        for (int turn = 1; turn <= MAX_TURNS; turn++)
        {
            System.out.println("\n========== TURN " + turn + " of " + MAX_TURNS + " ==========");

            // Player 1's turn
            playerTurn(player1);

            // Player 2's turn
            playerTurn(player2);
        }

        // Display results and determine winner
        displayFinalResults();
        Player winner = determineWinner();
        announceWinner(winner);

        scanner.close();
    }

    /**
     * Displays the final scores of both players
     */
    private void displayFinalResults()
    {
        System.out.println("\n========== GAME OVER ==========");
        System.out.println("Final Scores:");
        System.out.println(player1);
        System.out.println(player2);
    }

    /**
     * Determines which player has the highest score
     * @return the player with the highest score, or null if tied
     */
    public Player determineWinner()
    {
        if (player1.getScore() > player2.getScore())
        {
            return player1;
        }
        else if (player2.getScore() > player1.getScore())
        {
            return player2;
        }
        else
        {
            return null; // Tie
        }
    }

    /**
     * Announces the winner or reports a tie
     * @param winner the winning player, or null if tied
     */
    private void announceWinner(Player winner)
    {
        if (winner == null)
        {
            System.out.println("\nIt's a TIE! Both players have " + player1.getScore() + " points.");
        }
        else
        {
            System.out.println("\n🎲 " + winner.getName() + " WINS with " + winner.getScore() + " points!");
        }
    }

    // Getter methods for testing
    public Player getPlayer1()
    {
        return player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }

    public int getCurrentDieValue()
    {
        return currentDieValue;
    }

    public void setCurrentDieValue(int value)
    {
        this.currentDieValue = value;
    }
}

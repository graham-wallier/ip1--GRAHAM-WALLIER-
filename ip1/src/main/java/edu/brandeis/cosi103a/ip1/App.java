package edu.brandeis.cosi103a.ip1;

/**
 * Automation: The Game - A deck-building card game prototype based on Dominion.
 * Two automated players compete to build the most valuable deck.
 */
public class App {
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
        displayGameResults(game);
    }

    /**
     * Displays the final results of the game.
     *
     * @param game The completed game
     */
    private static void displayGameResults(Game game) {
        System.out.println("\n========== GAME OVER ==========");
        System.out.println("Final Turn Count: " + game.getTurnCount());

        int player1AP = game.getPlayer1().calculateAutomationPoints();
        int player2AP = game.getPlayer2().calculateAutomationPoints();

        System.out.println("\nPlayer 1 Automation Points: " + player1AP);
        System.out.println("Player 2 Automation Points: " + player2AP);

        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("\nWinner: " + winner.getName() + " with " + (winner == game.getPlayer1() ? player1AP : player2AP) + " APs!");
        } else {
            System.out.println("\nIt's a TIE! Both players have " + player1AP + " APs!");
        }
    }
}

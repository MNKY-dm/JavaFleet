package model;

import model.entity.Player;
import type.GameState;

public class Game {
    private Player[] players;
    private Player currentPlayer;
    private GameState gameState;
    private int turn;

    public  Game(String player1Name, String player2Name) {
        this.players = new Player[2];
        this.players[0] = new Player(player1Name);
        this.players[1] = new Player(player2Name);

        // Initialiser la dualité des plateaux
        this.players[0].setOpponentBoard(this.players[1].getMyBoard());
        this.players[1].setOpponentBoard(this.players[0].getMyBoard());


        this.gameState = GameState.SETUP; // Le jeu est en phase de construction
        this.turn = 0; // Tour 0
        this.currentPlayer = this.players[0]; // Le joueur 1 player1Name
    }

    // Getters

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getTurn() {
        return turn;
    }

    // Setters

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}

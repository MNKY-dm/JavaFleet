package model;

import model.entity.Player;

public class Game {
    private Player[] players;
    private Player currentPlayer;
    private GameState gameState;
    private int turn;

    public  Game(Player[] players, Player currentPlayer, GameState gameState, int turn) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.gameState = gameState;
        this.turn = turn;
    }

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
}

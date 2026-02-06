package model;

import model.entity.Player;
import type.AttackResult;
import type.Coordinate;
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

    public void startGame() throws IllegalStateException {
        if (areAllShipsReady()) {
            this.gameState = GameState.PLAYING;
            System.out.println("=== La partie commence ! ===");
        }
        else {
            throw new IllegalStateException(" Impossible de commencer le jeu car tous les bateaux ne sont pas placés. ");
        }
    }

    public AttackResult attack(int x, int y) throws Exception {
        if (getGameState() != GameState.PLAYING) {
            return null;
        }
        AttackResult attackResult = currentPlayer.attackOpponent(x, y);
        if (attackResult == null) {
            System.out.println("Attaque invalide ! ");
            return null;
        }
        System.out.println("Tour d'" + currentPlayer.getName() + " Tir sur la case " + new Coordinate(x, y) + " ; Résultat : " + attackResult);
        if (checkGameOver()) {
            this.gameState = GameState.FINISHED;
        }
        nextTurn();
        return attackResult;
    }

    public void nextTurn() throws Exception {
        this.turn++;
        if (this.currentPlayer == this.players[0]) {
            this.currentPlayer = this.players[1];
        } else if (this.currentPlayer == this.players[1]) {
            this.currentPlayer = this.players[0];
        } else {
            throw new Exception("Impossible de passer au tour suivant");
        }
        System.out.println("Tour n°" + this.turn + " ! C'est au tour de " + this.currentPlayer.getName() + ". ");
    }

    public void nextSetupTurn() throws Exception {
        if (this.currentPlayer == this.players[0]) {
            this.currentPlayer = this.players[1];
            System.out.println("C'est au tour du Joueur 2 de placer ses bateaux.");
        } else if (this.currentPlayer == this.players[1]) {
            try {
                System.out.println("Vérifier si tous les bateaux sont placés, si oui, démarrage de la partie");
                this.startGame();
            } catch (IllegalStateException e) {
                System.out.println("Impossible de passe au tour suivant");
            }
        } else {
            throw new Exception("Impossible de passer au tour suivant");
        }
    }

    public boolean checkGameOver() {
        for (Player player : this.players) {
            if (player.hasLost()) {
                return true;
            }
        }
        return false;
    }

    private boolean areAllShipsReady() {
        for (Player player : this.players) {
            if (!player.getMyBoard().areAllShipsPlaced()) {
                return false;
            }
        }
        return true;
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

package model.entity;

import controller.GameManager;
import model.Board;
import model.Ship;
import type.AttackResult;
import type.Orientation;

import java.util.ArrayList;

public class Player {
    private String name;
    private Player opponent;
    private Board myBoard;
    private Board opponentBoard;
    private ArrayList<Ship> ships;

    public Player(String name) {
        this.name = name;
        this.myBoard = new Board();
        this.opponentBoard = null;
        this.ships = new ArrayList<>();
        initializeShips();
    }

    public void initializeShips() {
        // Ajout des bateaux par défaut à la liste this.ships
        // À adapter en fonction
        this.ships.clear();
        this.ships.add(new Cruiser());
        this.ships.add(new BattleShip());
        this.ships.add(new BattleShip());
        this.ships.add(new Destroyer());
        this.ships.add(new Destroyer());
        this.ships.add(new Destroyer()); // Total de 16 points de vie par défaut
        this.ships.add(new NavalMine()); // Sera évitée lors du compte des points de vie
    }

    public AttackResult attackOpponent(int x, int y) {
        if (this.opponentBoard != null) {
            return this.opponentBoard.shootAt(x, y);
        }
        System.out.println("opponentBoard est null");
        return null;
    }

    public String getFleetHealth() {
        int totalHealth = 0;
        for (Ship ship : this.getShips()) {
            if (ship.getShipType().equals("NavalMine")) {
                continue;
            }
            totalHealth += ship.getHealth();
        }
        return "" + totalHealth;
    }

    public boolean hasLost() {
        return this.myBoard.areAllShipsSunk();
    }


    // Getters

    public String getName() {
        return name;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Player getOpponent() {
        return this.opponent;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setMyBoard() {
        this.myBoard = new Board();
    }

    public void setOpponentBoard(Board opponentBoard) {
        // Récupérer le bateau adverse
        this.opponentBoard = opponentBoard;
    }

    public void setOpponent(Player opponent) {
        // Récupérer l'adversaire
        this.opponent = opponent;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.myBoard.setShips(ships);
    }

    @Override
    public String toString() {
        return this.name;
    }
}

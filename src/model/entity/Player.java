package model.entity;

import model.Board;
import model.Ship;
import type.AttackResult;
import type.Orientation;

import java.util.ArrayList;

public class Player {
    private String name;
    private Board myBoard;
    private Board opponentBoard;

    public Player(String name) {
        this.name = name;
        this.myBoard = new Board();
        this.opponentBoard = null;
        initializeShips();
    }

    public void initializeShips() {
        // Ajout des bateaux par défaut à la liste this.ships
        // À adapter en fonction
        this.myBoard.ships.add(new Cruiser());
        this.myBoard.ships.add(new BattleShip());
        this.myBoard.ships.add(new BattleShip());
        this.myBoard.ships.add(new Destroyer());
        this.myBoard.ships.add(new Destroyer());
        this.myBoard.ships.add(new Destroyer()); // Total de 16 points de vie par défaut
        this.myBoard.ships.add(new NavalMine()); // Sera évitée lors du compte des points de vie
    }

    public boolean placeShip(Ship ship, int x, int y, Orientation orientation) {
        return this.myBoard.placeShip(ship, x, y, orientation);
    }

    public AttackResult attackOpponent(int x, int y) {
        if (this.opponentBoard != null) {
            return this.opponentBoard.shootAt(x, y);
        }
        System.out.println("opponentBoard est null");
        return null;
    }

    public int getFleetHealth() {
        int totalHealth = 0;
        for (Ship ship : this.getShips()) {
            totalHealth += ship.getHealth();
        }
        return totalHealth;
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

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public ArrayList<Ship> getShips() {
        return this.myBoard.getShips();
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
    }

    public void setShips(ArrayList<Ship> ships) {
        this.myBoard.setShips(ships);
    }
}

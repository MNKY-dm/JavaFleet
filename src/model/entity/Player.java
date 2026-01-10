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
    private ArrayList<Ship> ships;

    public Player(String name) {
        this.name = name;

        // Ajout des bateaux par défaut à la liste this.ships
        this.ships.add(new Cruiser());
        this.ships.add(new BattleShip());
        this.ships.add(new BattleShip());
        this.ships.add(new Destroyer());
        this.ships.add(new Destroyer());
        this.ships.add(new Destroyer()); // Total de 16 points de vie par défaut
        this.ships.add(new NavalMine()); // Sera évitée lors du compte des points de vie
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
        for (Ship ship : ships) {
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
        ships = this.myBoard.getShips();
        return ships;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setMyBoard(int x, int y) {
        this.myBoard = new Board(x, y);
    }

    public void setOpponentBoard(Board opponentBoard) {
        // Récupérer le bateau adverse
    }

    public void setShips(ArrayList<Ship> ships) {
        this.myBoard.setShips(ships);
    }
}

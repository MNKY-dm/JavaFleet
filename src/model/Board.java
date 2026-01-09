package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int weight;
    private int height;
    private Cell[][] cells;
    private List<Ship> ships;

    public Board(int sizeX, int sizeY) { // initialiser un plateau vide en fonction de sa hauteur et de sa largeur
        this.weight = sizeX;
        this.height = sizeY;

        for (int x  = 0; x < sizeX; x++) {
            for (int y  = 0; y < sizeY; y++) {
                cells = new Cell[this.height][this.weight];
                ships = new ArrayList<>();
            }
        }
    }

    private boolean isValidCoordinates(int x, int y) {
        return x >= 0 && x < this.weight && y >= 0 && y < this.height;
    }

    public Cell getCell(int x, int y) {
        if (isValidCoordinates(x, y)) {
            return cells[x][y];
        }
        return null;
    }

    // Getters

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public List<Ship> getShips() {
        return this.ships;
    }

    // Setters

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}

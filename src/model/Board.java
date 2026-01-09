package model;

import java.util.List;

public class Board {
    private int weight;
    private int height;
    private Cell[][] cells;
    private List<Ship> ships;

    public Board(int sizeX, int sizeY, Cell[][] cells, List<Ship> ships) {
        this.weight = sizeX;
        this.height = sizeY;
        this.cells = cells;
        this.ships = ships;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCells() {
        this.cells = cells;
    }

    public void setShips() {
        this.ships = ships;
    }
}

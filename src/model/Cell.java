package model;

import model.entity.Ship;

public class Cell {
    private int x;
    private int y;
    private Ship ship;
    private boolean hasBeenAttacked;
    private boolean isHit;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.ship = null;
        this.hasBeenAttacked = false;
        this.isHit = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isHasBeenAttacked() {
        return hasBeenAttacked;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setHasBeenAttacked(boolean hasBeenAttacked) {
        this.hasBeenAttacked = hasBeenAttacked;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }
}

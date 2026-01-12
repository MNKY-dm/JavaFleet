package model;

import type.Coordinate;
import type.Orientation;

public abstract class Ship {
    protected int health;
    protected String shipType;
    protected int length;
    protected Orientation orientation;
    protected Coordinate[] positions;

    protected Ship(int length, String shipType) {
        this.length = length;
        this.shipType = shipType;
        this.health = length;
        this.positions = new Coordinate[length];
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isSunk() {
        return health <= 0;
    }

    public void onSunk() {

    }

    // Getters

    public int getHealth() {
        return this.health;
    }

    public String getShipType() {
        return this.shipType;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public int getLength() {
        return this.length;
    }

    public Coordinate[] getPositions() {
        return this.positions;
    }

    // Setters

    public void setHealth(int health) {
        this.health = health;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setPositions(Coordinate[] coords, Orientation orientation) {
        this.positions = coords;
        this.orientation = orientation;
    }
}

package model;

import model.type.Coordinate;
import model.type.Orientation;

public abstract class Ship {
    protected int health;
    protected int maxHealth;
    protected String shipType;
    protected int length;
    protected Orientation orientation;
    protected Coordinate[] positions;

    public Ship(int length, String shipType) {
        this.length = length;
        this.maxHealth = length;
        this.health = length;
        this.shipType = shipType;
        this.positions = new Coordinate[length];
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isSunk() {
        return health <= 0;
    }

    // Getters

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
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

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
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

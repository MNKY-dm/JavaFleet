package model.entity;

import model.type.Coordinate;
import model.type.Orientation;
import model.type.ShipType;

public abstract class Ship {
    protected int health;
    protected int maxHealth;
    protected ShipType shipType;
    protected int length;
    protected Orientation orientation;
    protected Coordinate[] positions;

    // Getters

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public ShipType getShipType() {
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

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setPositions(Coordinate[] positions) {
        this.positions = positions;
    }
}

package model;

import type.AttackResult;

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

    public boolean isOccupied() {
        return this.ship != null;
    }

    public type.AttackResult receiveAttack () {
        if (this.hasBeenAttacked) {
            return null;
        }

        this.hasBeenAttacked = true;

        if (ship != null) { // Si le tir touche un bateau
            this.ship.takeDamage(1);
            this.isHit = true;

            if (this.ship.isSunk()) {
                this.ship.onSunk(); // Déclencher onSunk() --> Si c'est une NavalMine, fait couler l'attaquant
                return AttackResult.SUNK; // Le bâteau coule
            }
            return AttackResult.HIT;
        }
        return AttackResult.MISS; // "Plouf"
    }

    // Getters

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Ship getShip() {
        return this.ship;
    }

    public boolean hasBeenAttacked() {
        return this.hasBeenAttacked;
    }

    public boolean isHit() {
        return this.isHit;
    }

    // Setters

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

package model.entity;

import model.Ship;

public class BattleShip extends Ship {
    private static final int LENGTH = 3;

    public BattleShip() {
        super(LENGTH, "BattleShip");
    }
}

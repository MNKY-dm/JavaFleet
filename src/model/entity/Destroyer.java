package model.entity;

import model.Ship;

public class Destroyer extends Ship {
    private static final int LENGTH = 2;

    public Destroyer() {
        super(LENGTH, "Destroyer");
    }
}

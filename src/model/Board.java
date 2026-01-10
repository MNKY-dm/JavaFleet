package model;

import type.Coordinate;
import type.Orientation;

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

    public boolean placeShip(Ship ship, int x, int y, Orientation orientation) { // Fonction qui permet de placer un bateau sur le plateau

        // Vérifier si le bateau ne dépasse pas le bord droit du plateau (pas besoin de vérifier si ça dépasse du côté gauche car aucune valeur négative ne sera rentrée)
        if (orientation == Orientation.HORIZONTAL) {
            if (x + ship.getLength() > this.weight) {
                return false;
            }
        // Idem pour le côté inférieur
        } else {
            if (y + ship.getLength() > this.height) {
                return false;
            }
        }

        Coordinate[] positions = new Coordinate[ship.getLength()]; // crée un tableau vide dont le nombre de cases est égal à la taille du bateau

        // Vérifier si le bateau ne superpose pas un autre bateau
        for (int i = 0; i < ship.getLength(); i++) {
            int px = (orientation == Orientation.HORIZONTAL) ? x + i : x;
            int py = (orientation == Orientation.VERTICAL) ? y + i : y;

            if (cells[px][py].isOccupied()) {
                System.out.println("Chevauchement impossible");
                return false;  // Autre bateau détecté
            }
            positions[i] = new Coordinate(px, py);
        }

        // Si tout est ok, définir la position (coordonnées et orientation) du bateau
        ship.setPositions(positions, orientation);

        // Placer le bateau dans chaque cellule qui composent la position du bateau
        for (Coordinate p : positions) {
            cells[p.getX()][p.getY()].setShip(ship);
        }
        ships.add(ship);

        return true;
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

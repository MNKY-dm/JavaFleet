package model;

import type.AttackResult;
import type.Coordinate;
import type.Orientation;

import java.util.ArrayList;

public class Board {
    private int width;
    private int height;
    private Cell[][] cells;
    public ArrayList<Ship> ships;

    public Board() { // initialiser un plateau vide en fonction de sa hauteur et de sa largeur
        this.width = 10;
        this.height = 10;
        cells = new Cell[width][height];

        for (int x  = 0; x < width; x++) {
            for (int y  = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
        ships = new ArrayList<>();
    }

    private boolean isValidCoordinates(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    public Cell getCell(int x, int y) {
        if (isValidCoordinates(x, y)) {
            return cells[x][y];
        }
        return null;
    }

    public boolean canPlaceShip(Ship ship, int x, int y, Orientation orientation) {
        // Vérifier si le bateau ne dépasse pas le bord droit du plateau (pas besoin de vérifier si ça dépasse du côté gauche, car aucune valeur négative ne sera rentrée)
        if (orientation == Orientation.HORIZONTAL) {
            if (x + ship.getLength() > this.width) {
                return false;
            }
            // Idem pour le côté inférieur
        } else {
            if (y + ship.getLength() > this.height) {
                return false;
            }
        }

        Coordinate[] positions = calculatePositions(ship, x, y, orientation); // crée un tableau vide dont le nombre de cases est égal à la taille du bateau

        // Vérifier si le bateau ne superpose pas un autre bateau
        for (int i = 0; i < ship.getLength(); i++) {

            if (cells[positions[i].getX()][positions[i].getY()].isOccupied()) {
                System.out.println("Chevauchement impossible");
                return false;  // Autre bateau détecté
            }
        }
        return true;
    }

    public boolean placeShip(Ship ship, int x, int y, Orientation orientation) {
        if (canPlaceShip(ship, x, y, orientation)) {
            // Recalculer les positions
            Coordinate[] positions = calculatePositions(ship, x, y, orientation);

            ship.setPositions(positions, orientation);
            for (Coordinate p : positions) {
                cells[p.getX()][p.getY()].setShip(ship);
            }
            ships.add(ship);
            return true;
        }
        return false;
    }

    private Coordinate[] calculatePositions(Ship ship, int x, int y, Orientation orientation) {
        Coordinate[] positions = new Coordinate[ship.getLength()];
        for (int i = 0; i < ship.getLength(); i++) {
            int px = (orientation == Orientation.HORIZONTAL) ? x + i : x;
            int py = (orientation == Orientation.VERTICAL) ? y + i : y;
            positions[i] = new Coordinate(px, py);
        }
        return positions;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

    public boolean areAllShipsPlaced() {
        if (this.ships.isEmpty()) { // Vérifier si la liste des bateaux est vide
            return false;
        }

        for (Ship ship : this.ships) { // Vérifier si tous les bateaux de la liste ont été placés dans placeShip
            if (ship.positions == null) {
                return false;
            }
        }
        return true;
    }

    // Méthode qui permet de tirer sur une case
    public AttackResult shootAt(int x, int y) {

        // Vérifier si la case visée est bien une case présente dans le plateau
        if (!isValidCoordinates(x, y)) {
            return null;
        }

        Cell cell = cells[x][y];
        // Vérifier si la case visée a déjà été attaquée
        if (cell.hasBeenAttacked()) {
            return null;
        }

        return cell.receiveAttack(); // Retourne un AttackResult selon le résultat de l'attaque sur cette case
    }

    // Méthode qui permet de vérifier si tous les bateaux sont coulés
    public boolean areAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Getters

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }

    // Setters

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
}

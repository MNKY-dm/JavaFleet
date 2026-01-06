package model.entity;

import model.Board;

public class Player {
    private String name;
    private Board board;
    private Board opponentBoard;

    public Player(String name, Board board, Board opponentBoard) {
        this.name = name;
        this.board = board;
        this.opponentBoard = opponentBoard;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }
}

package com.example.models;

public class CellOnTheBord {

    private Pieces pieces;
    private int lineCoordinate;
    private int columnCoordinate;

    public CellOnTheBord(Pieces pieces, int lineCoordinate, int columnCoordinate) {
        this.pieces = pieces;
        this.lineCoordinate = lineCoordinate;
        this.columnCoordinate = columnCoordinate;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }

    public int getLineCoordinate() {
        return lineCoordinate;
    }

    public void setLineCoordinate(int lineCoordinate) {
        this.lineCoordinate = lineCoordinate;
    }

    public int getColumnCoordinate() {
        return columnCoordinate;
    }

    public void setColumnCoordinate(int columnCoordinate) {
        this.columnCoordinate = columnCoordinate;
    }
}

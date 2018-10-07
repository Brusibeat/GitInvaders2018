package org.academiadecodigo.bootcamp.GameEngine;

public class Position extends Grid {

    private double row;
    private double col;
    private int CELLSIZE = 10;

    public Position(double row, double col){
        this.row = row;
        this.col = col;
    }


    public double getRow(){
        return row;
    }

    public double getCol(){
        return col;
    }

    public double getWidth(){
        return col * CELLSIZE;
    }

    public double getHeight(){
        return row * CELLSIZE;
    }

    public int getCELLSIZE() {
        return CELLSIZE;
    }

    public void setCol(double col) {
        this.col = col;
    }

    public void setRow(double row) {
        this.row = row;
    }
}

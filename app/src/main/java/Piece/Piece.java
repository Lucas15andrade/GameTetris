package Piece;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Piece implements Padrao{

    //Coordenadas das peças
    ArrayList<int[]> points; // = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));

    public Piece(ArrayList<int[]> points) {
        this.points = points;
    }

    public Piece() {
    }

    public ArrayList<int[]> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<int[]> points) {
        this.points = points;
    }

    public void left(){

    };
}

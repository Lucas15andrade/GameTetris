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

    public void move(){
        points.get(0)[0] += 1;
        points.get(1)[0] += 1;
        points.get(2)[0] += 1;
        points.get(3)[0] += 1;
    }

    public void left(){
        points.get(0)[1] -= 1;
        points.get(1)[1] -= 1;
        points.get(2)[1] -= 1;
        points.get(3)[1] -= 1;
    };

    public void right(){
        points.get(0)[1] += 1;
        points.get(1)[1] += 1;
        points.get(2)[1] += 1;
        points.get(3)[1] += 1;
    }
}

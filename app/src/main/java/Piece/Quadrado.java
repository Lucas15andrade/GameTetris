package Piece;

import java.util.ArrayList;
import java.util.Arrays;

import Piece.Piece;

public class Quadrado extends Piece {

    public Quadrado() {

        /*
       ArrayList <int []> quadrado = new ArrayList<>();
       int vetor1[] = {1, 1}; // alocação de espaço para vetor
       int vetor2[] = {1, 2}; // alocação de espaço para vetor
       int vetor3[] = {2, 1}; // alocação de espaço para vetor
       int vetor4[] = {2, 2}; // alocação de espaço para vetor

       quadrado.add(vetor1);
       quadrado.add(vetor2);
       quadrado.add(vetor3);
       quadrado.add(vetor4);
       */

        points = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        points.get(0)[0] = 3;
        points.get(0)[1] = 10;

        points.get(1)[0] = 3;
        points.get(1)[1] = 11;

        points.get(2)[0] = 4;
        points.get(2)[1] = 10;

        points.get(3)[0] = 4;
        points.get(3)[1] = 11;
    }

    @Override
    public void rotate() {

    }

//    public void move(){
//        points.get(0)[0] += 1;
//        points.get(1)[0] += 1;
//        points.get(2)[0] += 1;
//        points.get(3)[0] += 1;
//    }


}

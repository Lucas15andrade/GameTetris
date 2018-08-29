package Piece;

import java.util.ArrayList;
import java.util.Arrays;

import Piece.Piece;

public class Casa extends Piece implements Padrao {

    public Casa() {
        points = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        points.get(0)[0] = 2;
        points.get(0)[1] = 10;

        points.get(1)[0] = 3;
        points.get(1)[1] = 9;

        points.get(2)[0] = 3;
        points.get(2)[1] = 11;

        points.get(3)[0] = 3;
        points.get(3)[1] = 10;
    }

    @Override
    public void rotate() {

    }
}

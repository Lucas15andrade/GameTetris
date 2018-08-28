package com.andradecoder.gametetris;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Piece.Casa;
import Piece.Linha;
import Piece.Piece;
import Piece.Quadrado;

public class BoardActivity extends AppCompatActivity {

    ImageView[][] board;
    int[][] references;
    GridLayout layout;
    int n1 = 35, n2 = 25;

    Handler handler = new Handler();
    Boolean running = true;
    TextView pontos;
    ImageView imgv;
    Context c = this;

    Piece peca;
    int numeroDaPeca;
    int cont = 1;
    int cordX = 0;
    int cordY = 0;

    //Peças

    ArrayList<int[]> quad;
    ArrayList<int[]> ele;
    ArrayList<int[]> casa;
    ArrayList<ArrayList> pieces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        pontos = (TextView) findViewById(R.id.pontos);
        imgv = (ImageView) findViewById(R.id.imageView);

        pieces = new ArrayList<>();
        preencherMatriz();

        //Tabuleiro
        //Criando uma matriz de imagens (ImageView)
        board = new ImageView[n1][n2];
        //Grid
        //Criando um Grid Layout e buscando na classe R
        layout = findViewById(R.id.gridBoard);
        //Setando tamanho das linhas e colunas
        layout.setRowCount(n1);
        layout.setColumnCount(n2);

        //Criando o Layout Inflater
        LayoutInflater inflater = LayoutInflater.from(c);

        //Percorrendo uma matriz do tamanho do grid e da matriz de imagens.
        for (int x = 0; x < layout.getRowCount(); x++) {
            for (int y = 0; y < layout.getColumnCount(); y++) {
                //O tabuleiro na posição X,Y recebe uma ImageView inflado.
                board[x][y] = (ImageView) inflater.inflate(R.layout.inflate_image_view, layout, false);

                if (x == 0) {
                    board[x][y].setImageResource(R.drawable.gray);
                    references[x][y] = 99;
                } else if (x == n1 - 1) {
                    board[x][y].setImageResource(R.drawable.gray);
                    references[x][y] = 99;
                } else if (y == 0) {
                    board[x][y].setImageResource(R.drawable.gray);
                    references[x][y] = 99;
                } else if (y == n2 - 1) {
                    board[x][y].setImageResource(R.drawable.gray);
                    references[x][y] = 99;
                }
                //Adicionando ao Grid Layout o conteúdo no tabuleiro de imagens na posição X,Y.
                layout.addView(board[x][y]);
            }
        }

        Log.i("deb", "Tamanho de linhas: " + layout.getRowCount());
        Log.i("deb", "Tamanho de colunas: " + layout.getColumnCount());
        Log.i("imagem", "Tamanho de colunas: " + layout.getColumnCount());

        //Log.i("deb", "Tamanho de colunas: " + layout.getColumnCount());
        piece();

        //Implementação dos botões
        ImageButton buttonLeft = findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Botao", "Botão eaquerdo");

                if (numeroDaPeca == 0) {
//                    references[peca.getPoints().get(0)[0]][peca.getPoints().get(0)[1]] = 0;
//                    references[peca.getPoints().get(1)[0]][peca.getPoints().get(1)[1]] = 0;
                    references[peca.getPoints().get(2)[0]][peca.getPoints().get(2)[1]] = 0;
                    references[peca.getPoints().get(3)[0]][peca.getPoints().get(3)[1]] = 0;

                    peca.getPoints().get(0)[1] -= 1;
                    peca.getPoints().get(1)[1] -= 1;
                    peca.getPoints().get(2)[1] -= 1;
                    peca.getPoints().get(3)[1] -= 1;

                } else if (numeroDaPeca == 1) {

                    references[peca.getPoints().get(0)[0]][peca.getPoints().get(0)[1]] = 0;
                    references[peca.getPoints().get(1)[0]][peca.getPoints().get(1)[1]] = 0;
//                    references[peca.getPoints().get(2)[0]][peca.getPoints().get(2)[1]] = 0;
//                    references[peca.getPoints().get(3)[0]][peca.getPoints().get(3)[1]] = 0;

                    peca.getPoints().get(0)[1] -= 1;
                    peca.getPoints().get(1)[1] -= 1;
                    peca.getPoints().get(2)[1] -= 1;
                    peca.getPoints().get(3)[1] -= 1;

                } else if (numeroDaPeca == 2) {
                    //peca = new Casa();
                }
            }
        });
    }


    public void piece() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (running) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //For que percorre a matriz de referência e preenche como deve ser preenchida
                            for (int i = 0; i < n1; i++) {
                                for (int j = 0; j < n2; j++) {
                                    if (references[i][j] == 1) {
                                        board[i][j].setImageResource(R.drawable.green);
                                    } else if (references[i][j] == 0) {
                                        board[i][j].setImageResource(R.drawable.black);
                                    }
                                }
                            }

                            //Se for a primeira vez que estiver rodando
                            if (cont == 1) {

                                //Trecho que cria a peça aleatóriamente
                                numeroDaPeca = createRandom();
                                if (numeroDaPeca == 0) {
                                    peca = new Quadrado();
                                } else if (numeroDaPeca == 1) {
                                    peca = new Linha();
                                } else if (numeroDaPeca == 2) {
                                    peca = new Casa();
                                }//Implementar outros posteriormente

                                Log.i("random", "Cont um");
                            } else {
                                if (((peca.getPoints().get(0)[0] + 1) >= n1) ||
                                        ((peca.getPoints().get(1)[0] + 1) >= n1) ||
                                        ((peca.getPoints().get(2)[0] + 1) >= n1) ||
                                        ((peca.getPoints().get(3)[0] + 1) >= n1)) {

                                    cont = 0;
                                }else{
                                    if(peca instanceof Linha){
                                        references[peca.getPoints().get(0)[0]][peca.getPoints().get(0)[1]] = 1;
                                        references[peca.getPoints().get(1)[0]][peca.getPoints().get(1)[1]] = 1;
                                        references[peca.getPoints().get(2)[0]][peca.getPoints().get(2)[1]] = 1;
                                        references[peca.getPoints().get(3)[0]][peca.getPoints().get(3)[1]] = 1;

                                        peca.getPoints().get(0)[0] += 1;
                                        peca.getPoints().get(1)[0] += 1;
                                        peca.getPoints().get(2)[0] += 1;
                                        peca.getPoints().get(3)[0] += 1;

                                        references[peca.getPoints().get(0)[0]-1][peca.getPoints().get(0)[1]] = 0;
                                    }else if(peca instanceof Quadrado){

                                        references[peca.getPoints().get(0)[0]][peca.getPoints().get(0)[1]] = 1;
                                        references[peca.getPoints().get(1)[0]][peca.getPoints().get(1)[1]] = 1;
                                        references[peca.getPoints().get(2)[0]][peca.getPoints().get(2)[1]] = 1;
                                        references[peca.getPoints().get(3)[0]][peca.getPoints().get(3)[1]] = 1;

                                        peca.getPoints().get(0)[0] += 1;
                                        peca.getPoints().get(1)[0] += 1;
                                        peca.getPoints().get(2)[0] += 1;
                                        peca.getPoints().get(3)[0] += 1;

                                        references[peca.getPoints().get(0)[0]-2][peca.getPoints().get(0)[1]] = 0;
                                        references[peca.getPoints().get(0)[0]-2][peca.getPoints().get(1)[1]] = 0;
                                        references[peca.getPoints().get(1)[0]-2][peca.getPoints().get(0)[1]] = 0;
                                        references[peca.getPoints().get(1)[1]-2][peca.getPoints().get(1)[1]] = 0;
                                    }
                                }

                            }

                            cont++;

                        }
                    });
                }
            }
        }).start();

    }

    public void createPiece() {

    }


    public int createRandom() {
        //Criando número random
        Random r = new Random();
        int numberRandom = r.nextInt(1);
        Log.i("random", "Número random: " + numberRandom);
        return numberRandom;
    }

    public void limparMatriz() {

    }


    //Método para preencher a matriz
    public void preencherMatriz() {
        references = new int[n1][n2];
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                references[i][j] = 0;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
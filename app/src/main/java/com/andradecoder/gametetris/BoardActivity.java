package com.andradecoder.gametetris;

import android.content.Context;
import android.content.SharedPreferences;
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
    Boolean find = false;
    TextView pontos;
    ImageView imgv;
    Context c = this;

    Piece peca;
    int numeroDaPeca;
    int cont = 1;
    int valorThread;
    int valorPecas;

    ArrayList<Piece> pecasFundo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        int valorDificuldade = config.getInt("dificuldade", 2);
        valorPecas = config.getInt("numeroPecas", 4);

        if (valorDificuldade == 1) {
            valorThread = 300;
        } else if (valorDificuldade == 2) {
            valorThread = 150;
        } else if (valorDificuldade == 3) {
            valorThread = 100;
        }


        Log.i("dificuldade", "" + valorThread);
        Log.i("pecas", "" + valorPecas);

        pontos = (TextView) findViewById(R.id.pontos);
        imgv = (ImageView) findViewById(R.id.imageView);

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
                } else if (x == n1 - 1) {
                    board[x][y].setImageResource(R.drawable.gray);
                } else if (y == 0) {
                    board[x][y].setImageResource(R.drawable.gray);
                } else if (y == n2 - 1) {
                    board[x][y].setImageResource(R.drawable.gray);
                }
                //Adicionando ao Grid Layout o conteúdo no tabuleiro de imagens na posição X,Y.
                layout.addView(board[x][y]);
            }
        }

        piece();

        //Implementação dos botões
        ImageButton buttonLeft = findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Botao", "Botão eaquerdo");

                if (numeroDaPeca == 0) {

                    peca.left();

                } else if (numeroDaPeca == 1) {

                    peca.left();

                } else if (numeroDaPeca == 2) {
                    peca.left();
                }
            }
        });

        ImageButton buttonRight = findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numeroDaPeca == 0) {

                    peca.right();

                } else if (numeroDaPeca == 1) {

                    peca.right();

                } else if (numeroDaPeca == 2) {
                    peca.right();
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
                        Thread.sleep(valorThread);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //Início da Thread
                            //For's que limpam a matriz de ImageView
                            //LIMPAR
                            limparMatriz();


                            //PINTAR
                            //For que percorre todas as peças já criadas e "pinta" o tabuleiro(ImageView).
                            for (Piece p : pecasFundo) {
                                //Log.i("TANIRO2", "x = "+ p.getPoints().get(0)[0]+ " y = "+ p.getPoints().get(0)[1] + " size: " + pecasFundo.size());
                                //Log.i("andrade","Tamanho:"+ p.getPoints().size());
                                //Para cada peça eu tenho um array de vetores
                                //For que percorre esse array para cada peça já salva
                                for (int i = 0; i < p.getPoints().size(); i++) {
                                    //Pega a posição X e Y de cada peça e coloca no na matriz de Image View
                                    board[p.getPoints().get(i)[0]][p.getPoints().get(i)[1]].setImageResource(R.drawable.green);
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

                                //existPiece();
                                //Se a próxima posição for maior que a borda
                                if (((peca.getPoints().get(0)[0] + 1) >= n1) ||
                                        ((peca.getPoints().get(1)[0] + 1) >= n1) ||
                                        ((peca.getPoints().get(2)[0] + 1) >= n1) ||
                                        ((peca.getPoints().get(3)[0] + 1) >= n1) || existPiece()) {

                                    Log.i("andrade2", "Tamanho das peças que estão no fundo: " + pecasFundo.size());
                                    pecasFundo.add(peca);
                                    cont = 0;
                                } else {

                                    // (!find) {
                                    if (peca instanceof Linha) {
                                        edge();
                                    } else if (peca instanceof Quadrado) {
                                        edge();
                                    } else if (peca instanceof Casa) {
                                        edge();
                                    }
                                    // }

                                }

                            }

                            //Além de pintar o fundo eu preciso pintar o movimento da peça;
                            //Quando ela chegar no fundo, é adicionada ao Array de peças do fundo
                            for (int i = 0; i < peca.getPoints().size(); i++) {
                                board[peca.getPoints().get(i)[0]][peca.getPoints().get(i)[1]].setImageResource(R.drawable.green);
                            }
                            cont++;
                        }
                    });
                }
            }
        }).start();

    }

    public int createRandom() {
        //Criando número random
        Random r = new Random();
        //Passar o valorPeças depois
        int numberRandom = r.nextInt(valorPecas);
        Log.i("random", "Número random: " + numberRandom);
        return 0;
    }


    public void limparMatriz() {
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                board[i][j].setImageResource(R.drawable.black);
                if (i == 0) {
                    board[i][j].setImageResource(R.drawable.gray);
                } else if (i == n1 - 1) {
                    board[i][j].setImageResource(R.drawable.gray);
                } else if (j == 0) {
                    board[i][j].setImageResource(R.drawable.gray);
                } else if (j == n2 - 1) {
                    board[i][j].setImageResource(R.drawable.gray);
                }
                // }
            }
        }
    }

    public void edge() {
        if ((peca.getPoints().get(3)[0]) >= n1 - 2 ||
                peca.getPoints().get(3)[1] >= n1 - 2 /*||
                peca.getPoints().get(2)[0] >= n1 -2 ||
                peca.getPoints().get(2)[1] >= n1 -2 */) {
            pecasFundo.add(peca);
            cont = 0;
        } else {
            peca.move();
        }

//        Log.i("TANIRO", " q isso " + (peca.getPoints().get(0)[0] + 1));
//        if ((peca.getPoints().get(3)[0]) >= n1 - 2) {
//            Log.i("TANIRO", "add");
//            pecasFundo.add(peca);
//            //adicionar os pontos da peça na matriz de referencias.
//            cont = 0;
//
//        } else {
//            peca.move();
//        }
    }

    public Boolean existPiece() {
        for (Piece p : pecasFundo) {
            for (int x = 0; x < p.getPoints().size(); x++) {
                //2-0
                //2-1
                //Acredito que esse seja para o quadrado
                //Fazer Ifzão antes para verificar qual é a peça do momemento e fazer as devidas comparações
//                if ((((peca.getPoints().get(2)[0] + 2) == p.getPoints().get(2)[0]) &&
//                        (peca.getPoints().get(2)[1]) == p.getPoints().get(2)[1]) || ((
//                        peca.getPoints().get(3)[0] + 2) == p.getPoints().get(0)[0] &&
//                        peca.getPoints().get(3)[0] == p.getPoints().get(3)[0])
//                        ){
//                    Log.i("encontrou", "Encontrou uma peça");
//                    //pecasFundo.add(peca);
//                    //find = true;
//                    //cont = 0;
//                    return true;
//                }

                if (peca instanceof Quadrado) {
                    if((peca.getPoints().get(3)[0]+2 == p.getPoints().get(3)[0] && peca.getPoints().get(3)[1] == p.getPoints().get(3)[1]) ||
                            peca.getPoints().get(2)[0] +2 == p.getPoints().get(2)[0] && peca.getPoints().get(2)[1] == p.getPoints().get(2)[1]){
                        Log.i("encontrou", "Encontrou uma peça");
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
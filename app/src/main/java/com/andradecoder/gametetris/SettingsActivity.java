package com.andradecoder.gametetris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    int valorDificuldade = 1;
    int valorPecas = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        RadioGroup dificuldade = findViewById(R.id.radioGroup);
        RadioGroup numeroPecas = findViewById(R.id.radioGroup2);

        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = config.edit();

        //Obtendo o valor da dificuldade
        if (R.id.buttonFacil == dificuldade.getCheckedRadioButtonId()) {
            valorDificuldade = 1;
        } else if (R.id.buttonNormal == dificuldade.getCheckedRadioButtonId()) {
            valorDificuldade = 2;
        } else if (R.id.buttonDificil == dificuldade.getCheckedRadioButtonId()) {
            valorDificuldade = 3;
        }

        //Obtendo o número de peças
        if (R.id.quant2 == numeroPecas.getCheckedRadioButtonId()) {
            valorPecas = 2;
        } else if (R.id.quant4 == numeroPecas.getCheckedRadioButtonId()) {
            valorPecas = 4;
        } else if (R.id.quant7 == numeroPecas.getCheckedRadioButtonId()) {
            valorPecas = 7;
        }

        editor.putInt("dificuldade", valorDificuldade);
        editor.putInt("numeroPecas", valorPecas);
        editor.commit();
    }
}

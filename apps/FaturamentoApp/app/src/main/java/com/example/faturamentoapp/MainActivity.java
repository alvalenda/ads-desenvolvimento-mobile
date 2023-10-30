package com.example.faturamentoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ARQUIVO_MEUS_DADOS = "MeusDados";

    private TextView mTextViewSaldo;
    private EditText mEditTextValor;
    private NumberPicker numberPicker;
    private RadioGroup mRadioGroup;
    private Button mButton;
    private Button mButtonTitulo;

    private NumberPicker.OnValueChangeListener valorAlteradoListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int valorAntigo, int valorAtual) {

            // Ao selecionar um ano diferente no NumberPicker, exibe o saldo para o usuário
            exibirSaldo(valorAtual);

        }
    };

    private void adicionarValor(int ano, float valor) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);
        float novoValor = valorAtual + valor;
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
    }

    private void excluirValor(int ano, float valor) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.
                valueOf(ano), 0);
        float novoValor = valorAtual - valor;
        if (novoValor < 0) {
            novoValor = 0;
        }
        sharedPreferences.edit()
                .putFloat(String.valueOf(ano), novoValor)
                .apply();
    }

    private void exibirSaldo(int ano) {
        SharedPreferences sharedPreferences =
                getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        float saldo = sharedPreferences.getFloat(String.valueOf(ano), 0);
        mTextViewSaldo.setText(String.format("R$ %f", saldo));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewSaldo = findViewById(R.id.textView2);
        mEditTextValor = findViewById(R.id.editTextTextPersonName);
        mRadioGroup = findViewById(R.id.radioGroup2);
        mButton = findViewById(R.id.button2);
        mButtonTitulo = findViewById(R.id.button3);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(2015);
        numberPicker.setMaxValue(2030);

        // Registra o Listener para alteração de valores no NumberPicker
        numberPicker.setOnValueChangedListener(valorAlteradoListener);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditTextValor.getText().toString().isEmpty()) {

                    // Recupera o valor digitado e o converte para float
                    float valor = Float.parseFloat(mEditTextValor.getText().toString());

                    // Recupera o ano selecionado
                    int ano = numberPicker.getValue();

                    // Verifica qual RadioButton está selecionado
                    // Recuperamos o ID do RadioButton que está selecionado e comparamos com o ID dos RadioButtons que criamos no layout
                    switch (mRadioGroup.getCheckedRadioButtonId()){
                        case R.id.radioButton7:
                            adicionarValor(ano, valor);
                            break;
                        case R.id.radioButton6:
                            excluirValor(ano, valor);
                            break;
                    }
                    exibirSaldo(ano);
                }
            }
        });

        mButtonTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalizarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_MEUS_DADOS, Context.MODE_PRIVATE);
        String nomeFantasia = sharedPreferences.getString("nomeFantasia", null);
        if(nomeFantasia!=null){
            setTitle(nomeFantasia);
        }

        int ano = numberPicker.getValue();
        exibirSaldo(ano);
    }
}
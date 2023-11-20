package com.auladevmobile.lojaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Produto> mListProduto;
    RecyclerView rvListagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Exemplificamos a criação de três objetos Produto para exibir na listagem
        mListProduto = new ArrayList<>();
        mListProduto.add(new Produto("Smartphone",1800f));
        mListProduto.add(new Produto("Tablet",3600f));
        mListProduto.add(new Produto("tv4K",6000f));
        mListProduto.add(new Produto("Notebook",9000f));
        mListProduto.add(new Produto("Smartwatch",1500f));
        mListProduto.add(new Produto("Fone Bluetooth",700f));
        mListProduto.add(new Produto("Monitor",1500f));
        mListProduto.add(new Produto("Teclado",300f));
        mListProduto.add(new Produto("Mouse",250f));
        mListProduto.add(new Produto("Powerbank",150f));
        mListProduto.add(new Produto("PC Desktop",9000f));
        mListProduto.add(new Produto("Smartband",200));
        mListProduto.add(new Produto("Caixa de som",800f));
        mListProduto.add(new Produto("Monitor 4k",5500f));
        mListProduto.add(new Produto("Teclado gamer",740f));
        mListProduto.add(new Produto("Video Game",2250f));
        mListProduto.add(new Produto("Micro SD 512GB",450f));

        //instancia o RecyclerView
        rvListagem = findViewById(R.id.rvListagem);
        //define um tamanho fixo no layout
        rvListagem.setHasFixedSize(true);

        // Define como os itens serão exibidos no RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvListagem.setLayoutManager(layoutManager);

        // Adiciona uma linha entre cada item da listagem
        RecyclerView.ItemDecoration itemDecoration
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvListagem.addItemDecoration(itemDecoration);

        //define adapter para recycler view
        Adaptador adaptador = new Adaptador();
        rvListagem.setAdapter(adaptador);

        //atualiza todos os itens para o adaptador
        adaptador.atualizarListagemCompleta(mListProduto);
    }
}
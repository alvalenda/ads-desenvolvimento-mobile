package com.auladevmobile.lojaapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.NossoViewHolder>{

    private List<Produto> mListProduto;

    @NonNull
    @Override
    public NossoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Declaramos um objeto View com o layout que será exibido para cada item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itens, parent, false);
        // Declaramos um objeto NossoViewHolder e passamos como parâmetro o objeto View recém-criado
        NossoViewHolder viewHolder = new NossoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NossoViewHolder holder, int position) {
        // Recuperamos um Produto do objeto List de acordo com a posição recebida no parâmetro
        Produto produto = mListProduto.get(position);
        // Exibimos os dados do item para o usuário através das referências criadas no NossoViewHolder
        holder.mTextViewDescricao.setText(produto.getDescricao());
        holder.mTextViewPreco.setText(String.format("R$ %.2f", produto.getPreco()));
    }

    @Override
    public int getItemCount() {
        // Este método retorna a quantidade de itens que deverá ser exibida
        if (mListProduto == null) return 0;

        return mListProduto.size();
    }

    public class NossoViewHolder extends RecyclerView.ViewHolder{

        TextView mTextViewDescricao, mTextViewPreco;

        public NossoViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewDescricao = itemView.findViewById(R.id.textDescricao);
            mTextViewPreco = itemView.findViewById(R.id.textPreco);

        }
    }

    public void atualizarListagemCompleta(List<Produto> mListProduto) {
        // Atualiza a listagem do Adaptador
        this.mListProduto = mListProduto;
        // Notifica o Adaptador para atualizar a listagem completa
        notifyDataSetChanged();
    }

    public void inserirItemNaListagem(int position, List<Produto> mListProduto) {
        this.mListProduto = mListProduto;
        // Notifica o Adaptador para inserir o item de acordo com a posição recebida no parâmetro
        notifyItemInserted(position);
    }

    public void excluirItemDaListagem(int position) {
        mListProduto.remove(position);
        // Notifica o Adaptador para remover o item de acordo com a posição recebida no parâmetro
        notifyItemRemoved(position);
    }
    public void atualizarItemDaListagem(int position, List<Produto> mListProduto) {
        this.mListProduto = mListProduto;
        // Notifica o Adaptador para atualizar os dados do item de acordo com a posição recebida no parâmetro
        notifyItemChanged(position);
    }
}

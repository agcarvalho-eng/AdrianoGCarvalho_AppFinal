package com.example.adrianogcarvalho_appfinal.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.models.Produto;

import java.util.List;

public class MeuAdapter extends RecyclerView.Adapter<MeuAdapter.MeuViewHolder> {
    private List<Produto> produtos;

    public MeuAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public MeuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflando o layout para cada item da lista
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_layout_produto, parent, false);

        // Criando e retornando o ViewHolder
        return new MeuViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MeuViewHolder holder, int position) {
        // Definindo os dados para cada item da lista
        Produto produto = produtos.get(position);

        // Definindo o nome do produto
        holder.nomeProduto.setText("Produto: " + produto.getNome());

        // Definindo o valor do PC do produto
        holder.pcProduto.setText(String.valueOf("Valor: " + produto.getValor()));
    }

    @Override
    public int getItemCount() {
        // Retornando a quantidade de itens na lista
        return produtos.size();
    }

    // Definindo o ViewHolder
    public static class MeuViewHolder extends RecyclerView.ViewHolder {
        TextView nomeProduto;
        TextView pcProduto;

        public MeuViewHolder(View itemView) {
            super(itemView);

            // Inicializando as views do item
            nomeProduto = itemView.findViewById(R.id.textViewNomeProduto);
            pcProduto = itemView.findViewById(R.id.textViewPCProduto);
        }
    }
}

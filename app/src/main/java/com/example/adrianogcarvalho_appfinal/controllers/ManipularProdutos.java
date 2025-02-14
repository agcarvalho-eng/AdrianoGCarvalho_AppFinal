package com.example.adrianogcarvalho_appfinal.controllers;

import android.content.Context;

import androidx.room.Room;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.models.DAO.ProdutoDao;
import com.example.adrianogcarvalho_appfinal.models.MyDatabase;
import com.example.adrianogcarvalho_appfinal.models.Produto;
import com.example.adrianogcarvalho_appfinal.models.Usuario;

public class ManipularProdutos {
    private MyDatabase dbProduto;
    private ProdutoDao produtoDao;

    public ManipularProdutos(Context context) {
        // Inicializando o BD
        dbProduto = Room.databaseBuilder(context, MyDatabase.class, "pegada_carbono")
                .fallbackToDestructiveMigration()
                .build();

        // Inicializando o produtoDao
        produtoDao = dbProduto.produtoDao();
    }
    public void inserirProduto(Produto produto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbProduto.produtoDao().inserirProduto(produto);
            }
        }).start();
    }

    // Método para inserir a lista de produtos no BD
    public void inserirListaProdutos(Context context) {

        // Lendo o string-array do arquivo strings.xml
        String[] produtos = context.getResources().getStringArray(R.array.produtos); // Array de strings com os produtos

        // Iterando sobre as strings do array
        for (int i = 0; i < produtos.length; i++) {
            // Dividindo cada string para obter a descrição e o valor nutricional
            String produtoString = produtos[i];
            // Dividindo a string usando vírgula como separador
            String[] separando = produtoString.split(",");

            // Pegando a descrição do produto e o valor da PC
            String descricao = separando[0];
            double PC = Double.parseDouble(separando[1]);

            // Criando o objeto Produto
            Produto produto = new Produto(descricao, PC);

            // Criando uma nova thread para inserir o produto no BD
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Inserindo o produto no BD
                    produtoDao.inserirListaProdutos(produto);
                }
            }).start();
        }
    }

    public void listarTodosProdutos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbProduto.produtoDao().listarTodosProdutos();
            }
        }).start();
    }

    public Produto obterProdutoId(int idProduto) {
        final Produto[] Produto = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                Produto[0] = dbProduto.produtoDao().obterProdutoId(idProduto);
            }
        }).start();
        return Produto[0];
    }
}

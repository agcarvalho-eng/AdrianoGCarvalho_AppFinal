package com.example.adrianogcarvalho_appfinal.controllers;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.adrianogcarvalho_appfinal.models.DAO.ProdutoDao;
import com.example.adrianogcarvalho_appfinal.models.DAO.UsuarioProdutoDao;
import com.example.adrianogcarvalho_appfinal.models.MyDatabase;
import com.example.adrianogcarvalho_appfinal.models.Produto;
import com.example.adrianogcarvalho_appfinal.models.UsuarioProduto;

import java.util.ArrayList;
import java.util.List;

public class ManipularUsuarioProduto {

    private MyDatabase dbProduto;
    private ProdutoDao produtoDao;
    private UsuarioProdutoDao usuarioProdutoDao;
    private double valorPegada = 0;

    public ManipularUsuarioProduto(Context context) {
        // Inicializando o BD
        dbProduto = Room.databaseBuilder(context, MyDatabase.class, "pegada_carbono")
                .fallbackToDestructiveMigration()
                .build();

        // Inicializando as classes Dao necessárias
        produtoDao = dbProduto.produtoDao();
        usuarioProdutoDao = dbProduto.usuarioProdutoDao();
    }

    // Método para listar todos os IDs dos produtos associados a um usuário
    public List<Integer> listarIdProdutosUsuario(int idUsuario) {
        final List<Integer>[] idProdutos = new List[]{new ArrayList<>()};

        // Criando uma thread para realizar a consulta
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Obtendo os relacionamentos de produtos do usuário
                List<UsuarioProduto> usuarioProdutos = usuarioProdutoDao.obterProdutosDeUsuario(idUsuario);
                // Iterando sobre a lista de UsuarioProduto e adicionando os IDs dos produtos
                for (UsuarioProduto usuarioProduto : usuarioProdutos) {
                    idProdutos[0].add(usuarioProduto.getId_produto());
                }
            }
        });

        thread.start();
        try {
            // Esperando até que a thread termine de buscar os dados
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retornando a lista de IDs dos produtos
        return idProdutos[0];
    }

    // Método para listar os valores dos produtos associados a um usuário, dado uma lista de ids de produtos
    public double calcularPegadaUsuario(List<Integer> idProdutos) {
        //final List<Double>[] valoresProdutos = new List[]{new ArrayList<>()};

        // Criando uma thread para realizar a consulta
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Iterando sobre a lista de IDs dos produtos
                for (int idProduto : idProdutos) {
                    // Obtendo o produto com o id fornecido
                    Produto produto = produtoDao.obterProdutoId(idProduto);

                    // Adicionando o valor do produto na lista de valores
                    if (produto != null) {
                        //valoresProdutos[0].add(produto.getValor());
                        valorPegada += produto.getValor();
                    }
                }
            }
        });

        // Iniciando a execução da thread
        thread.start();
        try {
            // Esperando até que a thread termine de buscar os dados
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retornando o valor da pegada
        return valorPegada;
    }

}


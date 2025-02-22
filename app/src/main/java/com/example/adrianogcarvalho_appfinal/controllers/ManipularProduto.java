package com.example.adrianogcarvalho_appfinal.controllers;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.adrianogcarvalho_appfinal.R;
import com.example.adrianogcarvalho_appfinal.models.DAO.ProdutoDao;
import com.example.adrianogcarvalho_appfinal.models.DAO.UsuarioProdutoDao;
import com.example.adrianogcarvalho_appfinal.models.MyDatabase;
import com.example.adrianogcarvalho_appfinal.models.Produto;
import com.example.adrianogcarvalho_appfinal.models.UsuarioProduto;

import java.util.ArrayList;
import java.util.List;

public class ManipularProduto {
    private MyDatabase dbProduto;
    private ProdutoDao produtoDao;
    private UsuarioProdutoDao usuarioProdutoDao;

    public ManipularProduto(Context context) {
        // Inicializando o BD
        dbProduto = Room.databaseBuilder(context, MyDatabase.class, "pegada_carbono")
                .fallbackToDestructiveMigration()
                .build();

        // Inicializando as classes Dao necessárias
        produtoDao = dbProduto.produtoDao();
        usuarioProdutoDao = dbProduto.usuarioProdutoDao();

    }
    public void inserirProduto(Produto produto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbProduto.produtoDao().inserirProduto(produto);
            }
        }).start();
    }

    // Método para inserir a lista inicial de produtos no BD
    public void inserirListaProdutos(Context context) {

        // Lendo o string-array do arquivo strings.xml
        String[] produtos = context.getResources().getStringArray(R.array.produtos);

        // Iterando sobre os itens do array
        for (String produtoString : produtos) {

            // Dividindo a string usando vírgula como separador
            String[] separando = produtoString.split(",");

            // Pegando a descrição do produto e o valor da PC
            String nome = separando[0];
            double valor = Double.parseDouble(separando[1]);

            // Criando o objeto Produto
            Produto produto = new Produto(nome, valor);

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

    public List<Produto> listarTodosProdutos() {
        // Lista que receberá a lista de produtos (final para impedir alteração na sua referência)
        final List<Produto>[] produtos = new List[]{new ArrayList<>()};

        // Alterado as threads para que se aguarde a conclusão da busca para depois fazer return
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                produtos[0] = produtoDao.listarTodosProdutos();
                Log.d("ManipularProduto", "Produtos obtidos: " + produtos[0].size());
            }
        });
        thread.start();

        // Esperando a thread termine suas operações
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("ManipularProduto", "Tamanho da lista de produtos retornada: " + produtos[0].size());
        return produtos[0];
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
    public boolean existeProduto(final String nomeProduto) {
        final boolean[] existe = {false};
        // Criando uma thread para realizar a consulta
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Produto produto = dbProduto.produtoDao().obterProdutoPorNome(nomeProduto);
                if (produto != null) {
                    existe[0] = true;
                }
            }
        });

        // Inicia a execução da thread
        thread.start();

        // Como a consulta no BD é realizada em paralelo tem que aguardar
        try {
            // Aguarda a conclusão da thread antes de continuar
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retorna o valor verificado
        return existe[0];
    }

    // Método para listar os produtos escolhidos por um usuário
    public List<Produto> listarProdutosEscolhidos(int idUsuario) {
        final List<Produto>[] produtosEscolhidos = new List[]{new ArrayList<>()};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<UsuarioProduto> escolhas = usuarioProdutoDao.obterProdutosDeUsuario(idUsuario);
                for (UsuarioProduto escolha : escolhas) {
                    //Produto produto = produtoDao.obterProdutoId(escolha.getId_produto());
                    //produtosEscolhidos[0].add(produto);
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return produtosEscolhidos[0];
    }

    // Método para salvar as escolhas de produtos pelo usuário
    public void salvarEscolhaProdutos(List<UsuarioProduto> usuarioProdutos) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (UsuarioProduto usuarioProduto : usuarioProdutos) {
                    usuarioProdutoDao.inserirUsuarioProduto(usuarioProduto);
                }
            }
        }).start();
    }
}

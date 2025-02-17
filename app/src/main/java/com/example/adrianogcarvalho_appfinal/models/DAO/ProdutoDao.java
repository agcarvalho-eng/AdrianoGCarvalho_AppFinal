package com.example.adrianogcarvalho_appfinal.models.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.adrianogcarvalho_appfinal.models.Produto;

import java.util.List;

@Dao
public interface ProdutoDao {

    @Insert
    void inserirProduto(Produto produto);

    @Insert
    void inserirListaProdutos(Produto produto);

    @Query("SELECT * FROM produtos")
    List<Produto> listarTodosProdutos();

    @Query("SELECT * FROM produtos WHERE id = :idProduto")
    Produto obterProdutoId(int idProduto);

    @Query("SELECT * FROM produtos WHERE nome = :nome")
    Produto obterProdutoPorNome(String nome);

}

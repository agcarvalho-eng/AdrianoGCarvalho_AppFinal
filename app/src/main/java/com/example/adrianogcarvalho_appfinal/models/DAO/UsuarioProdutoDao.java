package com.example.adrianogcarvalho_appfinal.models.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.adrianogcarvalho_appfinal.models.UsuarioProduto;

import java.util.List;

@Dao
public interface UsuarioProdutoDao {
    // Inserindo um novo relacionamento entre usuário e produto
    @Insert
    void inserirUsuarioProduto(UsuarioProduto usuarioProduto);

    // Consultando todas os produtos de um usuário
    @Query("SELECT * FROM usuario_produto WHERE id_usuario = :idUsuario")
    List<UsuarioProduto> obterProdutosDeUsuario(int idUsuario);



}

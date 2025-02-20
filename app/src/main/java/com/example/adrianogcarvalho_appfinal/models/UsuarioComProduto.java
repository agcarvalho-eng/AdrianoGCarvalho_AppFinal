package com.example.adrianogcarvalho_appfinal.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UsuarioComProduto {


    @Embedded
    // Atributo da classe Usuário
    public Usuario usuario;

    @Relation(
            parentColumn = "id",  // Coluna da tabela 'usuarios'
            entityColumn = "id",  // Coluna da tabela 'produtos'
            // Definindo a tabela intermediária
            associateBy = @Junction(UsuarioProduto.class)
    )
    // A lista de Produtos associados ao Usuário
    public List<Produto> produtos;

    // Construtor padrão para Room
    public UsuarioComProduto () {}

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}

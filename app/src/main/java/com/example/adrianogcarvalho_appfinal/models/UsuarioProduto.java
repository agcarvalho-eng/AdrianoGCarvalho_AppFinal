package com.example.adrianogcarvalho_appfinal.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario_produto")
public class UsuarioProduto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_usuario")
    private int id_usuario;

    @ColumnInfo(name = "id_produto")
    private int id_produto;

    // Room exige um construtor padrão
    public UsuarioProduto() {}


    public UsuarioProduto(int id_usuario, int id_produto) {
        this.id_usuario = id_usuario;
        this.id_produto = id_produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }
}

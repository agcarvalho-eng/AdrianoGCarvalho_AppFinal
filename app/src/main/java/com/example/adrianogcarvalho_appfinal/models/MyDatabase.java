package com.example.adrianogcarvalho_appfinal.models;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.adrianogcarvalho_appfinal.models.DAO.ProdutoDao;
import com.example.adrianogcarvalho_appfinal.models.DAO.UsuarioDao;

@Database(entities = {Usuario.class, Produto.class, PegadaCarbono.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase{

    public abstract UsuarioDao usuarioDao();
    public abstract ProdutoDao produtoDao();
    //public abstract PegadaCarbonoDao pegadaCarbonoDao();
}

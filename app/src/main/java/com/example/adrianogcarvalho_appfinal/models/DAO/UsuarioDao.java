package com.example.adrianogcarvalho_appfinal.models.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.adrianogcarvalho_appfinal.models.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void inserirUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios")
    List<Usuario> listarTodosUsuarios();

    @Update
    void atualizarUsuario(Usuario usuario);

    @Query("SELECT nome FROM usuarios WHERE id = :idUsuario")
    String obterNomeUsuarioId(int idUsuario);

    @Query("SELECT * FROM usuarios WHERE email = :email AND senha = :senha")
    Usuario validarLogin(String email, String senha);
}

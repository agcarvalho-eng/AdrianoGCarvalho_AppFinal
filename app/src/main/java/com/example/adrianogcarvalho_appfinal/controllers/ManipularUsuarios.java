package com.example.adrianogcarvalho_appfinal.controllers;

import android.content.Context;
import android.os.Message;
import android.os.Handler;
import androidx.room.Room;

import com.example.adrianogcarvalho_appfinal.models.MyDatabase;
import com.example.adrianogcarvalho_appfinal.models.SessaoUsuario;
import com.example.adrianogcarvalho_appfinal.models.Usuario;

public class ManipularUsuarios {

    private MyDatabase dbUsuario;

    public ManipularUsuarios(Context context) {
        // Inicializando o BD
        dbUsuario = Room.databaseBuilder(context, MyDatabase.class, "pegada_carbono")
                .fallbackToDestructiveMigration()
                .build();
    }

    public void inserirUsuario(Usuario usuario) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbUsuario.usuarioDao().inserirUsuario(usuario);
            }
        }).start();
    }

    public void listarTodosUsuarios() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbUsuario.usuarioDao().listarTodosUsuarios();
            }
        }).start();
    }

    public String obterNomeUsuarioId(int idUsuario) {
        final String[] nomeUsuario = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                nomeUsuario[0] = dbUsuario.usuarioDao().obterNomeUsuarioId(idUsuario);
            }
        }).start();
        return nomeUsuario[0];
    }

    public void validarLogin(Usuario usuario, Handler handler) {
        final String emailUsuario = usuario.getEmail();
        final String senhaUsuario = usuario.getSenha();

        // Criando uma nova Thread para consultar o BD
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Chamando o Dao para fazer a consulta
                Usuario usuarioResultado = dbUsuario.usuarioDao().validarLogin(emailUsuario, senhaUsuario);

                // Criando um objeto mensagem para enviar para o Handler (cominicação entre Threads)
                Message mensagem = handler.obtainMessage();
                if (usuarioResultado != null) {
                    // Guardando as informações do usuário na "sessão"
                    SessaoUsuario.setIdUsuario(usuarioResultado.getId());
                    SessaoUsuario.setNomeUsuario(usuarioResultado.getNome());
                    SessaoUsuario.setEmailUsuario(usuarioResultado.getEmail());
                    // Atribuindo o resultado ao objeto menssagem
                    mensagem.obj = usuarioResultado;
                } else {
                    mensagem.obj = null;
                }

                // Enviando o resultado para o Handler
                handler.sendMessage(mensagem);
            }
        }).start();
    }

}
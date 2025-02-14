package com.example.adrianogcarvalho_appfinal.models;

public class SessaoUsuario {
    private static int idUsuario;
    private static String nomeUsuario;
    private static String emailUsuario;

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static void setIdUsuario(int idUsuario) {
        SessaoUsuario.idUsuario = idUsuario;
    }

    public static String getNomeUsuario() {
        return nomeUsuario;
    }

    public static void setNomeUsuario(String nomeUsuario) {
        SessaoUsuario.nomeUsuario = nomeUsuario;
    }

    public static String getEmailUsuario() {
        return emailUsuario;
    }

    public static void setEmailUsuario(String emailUsuario) {
        SessaoUsuario.emailUsuario = emailUsuario;
    }
}

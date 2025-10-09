package com.avicheckpoint.model;

import java.time.LocalDate;

/**
 * Representa um usuário do tipo Administrador no sistema.
 * Herda de Usuario e possui privilégios administrativos.
 */
public class Administrador extends Usuario {
    
    public Administrador() {
        super();
    }
    
    public Administrador(Integer usuarioId, String usuarioNomeCompleto, LocalDate usuarioDataNascimento,
                        String usuarioEmail, String usuarioSenha) {
        super(usuarioId, usuarioNomeCompleto, usuarioDataNascimento, usuarioEmail, usuarioSenha);
    }
    
    @Override
    public String getTipoUsuario() {
        return "ADMINISTRADOR";
    }
    
    @Override
    public String toString() {
        return "Administrador{" +
                "usuarioId=" + getUsuarioId() +
                ", usuarioNomeCompleto='" + getUsuarioNomeCompleto() + '\'' +
                ", usuarioEmail='" + getUsuarioEmail() + '\'' +
                '}';
    }
}
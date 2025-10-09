package com.avicheckpoint.model;

import java.time.LocalDate;

/**
 * Representa um usuário do tipo Produtor no sistema.
 * Herda de Usuario e é o ator principal que preenche formulários.
 */
public class Produtor extends Usuario {
    
    public Produtor() {
        super();
    }
    
    public Produtor(Integer usuarioId, String usuarioNomeCompleto, LocalDate usuarioDataNascimento,
                   String usuarioEmail, String usuarioSenha) {
        super(usuarioId, usuarioNomeCompleto, usuarioDataNascimento, usuarioEmail, usuarioSenha);
    }
    
    @Override
    public String getTipoUsuario() {
        return "PRODUTOR";
    }
    
    @Override
    public String toString() {
        return "Produtor{" +
                "usuarioId=" + getUsuarioId() +
                ", usuarioNomeCompleto='" + getUsuarioNomeCompleto() + '\'' +
                ", usuarioEmail='" + getUsuarioEmail() + '\'' +
                '}';
    }
}
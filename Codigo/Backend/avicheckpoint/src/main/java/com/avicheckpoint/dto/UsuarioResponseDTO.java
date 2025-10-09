package com.avicheckpoint.dto;

import java.time.LocalDateTime;

/**
 * DTO para resposta de usuários.
 * Retorna dados do usuário sem informações sensíveis.
 */
public class UsuarioResponseDTO {
    
    private Integer usuarioId;
    private String usuarioNomeCompleto;
    private String usuarioEmail;
    private String tipoUsuario;
    private String estado;
    private String cidade;
    private LocalDateTime dataCriacao;
    
    // Para veterinários
    private String formacao;
    private String bio;
    private String veterinarioCRM;
    
    // Constructors
    public UsuarioResponseDTO() {}
    
    public UsuarioResponseDTO(Integer usuarioId, String usuarioNomeCompleto, String usuarioEmail, 
                            String tipoUsuario, String estado, String cidade) {
        this.usuarioId = usuarioId;
        this.usuarioNomeCompleto = usuarioNomeCompleto;
        this.usuarioEmail = usuarioEmail;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
        this.cidade = cidade;
    }
    
    // Getters and Setters
    public Integer getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getUsuarioNomeCompleto() {
        return usuarioNomeCompleto;
    }
    
    public void setUsuarioNomeCompleto(String usuarioNomeCompleto) {
        this.usuarioNomeCompleto = usuarioNomeCompleto;
    }
    
    public String getUsuarioEmail() {
        return usuarioEmail;
    }
    
    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public String getFormacao() {
        return formacao;
    }
    
    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public String getVeterinarioCRM() {
        return veterinarioCRM;
    }
    
    public void setVeterinarioCRM(String veterinarioCRM) {
        this.veterinarioCRM = veterinarioCRM;
    }
}
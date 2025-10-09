package com.avicheckpoint.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe base para todos os usuários do sistema Avicheckpoint.
 * Representa a entidade principal da qual herdam administrador, veterinario e produtor.
 */
public abstract class Usuario {
    
    @NotNull
    private Integer usuarioId;
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String usuarioNomeCompleto;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate usuarioDataNascimento;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String usuarioEmail;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @JsonIgnore
    private String usuarioSenha;
    
    private Endereco endereco;
    
    private LocalDateTime dataCriacao;
    
    private LocalDateTime dataAtualizacao;
    
    // Constructors
    public Usuario() {}
    
    public Usuario(Integer usuarioId, String usuarioNomeCompleto, LocalDate usuarioDataNascimento, 
                   String usuarioEmail, String usuarioSenha) {
        this.usuarioId = usuarioId;
        this.usuarioNomeCompleto = usuarioNomeCompleto;
        this.usuarioDataNascimento = usuarioDataNascimento;
        this.usuarioEmail = usuarioEmail;
        this.usuarioSenha = usuarioSenha;
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
    
    public LocalDate getUsuarioDataNascimento() {
        return usuarioDataNascimento;
    }
    
    public void setUsuarioDataNascimento(LocalDate usuarioDataNascimento) {
        this.usuarioDataNascimento = usuarioDataNascimento;
    }
    
    public String getUsuarioEmail() {
        return usuarioEmail;
    }
    
    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
    
    public String getUsuarioSenha() {
        return usuarioSenha;
    }
    
    public void setUsuarioSenha(String usuarioSenha) {
        this.usuarioSenha = usuarioSenha;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    /**
     * Método abstrato para obter o tipo de usuário.
     * Deve ser implementado pelas classes filhas.
     */
    public abstract String getTipoUsuario();
    
    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", usuarioNomeCompleto='" + usuarioNomeCompleto + '\'' +
                ", usuarioEmail='" + usuarioEmail + '\'' +
                '}';
    }
}
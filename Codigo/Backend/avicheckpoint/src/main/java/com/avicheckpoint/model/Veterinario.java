package com.avicheckpoint.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * Representa um usuário do tipo Veterinário no sistema.
 * Herda de Usuario e possui CRM e especialidades.
 */
public class Veterinario extends Usuario {
    
    @NotBlank(message = "CRM é obrigatório")
    @Size(min = 4, max = 15, message = "CRM deve ter entre 4 e 15 caracteres")
    private String veterinarioCRM;
    
    @Size(max = 100, message = "Formação deve ter no máximo 100 caracteres")
    private String formacao;
    
    @Size(max = 500, message = "Bio deve ter no máximo 500 caracteres")
    private String bio;
    
    private List<String> especialidades;
    
    public Veterinario() {
        super();
    }
    
    public Veterinario(Integer usuarioId, String usuarioNomeCompleto, LocalDate usuarioDataNascimento,
                      String usuarioEmail, String usuarioSenha, String veterinarioCRM, String formacao) {
        super(usuarioId, usuarioNomeCompleto, usuarioDataNascimento, usuarioEmail, usuarioSenha);
        this.veterinarioCRM = veterinarioCRM;
        this.formacao = formacao;
    }
    
    @Override
    public String getTipoUsuario() {
        return "VETERINARIO";
    }
    
    // Getters and Setters
    public String getVeterinarioCRM() {
        return veterinarioCRM;
    }
    
    public void setVeterinarioCRM(String veterinarioCRM) {
        this.veterinarioCRM = veterinarioCRM;
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
    
    public List<String> getEspecialidades() {
        return especialidades;
    }
    
    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }
    
    @Override
    public String toString() {
        return "Veterinario{" +
                "usuarioId=" + getUsuarioId() +
                ", usuarioNomeCompleto='" + getUsuarioNomeCompleto() + '\'' +
                ", usuarioEmail='" + getUsuarioEmail() + '\'' +
                ", veterinarioCRM='" + veterinarioCRM + '\'' +
                ", formacao='" + formacao + '\'' +
                '}';
    }
}
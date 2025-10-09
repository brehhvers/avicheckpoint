package com.avicheckpoint.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para receber dados do cadastro de veterinário do frontend.
 * Mapeia exatamente os campos enviados pelo JavaScript.
 */
public class VeterinarioRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).*$",
        message = "Senha deve conter pelo menos uma letra minúscula, uma maiúscula e um símbolo"
    )
    private String senha;
    
    @NotBlank(message = "UF é obrigatória")
    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    private String uf;
    
    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    private String cidade;
    
    @NotBlank(message = "Formação é obrigatória")
    private String formacao;
    
    @Size(max = 500, message = "Bio deve ter no máximo 500 caracteres")
    private String bio;
    
    private String crm; // Campo adicional não presente no frontend base
    
    // Constructors
    public VeterinarioRequestDTO() {}
    
    public VeterinarioRequestDTO(String nome, String email, String senha, String uf, 
                               String cidade, String formacao, String bio) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uf = uf;
        this.cidade = cidade;
        this.formacao = formacao;
        this.bio = bio;
    }
    
    // Getters and Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getUf() {
        return uf;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
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
    
    public String getCrm() {
        return crm;
    }
    
    public void setCrm(String crm) {
        this.crm = crm;
    }
    
    @Override
    public String toString() {
        return "VeterinarioRequestDTO{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", uf='" + uf + '\'' +
                ", cidade='" + cidade + '\'' +
                ", formacao='" + formacao + '\'' +
                '}';
    }
}
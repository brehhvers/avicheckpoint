package com.avicheckpoint.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para receber dados do cadastro de produtor do frontend.
 * Mapeia exatamente os campos enviados pelo JavaScript.
 */
public class ProdutorRequestDTO {
    
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
    
    // Constructors
    public ProdutorRequestDTO() {}
    
    public ProdutorRequestDTO(String nome, String email, String senha, String uf, String cidade) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.uf = uf;
        this.cidade = cidade;
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
    
    @Override
    public String toString() {
        return "ProdutorRequestDTO{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", uf='" + uf + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}